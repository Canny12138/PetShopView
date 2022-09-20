<template>
	<view>
		<view style="height: 25px; background-color: #ffadb1"></view>
		<component :is="component"></component>
		<u-tabbar :list="tabbar" v-model="current" @change="changeTb" :placeholder="false" :fixed="true"
			:safeAreaInsetBottom="true" activeColor="#fff7fc" inactiveColor="#ff5558" z-index="100"
			style="background-color: #ffadb1;">
			<u-tabbar-item text="主页" icon="home"></u-tabbar-item>
			<u-tabbar-item text="商品" icon="photo"></u-tabbar-item>
			<u-tabbar-item text="购物车" icon="photo"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="photo"></u-tabbar-item>
			<!-- 		<u-tabbar-item text="直播" icon="play-right"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account"></u-tabbar-item> -->
		</u-tabbar>
		<u-gap height="48" bgColor="#fff7fc"></u-gap>
	</view>
</template>

<script>
	import Home from './pages/Home.vue'
	import Shop2 from './pages/Shop2.vue'
	import Cart from './pages/Cart.vue'
	import Mine from './pages/Mine.vue'
	export default {
		components: {
			Home,
			Shop2,
			Cart,
			Mine,
		},
		data() {
			return {
				current: 0,
				tabbar: [{
						name: 'Home',
					},
					{
						name: 'Shop2',
					},
					{
						name: 'Cart',
					},
					{
						name: 'Mine',
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
			},
			// removeStorage() {
			// 	let self = this;
			// 	uni.removeStorage({
			// 		key: "user",
			// 		success() {
			// 			console.log('删除成功');
			// 			self.nickname = "未登录";
			// 			self.isLogin = false;
			// 		}
			// 	})
			// },
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
