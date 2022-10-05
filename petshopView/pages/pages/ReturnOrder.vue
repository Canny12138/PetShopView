<template>
	<view class="wrap">
		<u-navbar title="申请退款" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<view style="margin: 30rpx;">
			<u--textarea v-model="value" placeholder="请输入您的申请理由" confirmType="done" count
				style="background-color: #fff7fc">
			</u--textarea>
			<u-gap height="20" bgColor="#fff7fc"></u-gap>
			<u-button type="error" text="提交申请" customStyle="margin-top: 50px" @click="toReturnOrder()"></u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				count: 5,
				value: '',
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
			changeStatus(id, status) {
				uni.request({
					url: this.$baseUrl + '/order/order',
					method: 'PUT',
					data: {
						id: id,
						orderStatus: status,
					},
					header: {
						token: this.token,
					},
					success: ((res) => {
						uni.navigateTo({
							url: 'OrderPage?id=' + this.orderId
						});
					})
				})
			},
			toReturnOrder() {
				uni.request({
					url: this.$baseUrl + '/order/orderreturnsubmit',
					method: 'POST',
					data: {
						orderId: this.orderId,
						returnReason: this.value,
					},
					header: {
						token: this.token,
					},
					success: ((res) => {
						this.changeStatus(this.orderId, -2);
					})
				})
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
