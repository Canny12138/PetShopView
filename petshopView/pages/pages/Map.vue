<template>
	<view>
		<u-navbar title="附近商店" :autoBack="true" bgColor="#ffadb1"></u-navbar>
		<view class="page-body" style="margin-top: 44px;">
			<view class="page-section page-section-gap">
				<map style="width: 100%; height: 500px;" :latitude="latitude"
					:longitude="longitude" :markers="covers">
				</map>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: 0, // 使用 marker点击事件 需要填写id
				title: 'map',
				latitude: 24.6284512,
				longitude: 118.0919487,
				covers: [],
				//  [{
				// 	latitude: 24.6284512,
				// 	longitude: 118.0919487,
				// 	iconPath: 'http://150.158.85.93:88/img/pet store.png',
				// 	title: "旺角大学城店",
				// }, {
				// 	latitude: 24.633411,
				// 	longitude: 118.0970152,
				// 	iconPath: 'http://150.158.85.93:88/img/pet store.png',
				// 	title: "理工店",
				// }]
			}
		},
		mounted() {
			this.getMap();
		},
		methods: {
			getMap() {
				uni.request({
					url: '/api/store-server/storeMap/getAllStoreInMap',
					method: 'POST',
					success: ((res) => {
						console.log(res);
						// this.lineTemp = res.data.data;
						// console.log(this.lineTemp);
						// console.log(this.lineTemp[0].good.goodName);
						for (let i = 0; i < res.data.data.length; i++) {
							this.covers.push({
								latitude: res.data.data[i].latitude,
								longitude: res.data.data[i].longitude,
								title: res.data.data[i].storeName,
								iconPath: 'http://150.158.85.93:88/img/pet store.png',
							});
						}
					}),
				});
			},
		}
	}
</script>

<style>

</style>
