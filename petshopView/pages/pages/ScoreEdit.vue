<template>
	<view class="wrap">
		<u-navbar title="评价" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<view style="margin: 30rpx;">
			<u--textarea v-model="value" placeholder="请输入您的评价" confirmType="done" count
				style="background-color: #fff7fc">
			</u--textarea>
			<u-gap height="20" bgColor="#fff7fc"></u-gap>
			<u-rate :count="count" v-model="rank" size="25"></u-rate>
			<u-button type="error" text="评价" customStyle="margin-top: 50px" @click="submit()"></u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				count: 5,
				value: '',
				rank: 0,
				token: '',
				memberId: '',
				orderId: '',
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id); //打印出上个页面传递的参数。
			this.getStorage();
			this.orderId = option.id;
		},
		methods: {
			submit() {
				uni.request({
					url: this.$baseUrl + '/order/orderscore',
					method: 'POST',
					data: {
						orderId: this.orderId,
						info: this.value,
						orderRank: this.rank,
					},
					header: {
						token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("提交评价");
						console.log(res);
						// uni.navigateBack({})
						uni.navigateTo({
							url: 'Order'
						});
					}),
				});
			},
			getStorage() {
				let self = this;
				uni.getStorage({
					key: "user",
					success(res) {
						self.token = res.data.token;
						self.memberId = res.data.memberId;
						console.log('获取成功', res);
					}
				})
			},
		},
	}
</script>

<style scoped lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
	}
</style>
