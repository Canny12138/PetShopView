<template>
	<view>
		<u-navbar title="订单" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="48" bgColor="#fff7fc"></u-gap>
		<u-subsection :list="list" :current="curNow" @change="sectionChange" activeColor="#f56c6c"></u-subsection>
		<!-- 		<swiper :current="curNow" @change="swiperChange" class="swiper" style="margin: 0px">
			<swiper-item style="background-color: #fff7fc;"> -->
		<view v-for="(item, index) in orderList" :key="index">
			<uni-card style="margin: 5px; background-color: #fff7fc">
				<view @click="clickOrder(item.orderId, item.orderStatus)">
					<text>订单编号 {{item.orderId}}</text>
					<text style="float: right; color: #f56c6c">{{getOrderStatus(item.orderStatus)}}</text>
					<u-cell v-for="(item, index) in item.productList" :key="index">
						<image slot="icon" :src="item.img" mode="aspectFill" style="width: 120rpx; height: 120rpx;">
						</image>
						<text slot="title"
							style="padding-bottom: 30rpx; margin-left: 10rpx;">{{item.productName}}</text>
						<text slot="title"
							style="font-size: small; color: #6a6a6a; padding-bottom: 10rpx; margin-left: 10rpx;">{{item.storeName}}</text>
						<view slot="value" style="padding-bottom: 20rpx;">
							<text style="color: #ffb300;">¥{{item.price}}\n</text>
							<text style="padding-left: 40rpx; color: gray; font-size: small">x{{item.number}}</text>
						</view>
					</u-cell>
				</view>
				<view style="width: 40%; float: right">
					<text
						style="float: right; padding: 10rpx; font-weight: bold; font-size: medium;">实付款¥{{item.price.toFixed(2)}}</text>
					<u-button type="error" @click="clickButton(item.orderStatus, item.orderId, item.price)"
						:plain="true" :text="getStatusButton(item.orderStatus)" shape="circle"
						style="margin-top: 10rpx; margin-bottom: 20rpx; background-color: #fff7fc; ">
					</u-button>
				</view>
			</uni-card>
		</view>
		<!-- 			</swiper-item>
			<swiper-item style="background-color: #fff7fc;">
				page1
			</swiper-item>
			<swiper-item style="background-color: #fff7fc;">
				page2
			</swiper-item>
			<swiper-item style="background-color: #fff7fc;">
				page3
			</swiper-item>
			<swiper-item style="background-color: #fff7fc;">
				page4
			</swiper-item>
		</swiper> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: ['全部', '待付款', '待发货', '待收货', '待评价'],
				imgUrl: "http://150.158.85.93:88",
				// 或者如下，也可以配置keyName参数修改对象键名
				// list: [{name: '未付款'}, {name: '待评价'}, {name: '已付款'}],
				token: "",
				memberId: "",
				money: "",
				curNow: 0,
				orderList: [],
				productList: [],
				orderStatus: "",
			}
		},
		mounted() {
			this.getStorage();
			this.getWallet();
			this.getOrder();
		},
		methods: {
			getOrder() {
				this.orderStatus = "";
				if (this.curNow != 0) this.orderStatus = this.curNow - 1;
				uni.request({
					url: this.$baseUrl + '/product/orderOV/getOrder',
					method: 'GET',
					data: {
						page: 1,
						limit: 100,
						orderStatus: this.orderStatus,
						memberId: this.memberId,
						order: "desc",
						orderField: "id",
					},
					header: {
						token: this.token,
					},
					success: ((res) => {
						console.log(res);
						this.orderList = [];
						for (let i = 0; i < res.data.data.list.length; i++) {
							this.productList = [];
							for (let j = 0; j < res.data.data.list[i].products.length; j++) {
								this.productList.push({
									productName: res.data.data.list[i].products[j]
										.productName,
									storeName: res.data.data.list[i].products[j].storeName,
									img: this.imgUrl + res.data.data.list[i].products[j].img,
									price: res.data.data.list[i].products[j].price,
									number: res.data.data.list[i].products[j].number,
								});
							}
							this.orderList.push({
								productList: this.productList,
								orderId: res.data.data.list[i].orderId,
								price: res.data.data.list[i].price,
								orderStatus: res.data.data.list[i].orderStatus,
							});
						}
						console.log(this.orderList);
					}),
				});
			},
			clickOrder(id, status) {
				console.log(id, status);
				uni.navigateTo({
					url: 'OrderPage?id=' + id + '&?status=' + status
				});
			},
			getOrderStatus(code) {
				switch (code) {
					case 0:
						return "待付款";
						break;
					case 1:
						return "待发货";
						break;
					case 2:
						return "待收货";
						break;
					case 3:
						return "待评价";
						break;
					case 4:
						return "已完成";
						break;
					case -1:
						return "支付超时";
						break;
					case -2:
						return "已申请退款";
						break;
					case -3:
						return "已退款";
						break;
					case -4:
						return "已取消";
						break;
					default:
						break;
				}
			},
			getStatusButton(code) {
				switch (code) {
					case 0:
						return "去支付";
						break;
					case 1:
						return "确认收货";
						break;
					case 2:
						return "确认收货";
						break;
					case 3:
						return "去评价";
						break;
					case 4:
						return "查看评价";
						break;
					case -1:
						return "删除订单";
						break;
					case -2:
						return "查看退款进度";
						break;
					case -3:
						return "删除订单";
						break;
					case -4:
						return "删除订单";
						break;
					default:
						break;
				}
			},
			clickButton(code, id, price) {
				switch (code) {
					case 0:
						this.changeStatus(id, 1);
						this.pay(price);
						break;
					case 1:
						this.changeStatus(id, 3);
						break;
					case 2:
						this.changeStatus(id, 3);
						break;
					case 3:
						this.toScore(id);
						break;
					case 4:
						this.toScorePage(id);
						break;
					case -1:
						return "删除订单";
						break;
					case -2:
						return "查看退款进度";
						break;
					case -3:
						return "删除订单";
						break;
					case -4:
						return "删除订单";
						break;
					default:
						break;
				}
			},
			changeStatus(id, status) {
				uni.request({
					url: this.$baseUrl + '/order/order',
					method: 'PUT',
					data: {
						id: id,
						orderStatus: status,
					},
					header: {
						token: this.token,
					},
					success: ((res) => {
						this.getOrder();
					})
				})
			},
			toScore(id) {
				console.log(id);
				uni.navigateTo({
					url: 'ScoreEdit?id=' + id
				});
			},
			toScorePage(id) {
				console.log(id);
				uni.navigateTo({
					url: 'ScorePage?id=' + id
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
			getWallet() {
				let self = this;
				uni.getStorage({
					key: "wallet",
					success(res) {
						console.log('获取钱包成功', res);
						self.money = res.data.money;
					}
				})
			},
			pay(price) {
				let self = this;
				uni.setStorage({
					key: "wallet",
					data: {
						money: self.money - price,
					},
					success() {
						console.log('储存钱包成功');
						self.getWallet();
					}
				});
			},
			sectionChange(index) {
				this.curNow = index;
				this.getOrder();
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
