<template>
	<view class="wrap">
		<u-navbar title="收货地址" bgColor="#ffadb1" :autoBack="true" rightIcon="plus-circle" @rightClick="editAddress">
		</u-navbar>
		<u-gap height="44" bgColor="#bbb"></u-gap>
		<u-cell :label="item.address" :title="item.name + ' ' + item.tel" @click="editAddress(index)" :isLink="true"
			rightIcon="edit-pen" v-for="(item, index) in addressList" :key="index">
			<u-avatar v-if="item.isDef" icon="home-fill" fontSize="25" slot="icon" randomBgColor></u-avatar>
			<u-avatar v-if="!item.isDef" :text="item.name.slice(0,1)" fontSize="20" slot="icon" randomBgColor></u-avatar>
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
					url: this.$baseUrl + '/login-server/userAddress/getAddress',
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
								name: res.data.data[i].receiver,
								tel: res.data.data[i].tel,
								isDef: res.data.data[i].isDef,
							})
						}
					}),
				});
			},
			editAddress(i) {
				console.log(i);
				if (i == null) {
					uni.navigateTo({
						url: 'AddressEdit'
					});
				} else {
					uni.navigateTo({
						url: 'AddressEdit?id=' + this.addressList[i].addressId
					});
				}
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

<style scoped lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
	}
</style>