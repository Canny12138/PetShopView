<template>
	<view>
		<u-navbar title="收货地址" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<u-cell :label="item.address" title="Canny 12345678901" @click="editAddress(index)" :isLink="true" rightIcon="edit-pen" v-for="(item, index) in addressList" :key="index">
		</u-cell>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				addressList: [],
			}
		},
		mounted() {
			this.getStorage();
			this.getAddress();
		},
		methods: {
			getAddress() {
				uni.request({
					url: '/api/login-server/userAddress/getAddress',
					method: 'POST',
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						for (let i = 0; i < res.data.data.length; i++) {
							this.addressList.push({
								address: res.data.data[i].address,
								addressId: res.data.data[i].addressId,
							})
						}
					}),
				});
			},
			editAddress(i) {
				console.log(i);
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
		}
	}
</script>

<style>

</style>
