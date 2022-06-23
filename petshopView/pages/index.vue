<template>
	<view>
		<component :is="component"></component>
		<u-tabbar :list="tabbar" v-model="current" @change="changeTb" :placeholder="true" :fixed="true"
			:safeAreaInsetBottom="true">
			<u-tabbar-item text="HOME" icon="home" dot></u-tabbar-item>
			<u-tabbar-item text="放映厅" icon="photo" badge="3"></u-tabbar-item>
			<!-- 		<u-tabbar-item text="直播" icon="play-right"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account"></u-tabbar-item> -->
		</u-tabbar>
	</view>
</template>

<script>
	import Home from './pages/Home.vue'
	import Shop from './pages/Shop.vue'
	export default {
		components: {
			Home,
			Shop
		},
		data() {
			return {
				current: 0,
				tabbar: [{
						name: 'Home',
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
			// 	url: 'http://172.16.193.131:9001/login-server/login/test',
			// 	method: 'GET',
			// 	success: ((res) => {
			// 		console.log(res)
			// 	}),
				// fail: ((err) => {
				// 	uni.showToast({
				// 		title: '请求接口失败',
				// 		duration: 2000
				// 	})
				// 	reject(err)
				// })
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

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
</style>
