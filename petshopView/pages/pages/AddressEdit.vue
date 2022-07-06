<template>
	<view>
		<u-navbar title="编辑地址" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<u--form labelPosition="left" :model="addressInfo" style="margin: 25px">
			<u-form-item label="收货人" leftIcon="account" borderBottom labelWidth="100px">
				<u--input placeholder="请输入姓名" v-model="addressInfo.name" border="none"></u--input>
			</u-form-item>
			<u-form-item label="手机号码" leftIcon="phone" borderBottom labelWidth="100px">
				<u--input placeholder="请输入手机号" v-model="addressInfo.tel" border="none"></u--input>
			</u-form-item>
			<u-form-item label="详细地址" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入地址" v-model="addressInfo.address" border="none"></u--input>
			</u-form-item>
			<u-form-item label="设为默认收货地址" leftIcon="map" borderBottom labelWidth="200px">
				<u-switch v-model="value" activeColor="#f56c6c" slot="right"></u-switch>
			</u-form-item>
			<u-button type="error" text="保存" customStyle="margin-top: 50px" @click="addAddress"></u-button>
		</u--form>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: true,
				addressInfo: {
					name: 'default',
					tel: '+86 12345678901',
					address: 'aaaaaaaaaa',
				}
			}
		},
		onLoad() {
			this.getStorage();
		},
		methods: {
			// getAddress() {
			// 	uni.request({
			// 		url: '/api/login-server/userAddress/getAddress',
			// 		method: 'POST',
			// 		header: {
			// 			token: this.token,
			// 			"Content-Type": "application/x-www-form-urlencoded",
			// 		},
			// 		success: ((res) => {
			// 			console.log(res);
			// 			for (let i = 0; i < res.data.data.length; i++) {
			// 				this.addressList.push({
			// 					address: res.data.data[i].address,
			// 					addressId: res.data.data[i].addressId,
			// 				})
			// 			}
			// 		}),
			// 	});
			// },
			addAddress() {
				uni.request({
					url: '/api/login-server/userAddress/addAddress',
					method: 'POST',
					data: {
						address: this.addressInfo.address,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
					}),
				});
			},
			getStorage() {
				let self = this;
				uni.getStorage({
					key: "user",
					success(res) {
						self.token = res.data.token;
						console.log('获取成功', res);
					}
				})
			},
		},
	};
</script>
