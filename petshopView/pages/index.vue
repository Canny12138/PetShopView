<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			<text class="title">{{title}}</text>
			<navigator url="pages/shop/shop" open-type="redirect" hover-class="other-navigator-hover">
				<button type="default">在当前页打开</button>
			</navigator>
		</view>
		<router-view></router-view>
		<u-tabbar :list="tabBar" :placeholder="true" :fixed="false" 
			:safeAreaInsetBottom="true">
			<u-tabbar-item text="HOME" icon="home" dot></u-tabbar-item>
			<u-tabbar-item text="放映厅" icon="photo" badge="3"></u-tabbar-item>
	<!-- 		<u-tabbar-item text="直播" icon="play-right"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account"></u-tabbar-item> -->
		</u-tabbar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: "Hello",
				tabBar: [{
						pagePath: "pages/index",
						"text": "HOME"
					},
					{
						pagePath: "pages/pages/shop/shop"
					}
				],
				value2: 1,
			}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
