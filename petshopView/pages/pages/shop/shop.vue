<template>
<!-- 	<view v-if="currentTab===0">
		首页</view>
	<view v-if="currentTab===1">
		放映厅</view>
	<view v-if="currentTab===2">
		直播</view>
	<view v-show="currentTab===3">
		我的</view> -->
	<u-tabbar :value="currentTab" @change="change1" :fixed="false" :placeholder="false" :safeAreaInsetBottom="false">
		<u-tabbar-item text="首页" icon="home" @click="currentTab=0"></u-tabbar-item>
		<u-tabbar-item text="放映厅" icon="photo" @click="currentTab=1"></u-tabbar-item>
		<u-tabbar-item text="直播" icon="play-right" @click="currentTab=2"></u-tabbar-item>
		<u-tabbar-item text="我的" icon="account" @click="currentTab=3"></u-tabbar-item>
	</u-tabbar>
</template>
<script>
	export default {
		data() {
			return {
				currentTab: 0,
			}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>
