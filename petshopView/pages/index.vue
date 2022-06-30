<template>
	<view>
		<view style="height: 25px; background-color: #ffadb1"></view>
		<component :is="component"></component>
		<u-tabbar :list="tabbar" v-model="current" @change="changeTb" :placeholder="true" :fixed="true"
			:safeAreaInsetBottom="true" activeColor="#fff7fc" inactiveColor="#ff5558" style="background-color: #ffadb1;">
			<u-tabbar-item text="HOME" icon="home" dot></u-tabbar-item>
			<u-tabbar-item text="商店" icon="photo" badge="3"></u-tabbar-item>
			<u-tabbar-item text="商店" icon="photo" badge="3"></u-tabbar-item>
			<u-tabbar-item text="商店" icon="photo" badge="3"></u-tabbar-item>
			<!-- 		<u-tabbar-item text="直播" icon="play-right"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account"></u-tabbar-item> -->
		</u-tabbar>
	</view>
</template>

<script>
	import Home from './pages/Home.vue'
	import Shop from './pages/Shop.vue'
	import Shop2 from './pages/Shop2.vue'
	export default {
		components: {
			Home,
			Shop,
			Shop2
		},
		data() {
			return {
				current: 0,
				tabbar: [{
						name: 'Home',
					},
					{
						name: 'Shop',
					},
					{
						name: 'Shop2',
					},
					{
						name: 'Shop',
					}
				],
				// value2: 1,
			}
		},
		mounted() {
			// uni.request({
			// 	url: 'http://172.16.193.151:9001/login-server/login/test',
			// 	method: 'GET',
			// 	success: ((res) => {
			// 		console.log(res)
			// 	}),
			// 	fail: ((err) => {
			// 		uni.showToast({
			// 			title: '请求接口失败',
			// 			duration: 2000
			// 		})
			// 		reject(err)
			// 	})
			// })
		},
		methods: {
			changeTb(index) {
				this.current = index
			}
		},
		computed: {
			component: function() {
				return this.tabbar[this.current].name
			}
		},
	}
</script>

<style lang="scss">
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	uni-view.u-tabbar-item {
		background-color: #ffadb1;
	}
</style>
