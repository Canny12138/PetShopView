<template class="wrap">
	<view>
		<u-navbar title="附近商店" :autoBack="true" bgColor="#ffadb1"></u-navbar>
		<view class="page-body" style="margin-top: 44px;">
			<view class="page-section page-section-gap">
				<map style="width: 100%; height: 500px;" @markertap="mapClick" :latitude="latitude"
					:longitude="longitude" :markers="covers">
				</map>
			</view>
		</view>
		<u-gap height="10" bgColor="#fff7fc"></u-gap>
		<scroll-view style="height: 800rpx;" scroll-y="true" refresher-enabled="true" :refresher-triggered="triggered"
			:refresher-threshold="100" refresher-background="#fff7fc" @refresherpulling="onPulling"
			@refresherrefresh="onRefresh" @refresherrestore="onRestore" @refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc;">
				<u-list-item v-for="(item, index) in lineTemp" :key="index">
					<uni-card style="margin:0px; padding: 10px; background-color: #fff7fc">
						<image slot='cover' @click="clickStore(item.storeId)" :src="imgUrl" mode="aspectFill"
							style="width: 30%; height: 100px; float: left">
						</image>
						<view style="width: 65%; float: left; padding-left: 5%;">
							<text style="font-weight: bold; font-size: 14px;">{{item.storeName}}</text>
							<view>
								<u-icon name="star-fill" color="#ffbc10" v-for="index of item.rank" :key="index"
									style="float: left;"></u-icon>
							</view>
							<text>\n{{item.address}}</text>
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
				id: 0, // 使用 marker点击事件 需要填写id
				title: 'map',
				latitude: 24.6284512,
				longitude: 118.0919487,
				covers: [],
				triggered: true,
				showBackTop: false,
				scrollTop: 0,
				searchValue: "",
				currentPage: 1,
				curStore: "",
				indexList: [],
				lineTemp: [],
				etc: 1,
				isLoad: true,
				status: 'loading',
				token: "",
				isCollect: true,
				imgUrl: "http://150.158.85.93:88/img/Pet Store.png",
			}
		},
		mounted() {
			this.getMap();
			this.getStorage();
			this.firstLoad();
			this._freshing = false;
		},
		methods: {
			getMap() {
				uni.request({
					url: this.$baseUrl + '/store/storeOV/getAllInMap',
					method: 'GET',
					success: ((res) => {
						console.log(res);
						// this.lineTemp = res.data.data;
						// console.log(this.lineTemp);
						// console.log(this.lineTemp[0].good.goodName);
						for (let i = 0; i < res.data.data.length; i++) {
							this.covers.push({
								id: i,
								latitude: res.data.data[i].latitude,
								longitude: res.data.data[i].longitude,
								iconPath: 'http://150.158.85.93:88/img/pet store.png',
								storeId: res.data.data[i].storeId,
								callout: {
									content: res.data.data[i].storeName,
									display: "ALWAYS",
								}
							});
						}
					}),
				});
			},
			getGood() {
				this.status = "loading";
				uni.request({
					url: this.$baseUrl + '/store/storeOV/page',
					method: 'GET',
					data: {
						limit: 100,
						page: 1,
						storeName: this.curStore,
					},
					// header: {
					// 	token: this.token,
					// 	"Content-Type": "application/x-www-form-urlencoded",
					// },
					success: ((res) => {
						console.log(res);
						this.lineTemp = [];
						for (let j = 0; j < res.data.data.total; j++) {
							this.lineTemp.push({
								storeName: res.data.data.list[j].storeName,
								address: res.data.data.list[j].address,
								storeId: res.data.data.list[j].storeId,
								rank: res.data.data.list[j].rank,
							});
						}
						this.status = "nomore";
						this.isLoad = false;
					}),
				});
			},
			clickStore(id) {
				console.log(id);
				uni.navigateTo({
					url: 'StorePage?id=' + id
				});
			},
			mapClick(e) {
				console.log(e.detail.markerId);
				this.curStore = this.covers[e.detail.markerId].callout.content;
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
					this.lineTemp = [];
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
		}
	}
</script>

<style lang="scss">
	.wrap {
		background-color: #fff7fc;
		min-height: 100vh;
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
		height: 800rpx;
		margin-left: 20px;
		margin-right: 20px;
	}
</style>
