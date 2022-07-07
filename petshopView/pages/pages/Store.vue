<template>
	<view style="background-color: #fff7fc;">
		<u-navbar title="店铺详情" :autoBack="true" bgColor="#ffadb1" style="margin-top: 44px;">
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
						<image slot='cover' @click="clickStore(item.storeId)" :src="imgUrl" mode="aspectFill"
							style="width: 30%; height: 100px; float: left">
						</image>
						<view style="width: 65%; float: left; padding-left: 5%;">
							<text style="font-weight: bold; font-size: 14px;">{{item.storeName}}</text>
							<view>
								<u-icon name="star-fill" color="#ffbc10" v-for="index of item.rank" :key="index" style="float: left;"></u-icon>
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
				isCollect: true,
				imgUrl: "http://150.158.85.93:88/img/Pet Store.png",
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
					url: this.$baseUrl + '/store-server/storeOV/getAllStoreOV',
					method: 'POST',
					data: {},
					header: {
						token: this.token,
						"Content-Type": "application/x-www-form-urlencoded",
					},
					success: ((res) => {
						console.log(res);
						this.lineTemp = [];
						for (let j = 0; j < res.data.data.length; j++) {
							this.lineTemp.push({
								storeName: res.data.data[j].storeName,
								address: res.data.data[j].address,
								storeId: res.data.data[j].storeId,
								rank: res.data.data[j].rank,
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
