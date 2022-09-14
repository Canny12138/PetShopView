<template>
	<view>
		<u-navbar title="订单" bgColor="#ffadb1" leftIcon=""></u-navbar>
		<u-gap height="100" bgColor="#fff7fc"></u-gap>
		<uni-card style="margin: 5px; margin-bottom: 150rpx;background-color: #fff7fc">
			<text>旺角大学城店</text>
			<text style="float: right; color: #f56c6c">待收货</text>
			<u-cell v-for="i in 3">
				<image slot="icon" :src="img" mode="aspectFill" style="width: 120rpx; height: 120rpx;">
				</image>
				<text slot="title" style="padding-bottom: 50rpx; margin-left: 10rpx;">测试商品测试商品</text>
				<view slot="value" style="padding-bottom: 20rpx;">
					<text style="color: #ffb300;">¥6.99\n</text>
					<text style="padding-left: 40rpx; color: gray; font-size: small">x3</text>
				</view>
			</u-cell>
			<view style="width: 100%; float: right; margin: 10rpx">
				<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">实付款</text>
				<text
					style="float: right; padding: 10rpx; font-weight: bold; font-size: medium; color: #f56c6c">¥99.99</text>
			</view>
			<view style="width: 100%; float: right; margin: 10rpx">
				<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">收货信息:</text>
				<text
					style="width: 60%; float: right;">李先生，12345678901，福建省厦门市集美区后溪镇厦门理工学院学生公寓1期</text>
			</view>
			<view style="width: 100%; float: right; margin: 10rpx">
				<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">订单编号:</text>
				<text
					style="float: right; padding: 10rpx;">12345678987654321</text>
			</view>
		</uni-card>
		<u-tabbar :fixed="true" :placeholder="true" :safeAreaInsetBottom="true">
			<view style="width: 100%; background-color: #fff7fc">
				<u-button type="error" :plain="true" text="确认收货" shape="circle"
					style="margin-right: 20rpx; width: 30%; float: right; background-color: #fff7fc; ">
				</u-button>
			</view>
		</u-tabbar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: ['全部', '待付款', '待发货', '待收货', '待评价'],
				img: "http://150.158.85.93:81/pet/1.jpeg",
				// 或者如下，也可以配置keyName参数修改对象键名
				// list: [{name: '未付款'}, {name: '待评价'}, {name: '已付款'}],
				curNow: 0,
			}
		},
		methods: {
			sectionChange(index) {
				this.curNow = index;
			},
			swiperChange(index) {
				this.curNow = index.detail.current;
			},
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #fff7fc;
	}

	.scroll-list {
		@include flex(column);

		&__line {
			@include flex;
			margin-top: 10px;

			&__item {
				margin-right: 15px;

				&__image {
					width: 61px;
					height: 48px;
				}

				&__text {
					margin-top: 5px;
					color: $u-content-color;
					font-size: 12px;
					text-align: center;
				}

				&--no-margin-right {
					margin-right: 0;
				}
			}
		}
	}

	.swiper {
		height: 1300rpx;
		margin-left: 20px;
		margin-right: 20px;
	}

	.u-page {
		padding: 0;
	}

	.u-demo-block__title {
		padding: 10px 0 2px 15px;
	}

	.swipe-action {
		&__content {
			padding: 25rpx 0;

			&__text {
				font-size: 15px;
				color: $u-main-color;
				padding-left: 30rpx;
			}
		}
	}
</style>
