<template>
	<view class="wrap">
		<u-navbar title="登录" :autoBack="true" bgColor="#ffadb1"></u-navbar>
		<view style="margin-top: 200px; margin-left: 80px; margin-right: 80px;">
			<u--input placeholder="请输入用户名" border="surround" v-model="username"></u--input>
			<u-gap height="15"></u-gap>
			<u--input placeholder="请输入密码" border="surround" type="password" v-model="password"></u--input>
			<u-gap height="10"></u-gap>
			<u-button @click="login" text="登录" color="#ffadb1"></u-button>
			<u-gap height="5"></u-gap>
			<u-button text="注册" color="#ffc4c5"></u-button>
<!-- 			<u-button @click="setStorage" text="setStorage" color="#ffadb1"></u-button>
			<u-button @click="getStorage" text="getStorage" color="#ffadb1"></u-button>
			<u-button @click="removeStorage" text="removeStorage" color="#ffadb1"></u-button> -->
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username: "canny",
				password: "123456",
				loginMessage: "",
				show: false,
				toastParams: {
					type: 'error',
					message: "",
				},
			}
		},
		methods: {
			login() {
				console.log("ok");
				uni.request({
					url: '/api/login-server/login/useLogin',
					method: 'POST',
					data: {
						username: this.username,
						password: this.password,
					},
					header: {
						"Content-Type": "application/x-www-form-urlencoded"
					},
					success: ((res) => {
						// console.log(res);
						this.loginMessage = res.data.message;
						console.log(this.loginMessage);
						this.toastParams.message = this.loginMessage;
						if (res.data.isSuccess) {
							this.removeStorage();
							uni.setStorage({
								key: "user",
								data: {
									username: this.username,
									nickname: res.data.data.nickname,
									token: res.data.data.token,
								},
								success() {
									console.log('储存成功');
								}
							});
							this.toastParams.type = "success";
							this.toastParams.url = "../index";
						}
						this.showToast(this.toastParams);
					}),
				});
			},
			setStorage() {
				uni.setStorage({
					key: "user",
					data: {
						username: "canny",
						password: 123456,
						nickname: "res.data.nickname",
						token: 123,
					},
					success() {
						console.log('储存成功');
					},
					fail(res) {
						console.log(res);
					}
				});
			},
			getStorage() {
				uni.getStorage({
					key: "user",
					success(res) {
						console.log('获取成功', res)
					}
				})
			},
			removeStorage() {
				uni.removeStorage({
					key: "user",
					success() {
						console.log('删除成功')
					}
				})
			},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
					complete() {
						params.url && uni.reLaunch({
							url: params.url
						})
					}
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
	}
</style>
