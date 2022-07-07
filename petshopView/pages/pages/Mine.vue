<template>
	<view class="wrap">
		<u-navbar title="我的页面" bgColor="#ffadb1" leftIcon=""></u-navbar>
		<!-- <u-gap height="44" bgColor="#bbb"></u-gap> -->
		<u-gap height="40"></u-gap>
		<u-avatar :text="nickname" fontSize="20" size="70" randomBgColor style="float: left; margin-left: 10px;">
		</u-avatar>
		<view style="font-weight: bold; padding-top: 30px; padding-left: 100px; padding-bottom: 30px; font-size: 20px;">
			{{nickname}}
		</view>
		<uni-card style="height:250rpx; margin: 15px; background-color: #fff7fc">
			<view>我的订单</view>
			<u-gap height="10"></u-gap>
			<u-grid :border="false" col="4" @click="click">
				<u-grid-item v-for="(baseListItem,baseListIndex) in baseList" :key="baseListIndex">
					<u-icon :customStyle="{paddingTop:20+'rpx'}" :name="baseListItem.name" :size="25"></u-icon>
					<text class="grid-text">{{baseListItem.title}}</text>
				</u-grid-item>
			</u-grid>
		</uni-card>
		<uni-card style="height:250rpx; margin: 15px; background-color: #fff7fc">
			<view>常用信息</view>
			<u-gap height="10"></u-gap>
			<u-grid :border="false" col="4" @click="click2">
				<u-grid-item v-for="(baseListItem,baseListIndex) in baseList2" :key="baseListIndex">
					<u-icon :customStyle="{paddingTop:20+'rpx'}" :name="baseListItem.name" :size="25"></u-icon>
					<text class="grid-text">{{baseListItem.title}}</text>
				</u-grid-item>
			</u-grid>
		</uni-card>
		<u-gap height="20"></u-gap>
		<u-button @click="login" text="登录" v-show="!isLogin" style="background-color: #fff7fc"></u-button>
		<u-button @click="removeStorage" text="退出登录" v-show="isLogin" style="background-color: #fff7fc"></u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				nickname: "未登录",
				isLogin: false,
				baseList: [{
						name: 'rmb-circle',
						title: '待付款'
					},
					{
						name: 'car',
						title: '待发货'
					},
					{
						name: 'car-fill',
						title: '待收货'
					},
					{
						name: 'chat',
						title: '待评价'
					},
				],
				baseList2: [{
						name: 'star',
						title: '我的收藏',
						url: 'pages/Collect',
					},
					{
						name: 'clock',
						title: '浏览记录'
					},
					{
						name: 'map',
						title: '收货地址',
						url: 'pages/MyAddress'
					},
					{
						name: 'account',
						title: '个人资料',
						url: 'pages/User'
					},
				],
			}
		},
		mounted() {
			this.getStorage();
		},
		// updated() {
		// 	this.getStorage();
		// },
		methods: {
			click(name) {

			},
			click2(name) {
				// console.log(this.baseList2)
				uni.navigateTo({
					url: this.baseList2[name].url,
				});
			},
			login() {
				console.log("to login page");
				uni.navigateTo({
					url: 'pages/Login'
				});
			},
			toCollect() {
				uni.navigateTo({
					url: 'pages/Collect'
				});
			},
			set() {
				this.username = "aaa";
			},
			getStorage() {
				let self = this;
				uni.getStorage({
					key: "user",
					success(res) {
						console.log('获取成功', res);
						self.nickname = res.data.nickname;
						self.isLogin = true;
					}
				})
			},
			removeStorage() {
				let self = this;
				uni.removeStorage({
					key: "user",
					success() {
						console.log('删除成功');
						self.nickname = "未登录";
						self.isLogin = false;
					}
				})
			},
		}
	}
</script>

<style scoped lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
	}
</style>
