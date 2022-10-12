<template>
	<view class="wrap">
		<u-navbar title="编辑地址" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<u--form labelPosition="left" :model="addressInfo" style="margin: 25px">
			<u-form-item label="收货人" leftIcon="account" borderBottom labelWidth="100px">
				<u--input placeholder="请输入姓名" v-model="addressInfo.name" border="none"></u--input>
			</u-form-item>
			<u-form-item label="电话号码" leftIcon="phone" borderBottom labelWidth="100px">
				<u--input placeholder="请输入电话号码" v-model="addressInfo.tel" border="none"></u--input>
			</u-form-item>
			<u-form-item label="省份" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入省份/直辖市" v-model="addressInfo.province" border="none"></u--input>
			</u-form-item>
			<u-form-item label="城市" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入城市" v-model="addressInfo.city" border="none"></u--input>
			</u-form-item>
			<u-form-item label="区" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入区" v-model="addressInfo.region" border="none"></u--input>
			</u-form-item>
			<u-form-item label="详细地址" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入地址(街道)" v-model="addressInfo.address" border="none"></u--input>
			</u-form-item>
			<u-form-item label="邮政编码" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入邮政编码" v-model="addressInfo.postCode" border="none"></u--input>
			</u-form-item>
			<u-form-item label="省市区代码" leftIcon="map" borderBottom labelWidth="100px">
				<u--input placeholder="请输入省市区代码" v-model="addressInfo.areacode" border="none"></u--input>
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
					province: '',
					city: '',
					region: '',
					address: '',
					postCode: '',
					areacode: '',
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
					url: this.$baseUrl + `/member/memberaddress/${this.addressId}`,
					method: 'GET',
					data: {
						// addressId: this.addressId,
					},
					header: {
						token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("getAddress");
						console.log(res);
						this.addressInfo.name = res.data.data.name;
						this.addressInfo.tel = res.data.data.phone;
						this.addressInfo.province = res.data.data.province;
						this.addressInfo.city = res.data.data.city;
						this.addressInfo.region = res.data.data.region;
						this.addressInfo.address = res.data.data.detailAddress;
						this.addressInfo.postCode = res.data.data.postCode;
						this.addressInfo.areacode = res.data.data.areacode;
						if (res.data.data.defaultStatus == 1) this.value = true;
					}),
				});
			},
			sumbit() {
				if (this.addressId == null) this.addAddress();
				else this.updateAddress();
			},
			addAddress() {
				uni.request({
					url: this.$baseUrl + '/member/memberaddress',
					method: 'POST',
					data: {
						memberId: this.memberId,
						name: this.addressInfo.name,
						phone: this.addressInfo.tel,
						province: this.addressInfo.province,
						city: this.addressInfo.city,
						region: this.addressInfo.region,
						detailAddress: this.addressInfo.address,
						postCode: this.addressInfo.postCode,
						areacode: this.addressInfo.areacode,
						defaultStatus: 0,
					},
					header: {
						token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("add");
						console.log(res);
						// this.addressId = res.data.data;
						// if (this.value == 1) {
						this.setDef();
						// }
						// uni.navigateBack({})
						uni.navigateTo({
							url: 'MyAddress'
						});
					}),
				});
			},
			updateAddress() {
				uni.request({
					url: this.$baseUrl + '/member/memberaddress',
					method: 'PUT',
					data: {
						id: this.addressId,
						memberId: this.memberId,
						name: this.addressInfo.name,
						phone: this.addressInfo.tel,
						province: this.addressInfo.province,
						city: this.addressInfo.city,
						region: this.addressInfo.region,
						detailAddress: this.addressInfo.address,
						postCode: this.addressInfo.postCode,
						areacode: this.addressInfo.areacode,
					},
					header: {
						token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log("update");
						console.log(res);
						if (this.value == 1) {
							this.setDef();
						}
						// uni.navigateBack({})
						uni.navigateTo({
							url: 'MyAddress'
						});
					}),
				});
			},
			setDef() {
				uni.request({
					url: this.$baseUrl + '/member/memberaddress/setDefaultAddress',
					method: 'POST',
					data: {
						id: this.addressId,
						memberId: this.memberId,
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
						self.memberId = res.data.memberId;
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
