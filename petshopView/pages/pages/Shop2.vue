<template>
	<view @tap="btnBClick">
		<u-search shape="round" style="padding: 15px; padding-top: 5px;background-color: #ffadb1"></u-search>
		<scroll-view style="height: 1300rpx;" scroll-y="true" refresher-enabled="true" :refresher-triggered="triggered"
			:refresher-threshold="100" refresher-background="#fff7fc" @refresherpulling="onPulling"
			@refresherrefresh="onRefresh" @refresherrestore="onRestore" @refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc; margin-top: 5px">
				<u-list-item v-for="(item, index) in indexList" :key="index">
					<!-- <u-cell :title="`商品-${index + 1}`">
						<u-avatar slot="icon" shape="square" size="80" :src="item.url"
							customStyle="margin: -3px 5px -3px 0"></u-avatar>
					</u-cell> -->
					<u-grid :border="false" @click="click" col="2">
						<u-grid-item v-for="(item, index) in item" :key="index">
							<uni-card style="width: 85%; height:250px; margin: 5px; background-color: #fff7fc">
								<!-- <image slot='cover' :src="item.url" mode="aspectFill"
									style="width: 150px; height: 130px">
								</image> -->
								<!-- <text>商品-{{index + 1}}口口口口口口口口口口口口口口口口</text> -->
								<text>{{item.goodName}}</text>
								<text style="color: #ffb300;font-weight: bold; font-size: 18px;">\n¥8888\n</text>
								<text
									style="background-color: #eee7ec;border-radius: 8px; padding: 1px 10px 1px 10px; position: relative; top: 5px">旺角大学城店 ></text>
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
				urls: [
					'http://150.158.85.93:81/pet/1.jpeg',
					'http://150.158.85.93:81/pet/2.jpeg',
					'http://150.158.85.93:81/pet/3.jpeg',
					'http://150.158.85.93:81/pet/4.jpeg',
					'http://150.158.85.93:81/pet/5.jpeg',
					'http://150.158.85.93:81/pet/6.jpeg',
					'http://150.158.85.93:81/pet/7.jpeg',
					'http://150.158.85.93:81/pet/8.jpeg',
					'http://150.158.85.93:81/pet/9.jpeg',
					'http://150.158.85.93:81/pet/10.jpeg',
					'http://150.158.85.93:81/pet/11.jpeg',
					'http://150.158.85.93:81/pet/12.jpeg',
				]
			}
		},
		mounted() {
			this.loadmore();
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
				console.log("a")
				uni.request({
					url: 'http://172.16.193.151:9001/store-server/goodOV/getGoodOVByPage',
					method: 'GET',
					data: {
						pageNum: this.currentPage,
						pageSize: 2,
						goodName: "",
					},
					success: ((res) => {
						// console.log(res.data.data);
						this.lineTemp = res.data.data;
						console.log(this.lineTemp);
						// console.log(this.lineTemp[0].good.goodName);
						for (let j = 0; j < 2; j++) {
							this.lineTemp.push({
								goodName: this.lineTemp[j].goodName,
								img: this.lineTemp[j].img,
								price: this.lineTemp[j].price,
								storeName: this.lineTemp[j].storeName,
								// url: this.urls[uni.$u.random(0, this.urls.length - 1)]
							});
						}
						this.indexList.push(this.lineTemp);
					}),
					// fail: ((err) => {
					// 	uni.showToast({
					// 		title: '请求接口失败',
					// 		duration: 2000
					// 	})
					// 	reject(err)
					// })
				});
				this.currentPage ++ ;
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
			loadmore() {
				for (let i = 0; i < 7; i++) {
					this.lineTemp = [];
					this.getGood();

				}
				console.log(this.indexList);
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
			btnBClick() {
				uni.$u.debounce(500);
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
</style>
