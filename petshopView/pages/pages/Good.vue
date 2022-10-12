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
			<text style="color: #ffb300; font-size: 30px; float: left;">¥{{goodTemp.price}}</text>
			
			<view v-show="goodTemp.isSale == 1">
				<text
					style="margin-left: 20rpx; text-decoration: line-through; float: left;">原价¥{{goodTemp.priceNoSale}}</text>
				<text style="margin-left: 20rpx; float: left;">剩余时间:</text>
				<u-count-down :time="goodTemp.createTime + goodTemp.saleTime - new Date().getTime()" format="HH:mm:ss"
					style="font-weight: bold; "></u-count-down>
			</view>
			
			<u-gap height="20" style="width: 100%;"></u-gap>
			<text style="font-size: 20px; font-weight: 600;">{{goodTemp.goodName}}</text>
			<u-gap height="10"></u-gap>
			<text @click="clickStore(goodTemp.storeId)"
				style="background-color: #eee7ec; border-radius: 8px; padding: 1px 15px 1px 15px; font-size: 15px;">{{goodTemp.storeName}}&nbsp进入></text>
			<u-divider text="分割线" :dot="true"></u-divider>
			<text style="font-size: 17px;">{{goodTemp.goodInfo}}</text>
			<u-gap height="30"></u-gap>
			<text>商品类型：{{goodTemp.type}}</text>
			<u-tabbar :fixed="true" :placeholder="true" :safeAreaInsetBottom="true">
				<view style="width: 12%; padding-top: 5px; background-color: #fff7fc;">
					<u-button @click="clickStore(goodTemp.storeId)" color="#fff7fc">
						<u-icon label="店铺" labelSize="1" labelPos="bottom" size="25" name="bag"></u-icon>
					</u-button>
				</view>
				<view style="width: 12%; padding-top: 5px; background-color: #fff7fc;">
					<u-button color="#fff7fc">
						<u-icon label="客服" labelSize="1" labelPos="bottom" size="25" name="kefu-ermai"></u-icon>
					</u-button>
				</view>
				<view style="width: 12%; padding-top: 5px; background-color: #fff7fc;">
					<u-button v-show="isCollect == 0" @click="toCollect" color="#fff7fc">
						<u-icon label="收藏" labelSize="1" labelPos="bottom" size="25" name="star"></u-icon>
					</u-button>
					<u-button v-show="isCollect == 1" @click="delCollect" color="#fff7fc">
						<u-icon label="已收藏" labelSize="1" labelPos="bottom" size="25" name="star-fill"></u-icon>
					</u-button>
				</view>
				<view style="width: 32%; padding-top: 5px; background-color: #fff7fc;">
					<u-button text="加入购物车" color="linear-gradient(to right, #ffd7d8, #ffadb1)" @click="show = true"
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
		<view>
			<u-popup :show="show" @close="close" @open="open">
				<view style="height: 200px; background-color: #fff7fc;">
					<uni-card style="margin:0px; padding: 10px; background-color: #fff7fc">
						<image slot='cover' :src="goodTemp.img" mode="aspectFill"
							style="width: 30%; height: 100px; float: left">
						</image>
						<view style="width: 35%; float: left; padding-left: 5%;">
							<text style="font-weight: bold; font-size: 13px;">{{goodTemp.goodName}}</text>
							<text
								style="color: #ffb300; font-weight: bold; font-size: 15px;">\n¥{{goodTemp.price}}\n</text>
							<text>剩余{{goodTemp.stock}}件</text>
						</view>
						<u-number-box :min="1" :max="goodTemp.stock" v-model="carNum"
							style="width: 30%; margin-top: 10px; float:left"></u-number-box>
					</uni-card>
					<view style="padding-top: 5px; background-color: #fff7fc;">
						<view style="width: 40%; margin-right: 20px;float: right">
							<u-button text="加入购物车" shape="circle" color="linear-gradient(to right, #ffd7d8, #ffadb1)"
								@click="addCar_del">
							</u-button>
						</view>
					</view>
				</view>
			</u-popup>
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				GoodId: "1",
				current: 0,
				imgUrl: "http://150.158.85.93:88",
				list1: [
					'',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				goodTemp: [],
				carNum: 1,
				token: "",
				memberId: "",
				isCollect: 0,
				show: false,
				toastParams: {
					type: 'error',
					message: "",
				},
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id); //打印出上个页面传递的参数。
			// console.log(option.name); //打印出上个页面传递的参数。
			this.getStorage();
			this.GoodId = option.id;
			this.getGood();
		},
		mounted() {

		},
		methods: {
			getGood() {
				uni.request({
					url: this.$baseUrl + `/product/productOV/info`,
					method: 'GET',
					data: {
						id: this.GoodId,
						memberId: this.memberId,
					},
					header: {
						// token: this.token,
					},
					success: ((res) => {
						console.log(res);
						this.goodTemp = {
							goodId: res.data.data.id,
							goodName: res.data.data.productName,
							goodInfo: res.data.data.info,
							img: this.imgUrl + res.data.data.img,
							isCollect: res.data.data.isCollect,
							price: res.data.data.price,
							storeName: res.data.data.storeName,
							type: res.data.data.category,
							storeId: res.data.data.storeId,
							stock: res.data.data.stock,
							isSale: res.data.data.isSale,
							priceNoSale: res.data.data.priceNoSale,
							createTime: res.data.data.createTime,
							saleTime: (res.data.data.saleTime - 16) * 3600000,
						};
						this.list1[0] = this.goodTemp.img;
						console.log(this.goodTemp);
						this.isCollect = this.goodTemp.isCollect;
					}),
				});
			},
			toCollect() {
				this.toastParams.message = "";
				this.toastParams.type = "error";
				uni.request({
					url: this.$baseUrl + '/member/membercollect',
					method: 'POST',
					data: {
						productId: this.GoodId,
						memberId: this.memberId,
					},
					header: {
						// "Content-Type": "application/x-www-form-urlencoded",
						token: this.token,
					},
					success: ((res) => {
						this.toastParams.message = res.data.msg;
						if (res.data.code == 0) {
							console.log("收藏成功!");
							console.log(res);
							this.toastParams.type = "success";
							this.toastParams.message = "收藏成功"
							this.isCollect = 1;
						}
						this.showToast(this.toastParams);
					}),
				});
			},
			delCollect() {
				this.toastParams.message = "";
				this.toastParams.type = "error";
				uni.request({
					url: this.$baseUrl + '/member/membercollect/remove',
					method: 'DELETE',
					data: {
						productId: this.GoodId,
						memberId: this.memberId,
					},
					header: {
						"Content-Type": "application/x-www-form-urlencoded",
						token: this.token,
					},
					success: ((res) => {
						this.toastParams.message = res.data.msg;
						if (res.data.code == 0) {
							console.log("取消收藏");
							console.log(res);
							this.toastParams.type = "success";
							this.toastParams.message = "取消收藏"
							this.isCollect = 0;
						}
						this.showToast(this.toastParams);
					}),
				});
			},
			addCar() {
				uni.request({
					url: this.$baseUrl + '/member/membercart',
					method: 'POST',
					data: {
						productId: this.GoodId,
						memberId: this.memberId,
						number: this.carNum,
					},
					header: {
						token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						this.show = false;
						this.toastParams.message = res.data.msg;
						if (res.data.code == 0) {
							this.toastParams.type = "success";
							this.toastParams.message = "添加成功"
						} else this.toastParams.type = "error";
						this.showToast(this.toastParams);
					}),
				});
			},
			addCar_del() {
				uni.request({
					url: this.$baseUrl + '/member/membercart/remove',
					method: 'DELETE',
					data: {
						productId: this.GoodId,
						memberId: this.memberId,
					},
					header: {
						"Content-Type": "application/x-www-form-urlencoded",
						token: this.token,
					},
					success: ((res) => {
						this.addCar();
					}),
				});
			},
			clickStore(id) {
				console.log(id);
				uni.navigateTo({
					url: 'StorePage?id=' + id
				});
			},
			getStorage() {
				let self = this;
				uni.getStorage({
					key: "user",
					success(res) {
						self.token = res.data.token;
						self.memberId = res.data.memberId;
						console.log('获取成功', res);
					}
				})
			},
			open() {
				console.log('open');
			},
			close() {
				this.show = false
				console.log('close');
			},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
					complete() {
						params.url && uni.reLaunch({
							url: params.url
						})
					}
				})
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
