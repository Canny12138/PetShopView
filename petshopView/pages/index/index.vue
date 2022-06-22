<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			<text class="title">{{title}}</text>
			<u-button type="primary" text="确定"></u-button>
		</view>
		<u-tabbar
			:value="value2"
			:placeholder="false"
			@change="name => value2 = name"
			:fixed="false"
			:safeAreaInsetBottom="false"
		>
			<u-tabbar-item text="首页" icon="home" dot ></u-tabbar-item>
			<u-tabbar-item text="放映厅" icon="photo" badge="3"></u-tabbar-item>
			<u-tabbar-item text="直播" icon="play-right" ></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account" ></u-tabbar-item>
		</u-tabbar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello',
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
