<template>
	<view style="background-color: #fff7fc">
		<u-navbar title="商品详情" :autoBack="true" bgColor="#ffadb1">
		</u-navbar>
		<u-swiper :list="list1" @change="e => current = e.current" :autoplay="false" height="360px"
			style="margin-top: 44px;">
			<view slot="indicator" class="indicator">
				<view class="indicator__dot" v-for="(item, index) in list1" :key="index"
					:class="[index === current && 'indicator__dot--active']">
				</view>
			</view>
		</u-swiper>
		<uni-card style=" margin: 8px; padding-top:10px; background-color: #fff7fc">
			<text style="color: #ffb300; font-size: 30px;">¥{{goodTemp.price}}</text>
			<u-gap height="20"></u-gap>
			<text style="font-size: 20px; font-weight: 600;">{{goodTemp.goodName}}</text>
			<u-gap height="10"></u-gap>
			<text
				style="background-color: #eee7ec; border-radius: 8px; padding: 1px 15px 1px 15px; font-size: 15px;">{{goodTemp.storeName}}&nbsp进入></text>
			<u-divider text="分割线" :dot="true"></u-divider>
			<text style="font-size: 17px;">{{goodTemp.goodInfo}}</text>
			<u-gap height="30"></u-gap>
			<text>商品类型：{{goodTemp.type}}</text>
			<u-tabbar :fixed="true" :placeholder="true" :safeAreaInsetBottom="true">
				<view style="width: 12%; padding-top: 5px; background-color: #fff7fc;">
					<u-button color="#fff7fc"><u-icon label="店铺" labelSize="1" labelPos="bottom" size="25" name="bag"></u-icon></u-button>
				</view>
				<view style="width: 12%; padding-top: 5px; background-color: #fff7fc;">
					<u-button color="#fff7fc"><u-icon label="客服" labelSize="1" labelPos="bottom" size="25" name="kefu-ermai"></u-icon></u-button>
				</view>
				<view style="width: 12%; padding-top: 5px; background-color: #fff7fc;">
					<u-button color="#fff7fc"><u-icon label="收藏" labelSize="1" labelPos="bottom" size="25" name="star"></u-icon></u-button>
				</view>
				<view style="width: 32%; padding-top: 5px; background-color: #fff7fc;">
					<u-button text="加入购物车" color="linear-gradient(to right, #ffd7d8, #ffadb1)"
						style=" border-top-left-radius: 18px; border-bottom-left-radius: 18px;">
					</u-button>
				</view>
				<view style="width: 32%; padding-top: 5px; background-color: #fff7fc;">
					<u-button text="立即购买" color="linear-gradient(to right, #ffb300, #ffd320)"
						style="border-top-right-radius: 18px; border-bottom-right-radius: 18px;">
					</u-button>
				</view>
			</u-tabbar>
		</uni-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				GoodId: "12a9ae63-774c-4c45-994a-e52f2f3ea1be",
				current: 0,
				imgUrl: "http://150.158.85.93:88",
				list1: [
					'',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				goodTemp: [],
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id); //打印出上个页面传递的参数。
			// console.log(option.name); //打印出上个页面传递的参数。
			this.GoodId = option.id;
		},
		mounted() {
			this.getGood();
		},
		methods: {
			getGood() {
				uni.request({
					url: 'http://localhost:9001/store-server/goodInfoOV/getInfo',
					method: 'GET',
					data: {
						goodId: this.GoodId,
					},
					success: ((res) => {
						this.goodTemp = {
							goodId: res.data.data.goodId,
							goodName: res.data.data.goodName,
							goodInfo: res.data.data.goodInfo,
							img: this.imgUrl + res.data.data.img,
							isCollect: res.data.data.isCollect,
							price: res.data.data.price,
							storeName: res.data.data.storeName,
							type: res.data.data.type,
						};
						this.list1[0] = this.goodTemp.img;
						console.log(this.goodTemp);
					}),
				});
			},
		}
	}
</script>

<style lang="scss">
	.indicator {
		@include flex(row);
		justify-content: center;

		&__dot {
			height: 6px;
			width: 6px;
			border-radius: 100px;
			background-color: rgba(255, 255, 255, 0.35);
			margin: 0 5px;
			transition: background-color 0.3s;

			&--active {
				background-color: #ffffff;
			}
		}
	}
</style>
