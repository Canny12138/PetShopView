<template>
	<view style="background-color: #fff7fc;">
		<u-navbar title="购物车" :autoBack="true" bgColor="#ffadb1"></u-navbar>
		<u-empty v-show="isEmpty" mode="car" icon="http://cdn.uviewui.com/uview/empty/car.png"
			style="height: 1300rpx; margin-top:45px">
		</u-empty>
		<scroll-view style="height: 1300rpx; margin-top:45px" scroll-y="true" refresher-enabled="true"
			:refresher-triggered="triggered" :refresher-threshold="100" refresher-background="#fff7fc"
			@refresherpulling="onPulling" @refresherrefresh="onRefresh" @refresherrestore="onRestore"
			@refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc; margin-top: 5px">
				<u-list-item v-for="(item, index) in lineTemp" :key="index">
					<u-swipe-action>
						<u-swipe-action-item @click="delCar(index)" :options="options1">
							<uni-card style="margin:0px; padding: 10px; background-color: #fff7fc">
								<image slot='cover' @click="clickGood(item.goodId)" :src="item.img" mode="aspectFill"
									style="width: 30%; height: 100px; float: left">
								</image>
								<view style="width: 35%; float: left; padding-left: 5%;">
									<text style="font-weight: bold; font-size: 13px;">{{item.goodName}}</text>
									<text
										style="color: #ffb300; font-weight: bold; font-size: 15px;">\n¥{{item.price}}\n</text>
									<text
										style="background-color: #eee7ec; font-size: 10px; border-radius: 8px; padding: 1px 10px 1px 10px; position: relative;">{{item.storeName}}&nbsp></text>
								</view>
								<u-number-box :min="1" :max="item.stock" v-model="item.number"
									@change="setCar(item.goodId, item.number, $event.value, index)"
									style="width: 30%; margin-top: 10px; float:left"></u-number-box>
							</uni-card>
						</u-swipe-action-item>
					</u-swipe-action>
				</u-list-item>
				<u-transition :show="showBackTop" style="position: fixed; right: 30px; bottom: 60px; z-index: 100;">
					<u-avatar icon="arrow-up" fontSize="22" @click="backTop"></u-avatar>
				</u-transition>
			</u-list>
		</scroll-view>
		<u-tabbar :fixed="true" :placeholder="true" :safeAreaInsetBottom="true" z-index="10">
			<view
				style="width: 5%; padding-left: 35%; padding-top: 2px; color: #606266; font-size: 30px; background-color: #ffeefb;">
				¥</view>
			<u-count-to :startVal="prePrice" :endVal="curPrice" :decimals="2" separator=","
				style="width: 30%; padding-top: 10px; background-color: #ffeefb;">
			</u-count-to>
			<view style="width: 30%; padding-top: 5px; background-color: #ffeefb;">
				<u-button text="立即购买" shape="circle" color="linear-gradient(to right, #ffb300, #ffd320)">
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
				triggered: true,
				showBackTop: false,
				scrollTop: 0,
				currentPage: 1,
				indexList: [],
				lineTemp: [],
				etc: 1,
				isLoad: true,
				token: "",
				isEmpty: true,
				prePrice: 0,
				curPrice: 0,
				imgUrl: "http://150.158.85.93:88",
				options1: [{
					text: '删除',
					style: {
						backgroundColor: '#f56c6c'
					}
				}],
				toastParams: {
					type: 'error',
					message: "",
				},
			}
		},
		mounted() {
			this.getStorage();
			this.firstLoad();
			this._freshing = false;
			// setTimeout(() => {
			// this.triggered = true;
			// }, 1000)
			// uni.$u.http.setConfig((config) => {
			// 	/* config 为默认全局配置*/
			// 	config.baseURL = `https://172.16.193.151:9001`; /* 根域名 */
			// 	return config
			// });
			// this.getGood();
		},
		methods: {
			getGood() {
				uni.request({
					url: '/api/login-server/cart/getCartByUserId',
					method: 'POST',
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						// this.lineTemp = res.data.data;
						// console.log(this.lineTemp);
						// console.log(this.lineTemp[0].good.goodName);
						this.lineTemp = [];
						for (let i = 0; i < res.data.data.length; i++) {
							this.lineTemp.push({
								goodId: res.data.data[i].goodId,
								goodName: res.data.data[i].goodName,
								img: this.imgUrl + res.data.data[i].img,
								price: res.data.data[i].price,
								storeName: res.data.data[i].storeName,
								stock: res.data.data[i].stock,
								number: res.data.data[i].number,
							});
							this.curPrice += res.data.data[i].price * res.data.data[i].number;
						}
						this.indexList.push(this.lineTemp);
					}),
				});
				this.isLoad = false;
			},
			clickGood(id) {
				console.log(id);
				uni.navigateTo({
					url: 'Good?id=' + id
				});
			},
			setCar(goodId, preNum, curNum, i) {
				console.log(goodId, preNum, curNum, i);
				this.prePrice = this.curPrice;
				this.curPrice -= this.lineTemp[i].price * preNum;
				this.curPrice += this.lineTemp[i].price * curNum;
				uni.request({
					url: '/api/login-server/cart/updateCart',
					method: 'POST',
					data: {
						goodId: this.lineTemp[i].goodId,
						number: curNum,
					},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
					}),
				});
			},
			delCar(i) {
				console.log('click', i);
				uni.showModal({
					title: '温馨提示',
					content: '确定要删除吗？',
					success: res => {
						if (res.confirm) {
							console.log("进入删除");
							uni.request({
								url: '/api/login-server/cart/deleteCart',
								method: 'POST',
								data: {
									goodId: this.lineTemp[i].goodId,
								},
								header: {
									token: this.token,
									"Content-Type": "application/x-www-form-urlencoded",
								},
								success: ((res) => {
									console.log(res);
									console.log("删除成功");
									this.toastParams.message = res.data.message;
									this.toastParams.type = "success";
									this.showToast(this.toastParams);
									this.onRefresh();
								}),
							});
						}
					}
				})
			},
			getStorage() {
				let self = this;
				uni.getStorage({
					key: "user",
					success(res) {
						self.token = res.data.token;
						console.log('获取成功', res);
					}
				})
			},
			click(name) {
				// this.$refs.uToast.success(`点击了第${name}个`)
			},
			backTop() {
				this.scrollTop = 0;
			},
			scrolltolower() {
				this.loadmore();
			},
			scrolltoupper() {
				this.showBackTop = false;
			},
			scroll(e) {
				// console.log(e);
				this.showBackTop = true;
				this.scrollTop = e;
			},
			firstLoad() {
				uni.request({
					url: '/api/login-server/cart/getCartByUserId',
					method: 'POST',
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						if (res.data.statusCode == "400") {
							this.isLoad = false;
							this.isEmpty = true;
						} else {
							this.etc = res.data.etc;
							this.isEmpty = false;
							this.loadmore();
						}
					}),
				});
			},
			loadmore() {
				if (this.isLoad) {
					this.getGood();
				} else {
					console.log("到底了");
				}
			},
			onPulling(e) {},
			onRefresh() {
				if (this._freshing) return;
				this._freshing = true;
				setTimeout(() => {
					this.triggered = false;
					this._freshing = false;
					this.indexList = [];
					this.currentPage = 1;
					this.isLoad = true;
					this.prePrice = this.curPrice;
					this.curPrice = 0;
					this.firstLoad();
				}, 1000)
			},
			onRestore() {
				this.triggered = 'restore'; // 需要重置
			},
			onAbort() {},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
				})
			},
		}
	}
</script>

<style lang="scss">
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
