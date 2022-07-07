<template>
	<view class="wrap">
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
			<u-button type="error" text="保存" customStyle="margin-top: 50px" @click="sumbit"></u-button>
		</u--form>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: false,
				addressId: null,
				addressInfo: {
					name: '',
					tel: '',
					address: '',
					isDef: 0,
				}
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id);
			this.addressId = option.id;
			this.getStorage();
			if (this.addressId != null) {
				this.getAddress();
			}
		},
		methods: {
			getAddress() {
				uni.request({
					url: this.$baseUrl + '/login-server/userAddress/getAddressById',
					method: 'POST',
					data: {
						addressId: this.addressId,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("getAddress");
						console.log(res);
						this.addressInfo.name = res.data.data.receiver;
						this.addressInfo.address = res.data.data.address;
						this.addressInfo.tel = res.data.data.tel;
						if (res.data.data.isDef) this.value = true;
					}),
				});
			},
			sumbit() {
				if (this.addressId == null) this.addAddress();
				else this.updateAddress();
			},
			addAddress() {
				uni.request({
					url: this.$baseUrl + '/login-server/userAddress/addAddress',
					method: 'POST',
					data: {
						address: this.addressInfo.address,
						receiver: this.addressInfo.name,
						tel: this.addressInfo.tel,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("add");
						console.log(res);
						this.addressId = res.data.data;
						if (this.value == 1) {
							this.setDef();
						}
						uni.navigateBack({})
					}),
				});
			},
			updateAddress() {
				uni.request({
					url: this.$baseUrl + '/login-server/userAddress/updateAddress',
					method: 'POST',
					data: {
						addressId: this.addressId,
						address: this.addressInfo.address,
						receiver: this.addressInfo.name,
						tel: this.addressInfo.tel,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("update");
						console.log(res);
						if (this.value == 1) {
							this.setDef();
						}
						uni.navigateBack({})
					}),
				});
			},
			setDef() {
				uni.request({
					url: this.$baseUrl + '/login-server/userAddress/setDefAddress',
					method: 'POST',
					data: {
						addressId: this.addressId,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("setDef");
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
<style scoped lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
	}
</style>