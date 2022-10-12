<template>
	<view class="wrap">
		<u-navbar title="个人资料" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<u--form labelPosition="left" :model="userInfo" style="margin: 25px">
			<u-form-item label="头像" leftIcon="account" borderBottom labelWidth="100px">
				<u-avatar :text="userInfo.name" fontSize="20" size="70" randomBgColor>
				</u-avatar>
			</u-form-item>
			<u-form-item label="账号" leftIcon="account" borderBottom labelWidth="100px">
				<text>{{username}}</text>
			</u-form-item>
			<u-form-item label="用户名" leftIcon="account" borderBottom labelWidth="100px">
				<u--input placeholder="请输入用户名" v-model="userInfo.name" border="none"></u--input>
			</u-form-item>
			<u-form-item label="手机号码" leftIcon="phone" borderBottom labelWidth="100px">
				<u--input placeholder="请输入手机号" v-model="userInfo.tel" border="none"></u--input>
			</u-form-item>
			<u-form-item label="电子邮箱" leftIcon="email" borderBottom labelWidth="100px">
				<u--input placeholder="请输入邮箱" v-model="userInfo.mail" border="none"></u--input>
			</u-form-item>
			<u-form-item label="城市" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入所在城市" v-model="userInfo.city" border="none"></u--input>
			</u-form-item>
			<u-form-item label="会员等级" leftIcon="map" borderBottom labelWidth="100px">
				<text>Level.{{userInfo.level}}</text>
			</u-form-item>
			<u-form-item label="注册时间" leftIcon="map" borderBottom labelWidth="100px">
				<text>{{userInfo.createTime}}</text>
			</u-form-item>
			<u-button type="error" text="保存" customStyle="margin-top: 50px" @click="updateUser"></u-button>
			<u-button type="error" text="修改密码" customStyle="margin-top: 20px" @click="show = true"></u-button>
		</u--form>
		<u-toast ref="uToast"></u-toast>
		<u-popup :show="show" @close="close" @open="open" mode="center">
			<view style="height: 50vh; width: 45vh;">
				<u--form labelPosition="left" :model="userInfo" style="margin: 25px">
					<u-form-item borderBottom labelWidth="100px">
						<text>修改密码</text>
					</u-form-item>
					<u-form-item label="旧的密码" leftIcon="account" borderBottom labelWidth="100px">
						<u--input placeholder="请输入密码" v-model="password.old" :password="true" border="none"></u--input>
					</u-form-item>
					<u-form-item label="新的密码" leftIcon="phone" borderBottom labelWidth="100px">
						<u--input placeholder="请输入密码" v-model="password.new" :password="true" border="none"></u--input>
					</u-form-item>
				</u--form>
				<u-button type="error" text="确认修改" customStyle="margin-top: 20px; width: 20vh" @click="updatePassword"></u-button>
			</view>
			<u-toast ref="uToast"></u-toast>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: true,
				show: false,
				username: "",
				token: "",
				memberId: "",
				userInfo: {
					name: '',
					tel: '',
					createTime: '',
					mail: '',
					city: '',
					id: '',
					level: '',
				},
				password: {
					old: "",
					new: "",
				},
				toastParams: {
					type: 'error',
					message: "fail",
				},
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id);
			this.getStorage();
			this.getUser();
		},
		methods: {
			getUser() {
				uni.request({
					url: this.$baseUrl + `/member/member/${this.memberId}`,
					method: 'GET',
					header: {
						// token: this.token,
						// 		"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						this.userInfo.name = res.data.data.nickname;
						this.userInfo.createTime = res.data.data.createTime;
						this.userInfo.city = res.data.data.city;
						this.userInfo.tel = res.data.data.mobile;
						this.userInfo.mail = res.data.data.email;
						this.userInfo.id = res.data.data.id;
						this.userInfo.level = res.data.data.level;
					}),
				});
			},
			updateUser() {
				this.toastParams.message = "fail";
				this.toastParams.type = "error";
				uni.request({
					url: this.$baseUrl + '/member/member',
					method: 'PUT',
					data: {
						city: this.userInfo.city,
						email: this.userInfo.mail,
						id: this.userInfo.id,
						mobile: this.userInfo.tel,
						nickname: this.userInfo.name,
					},
					header: {
						// token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("update");
						console.log(res);
						this.toastParams.message = res.data.msg;
						if (res.data.code == 0)
							this.toastParams.type = "success";
						this.showToast(this.toastParams);
					}),
				});
			},
			updatePassword() {
				this.toastParams.message = "fail";
				this.toastParams.type = "error";
				uni.request({
					url: this.$baseUrl + '/login-server/userInfo/updateUserPassword',
					method: 'POST',
					data: {
						oldPassword: this.password.old,
						newPassword: this.password.new,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("updatePassword");
						console.log(res);
						this.toastParams.message = res.data.message;
						if (res.data.statusCode == "200")
							this.toastParams.type = "success";
						this.showToast(this.toastParams);
					}),
				});
			},
			getStorage() {
				let self = this;
				uni.getStorage({
					key: "user",
					success(res) {
						self.token = res.data.token;
						self.username = res.data.username;
						self.memberId = res.data.memberId;
						console.log('获取成功', res);
					}
				})
			},
			open() {
				// console.log('open');
			},
			close() {
				this.show = false
				// console.log('close');
			},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
				})
			}
		},
	};
</script>
<style scoped lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
	}
</style>
