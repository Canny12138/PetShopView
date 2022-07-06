<template>
	<view style="background-color: #fff7fc;">
		<u-navbar title="我的收藏" :autoBack="true" bgColor="#ffadb1"></u-navbar>
		<u-empty v-show="isEmpty" mode="favor" icon="http://cdn.uviewui.com/uview/empty/data.png"
			style="height: 1300rpx; margin-top:45px">
		</u-empty>
		<scroll-view style="height: 1300rpx; margin-top:45px" scroll-y="true" refresher-enabled="true"
			:refresher-triggered="triggered" :refresher-threshold="100" refresher-background="#fff7fc"
			@refresherpulling="onPulling" @refresherrefresh="onRefresh" @refresherrestore="onRestore"
			@refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc; margin-top: 5px">
				<u-list-item v-for="(item, index) in indexList" :key="index">
					<u-grid :border="false" @click="click" col="2">
						<u-grid-item v-for="(item, index) in item" :key="index">
							<uni-card @click="clickGood(item.goodId)"
								style="width: 85%; height:250px; margin: 5px; background-color: #fff7fc">
								<image slot='cover' :src="item.img" mode="aspectFill"
									style="width: 100%; height: 130px">
								</image>
								<text style="font-weight: bold; font-size: 15px;">{{item.goodName}}</text>
								<text
									style="color: #ffb300;font-weight: bold; font-size: 18px;">\n\n¥{{item.price}}\n</text>
								<text
									style="background-color: #eee7ec; border-radius: 8px; padding: 1px 10px 1px 10px; position: relative; top: 5px">{{item.storeName}}&nbsp></text>
								<u-icon name="bookmark-fill" size="40" color="#ffb300" v-show="item.isCollect == 1"
									style="position: absolute; right: 15px; bottom: 218px"></u-icon>
							</uni-card>
						</u-grid-item>
					</u-grid>
				</u-list-item>
				<u-transition :show="showBackTop" style="position: fixed; right: 50px; bottom: 100px">
					<u-avatar icon="arrow-up" fontSize="22" @click="backTop"></u-avatar>
				</u-transition>
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
				currentPage: 1,
				indexList: [],
				lineTemp: [],
				etc: 1,
				isLoad: true,
				token: "",
				isCollect: true,
				isEmpty: true,
				imgUrl: "http://150.158.85.93:88",
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
					url: '/api/login-server/collect/getCollectPageByUserId',
					method: 'POST',
					data: {
						pageNum: this.currentPage,
						pageSize: 2,
					},
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
						for (let j = 0; j < res.data.data.length; j++) {
							this.lineTemp.push({
								goodId: res.data.data[j].goodId,
								goodName: res.data.data[j].goodName,
								img: this.imgUrl + res.data.data[j].img,
								price: res.data.data[j].price,
								storeName: res.data.data[j].storeName,
								isCollect: res.data.data[j].isCollect,
							});
						}
						if (res.data.data.length == 1) {
							this.lineTemp.push({
								goodName: "广告位出租",
								img: 'http://150.158.85.93:81/pet/1.jpeg',
								price: 8888,
								storeName: "广告店",
								isCollect: 1,
							});
						}
						this.indexList.push(this.lineTemp);
					}),
				});
				this.currentPage++;
				if (this.currentPage > this.etc) this.isLoad = false;
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
					url: '/api/login-server/collect/getCollectPageByUserId',
					method: 'POST',
					data: {
						pageNum: 1,
						pageSize: 2,
					},
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
					for (let i = 0; this.isLoad && i < 10; i++) {
						this.getGood();
					}
					console.log(this.indexList);
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
					this.firstLoad();
				}, 1000)
			},
			onRestore() {
				this.triggered = 'restore'; // 需要重置
			},
			onAbort() {},
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
