<template>
	<view>
		<u-button @click="getStorage" text="getStorage" color="#ffadb1"></u-button>
		<text>Welcome {{nickname}}</text>
		<u-button @click="login" text="登录" v-show="!isLogin"></u-button>
		<u-button @click="removeStorage" text="退出登录" v-show="isLogin"></u-button>
		<u-button @click="toCollect" text="我的收藏"></u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				nickname: "未登录",
				isLogin: false,
			}
		},
		mounted() {
			this.getStorage();
		},
		// updated() {
		// 	this.getStorage();
		// },
		methods: {
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

<style>

</style>
