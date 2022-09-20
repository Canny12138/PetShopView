<template>
	<view style="background-color: #fff7fc;">
		<u-navbar title="特价商品" :autoBack="true" bgColor="#ffadb1" style="margin-top: 44px;">
		</u-navbar>
		<u-search shape="round" style="padding: 15px; padding-top: 5px; background-color: #ffadb1" @custom="toSearch">
		</u-search>
		<scroll-view style="height: 1350rpx;" scroll-y="true" refresher-enabled="true" :refresher-triggered="triggered"
			:refresher-threshold="100" refresher-background="#fff7fc" @refresherpulling="onPulling"
			@refresherrefresh="onRefresh" @refresherrestore="onRestore" @refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc; margin-top: 5px">
				<u-list-item v-for="(item, index) in lineTemp" :key="index">
					<uni-card style="margin:0px; padding: 10px; background-color: #fff7fc">
						<image slot='cover' @click="clickGood(item.goodId)" :src="imgUrl + item.img" mode="aspectFill"
							style="width: 30%; height: 100px; float: left">
						</image>
						<view style="width: 60%; float: left; padding-left: 10%;">
							<text style="font-weight: bold; font-size: 13px;">{{item.goodName}}</text>
							<text style="color: #ffb300; font-weight: bold; font-size: 15px;">\n¥{{item.price}}</text>
							<text
								style="margin-left: 20rpx; text-decoration: line-through;">原价¥{{item.noSalePrice}}</text>
							<view>
								<text style="margin-right: 10rpx; float: left;">剩余时间:</text>
								<u-count-down :time="item.createTime + item.saleTime - new Date().getTime()"
									format="HH:mm:ss" style="font-weight: bold; "></u-count-down>
							</view>
							<text
								style="background-color: #eee7ec; font-size: 10px; border-radius: 8px; padding: 1px 10px 1px 10px; position: relative;">{{item.storeName}}&nbsp></text>
						</view>
					</uni-card>
				</u-list-item>
				<u-transition :show="showBackTop" style="position: fixed; right: 50px; bottom: 100px">
					<u-avatar icon="arrow-up" fontSize="22" @click="backTop"></u-avatar>
				</u-transition>
				<u-loadmore :status="status" />
			</u-list>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				triggered: true,
				showBackTop: false,
				scrollTop: 0,
				searchValue: "",
				currentPage: 1,
				indexList: [],
				lineTemp: [],
				etc: 1,
				isLoad: true,
				status: 'loading',
				token: "",
				memberId: "",
				isCollect: true,
				imgUrl: "http://150.158.85.93:88",
			}
		},
		mounted() {
			this.getStorage();
			this.firstLoad();
			this._freshing = false;
		},
		methods: {
			getGood() {
				this.status = "loading";
				uni.request({
					url: this.$baseUrl + '/product/productOV/getSale',
					method: 'GET',
					data: {
						limit: 100,
						memberId: this.memberId,
						page: 1,
					},
					header: {
						token: this.token,
						// "Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						this.lineTemp = [];
						for (let j = 0; j < res.data.data.length; j++) {
							this.lineTemp.push({
								goodId: res.data.data[j].id,
								goodName: res.data.data[j].productName,
								storeName: res.data.data[j].storeName,
								price: res.data.data[j].price,
								img: res.data.data[j].img,
								noSalePrice: res.data.data[j].noSalePrice,
								createTime: res.data.data[j].createTime,
								saleTime: (res.data.data[j].saleTime - 16) * 3600000,
							});
						}
						this.status = "nomore";
						this.isLoad = false;
					}),
				});
			},
			clickGood(id) {
				console.log(id);
				uni.navigateTo({
					url: 'Good?id=' + id
				});
			},
			collect() {
				if (this.isCollect) this.isCollect = false;
				else this.isCollect = true;
				console.log("666");
			},
			toSearch(value) {
				console.log(value);
				this.searchValue = value;
				this.onRefresh();
			},
			sectionChange(index) {
				this.curNow = index;
				this.onRefresh();
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
			click(name) {
				// this.$refs.uToast.success(`点击了第${name}个`)
			},
			backTop() {
				this.scrollTop = this.scrollTop == 0 ? -1 : 0;
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
			},
			firstLoad() {
				this.loadmore();
			},
			loadmore() {
				if (this.isLoad) {
					this.getGood();
				} else {
					console.log("到底了");
				}
			},
			onPulling(e) {
				// console.log("onpulling", e);
			},
			onRefresh() {
				if (this._freshing) return;
				this._freshing = true;
				setTimeout(() => {
					this.triggered = false;
					this._freshing = false;
					this.indexList = [];
					this.currentPage = 1;
					this.isLoad = true;
					this.loadmore();
				}, 1000)
			},
			onRestore() {
				this.triggered = 'restore'; // 需要重置
				// console.log("onRestore");
			},
			onAbort() {
				// console.log("onAbort");
			},
			// btnBClick() {
			// 	uni.$u.debounce(500);
			// },
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
</style>
