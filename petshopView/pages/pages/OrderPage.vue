<template>
	<view>
		<u-navbar title="订单" bgColor="#ffadb1" :autoBack="true"></u-navbar>
		<u-gap height="100" bgColor="#fff7fc"></u-gap>
		<view v-for="(item, index) in orderList" :key="index">
			<view v-show="item.orderStatus == 0" style="padding-left: 40rpx;">
				<text style="font-size: 40rpx; font-weight: bold">等待买家付款</text>
				<u-count-down :time="item.createTime + 300000 - new Date().getTime()" format="HH:mm:ss"
					style="font-weight: bold"></u-count-down>
			</view>
			<uni-card style="margin: 5px; margin-bottom: 150rpx;background-color: #fff7fc">
				<text style="float: right; color: #f56c6c">{{getOrderStatus(item.orderStatus)}}</text>
				<u-cell v-for="(item, index) in item.productList" :key="index">
					<image slot="icon" :src="item.img" mode="aspectFill" style="width: 120rpx; height: 120rpx;">
					</image>
					<text slot="title" style="padding-bottom: 30rpx; margin-left: 10rpx;">{{item.productName}}</text>
					<text slot="title"
						style="font-size: small; color: #6a6a6a; padding-bottom: 10rpx; margin-left: 10rpx;">{{item.storeName}}</text>
					<view slot="value" style="padding-bottom: 20rpx;">
						<text style="color: #ffb300;">¥{{item.price}}\n</text>
						<text style="padding-left: 40rpx; color: gray; font-size: small">x{{item.number}}</text>
					</view>
				</u-cell>
				<view style="width: 100%; float: right; margin: 10rpx">
					<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">实付款</text>
					<text
						style="float: right; padding: 10rpx; font-weight: bold; font-size: medium; color: #f56c6c">¥{{item.price.toFixed(2)}}</text>
				</view>
				<view style="width: 100%; float: right; margin: 10rpx">
					<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">收货信息:</text>
					<text style="width: 60%; float: right;">{{item.name}}，{{item.phone}}，{{item.address}}</text>
				</view>
				<view style="width: 100%; float: right; margin: 10rpx">
					<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">订单编号:</text>
					<text style="float: right; padding: 10rpx;">{{item.orderId}}</text>
				</view>
				<view style="width: 100%; float: right; margin: 10rpx">
					<text style="float: left; padding: 10rpx; font-weight: bold; font-size: medium;">下单时间:</text>
					<text
						style="float: right; padding: 10rpx;">{{$u.timeFormat(item.createTime, 'yyyy-mm-dd hh:MM:ss')}}</text>
				</view>
			</uni-card>
		</view>
		<u-tabbar :fixed="true" :placeholder="true" :safeAreaInsetBottom="true">
			<view style="width: 100%; background-color: #fff7fc">
				<u-button type="error" @click="clickButton(orderStatus, orderId, price)" :plain="true"
					:text="getStatusButton(this.orderStatus)" shape="circle"
					style="margin-right: 20rpx; width: 30%; float: right; background-color: #fff7fc;">
				</u-button>
				<u-button type="error" @click="toReturnOrder()" v-show="this.orderStatus > 0 && this.orderStatus < 4"
					:plain="true" text="申请退款" shape="circle"
					style="margin-right: 20rpx; width: 30%; float: right; background-color: #fff7fc; ">
				</u-button>
			</view>
		</u-tabbar>
		<u-toast ref="uToast"></u-toast>
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
				orderId: 1000017,
				price: "",
				toastParams: {
					type: "success",
					message: "",
				},
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id); //打印出上个页面传递的参数。
			// console.log(option.name); //打印出上个页面传递的参数。
			this.getStorage();
			this.getWallet();
			this.orderId = option.id;
			// this.orderStatus = option.status;
			// console.log(this.orderId);
			this.getOrder();
		},
		// mounted() {
		// 	this.getStorage();
		// 	this.getOrder();
		// },
		methods: {
			getOrder() {
				if (this.curNow != 0) this.orderStatus = this.curNow - 1;
				uni.request({
					url: this.$baseUrl + '/product/orderOV/getOrder',
					method: 'GET',
					data: {
						page: 1,
						limit: 100,
						memberId: this.memberId,
						order: "desc",
						orderField: "id",
						id: this.orderId,
					},
					header: {
						token: this.token,
					},
					success: ((res) => {
						console.log(res);
						this.orderList = [];
						this.productList = [];
						for (let j = 0; j < res.data.data.list[0].products.length; j++) {
							this.productList.push({
								productName: res.data.data.list[0].products[j]
									.productName,
								storeName: res.data.data.list[0].products[j].storeName,
								img: this.imgUrl + res.data.data.list[0].products[j].img,
								price: res.data.data.list[0].products[j].price,
								number: res.data.data.list[0].products[j].number,
							});
						}
						this.orderList.push({
							productList: this.productList,
							orderId: res.data.data.list[0].orderId,
							price: res.data.data.list[0].price,
							orderStatus: res.data.data.list[0].orderStatus,
							createTime: res.data.data.list[0].createTime,
							address: res.data.data.list[0].receiveAddress.province + " " + res.data
								.data.list[0].receiveAddress.city + " " + res.data.data.list[0]
								.receiveAddress.region + " " + res.data.data.list[0].receiveAddress
								.detailAddress,
							name: res.data.data.list[0].receiveAddress.name,
							phone: res.data.data.list[0].receiveAddress.phone,
						});
						this.orderStatus = res.data.data.list[0].orderStatus;
						this.price = res.data.data.list[0].price;
						console.log(this.orderList);
					}),
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
						this.toastParams.message = "支付成功";
						this.showToast(this.toastParams);
						break;
					case 1:
						this.changeStatus(id, 3);
						this.toastParams.message = "确认收货";
						this.showToast(this.toastParams);
						break;
					case 2:
						this.changeStatus(id, 3);
						this.toastParams.message = "确认收货";
						this.showToast(this.toastParams);
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
			toReturnOrder() {
				uni.navigateTo({
					url: 'ReturnOrder?id=' + this.orderId
				});
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
			showToast(params) {
				this.$refs.uToast.show({
					...params,
				})
			},
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
