<template>
	<view style="background-color: #fff7fc;">
		<u-navbar title="店铺详情" :autoBack="true" bgColor="#ffadb1" style="margin-top: 44px;">
		</u-navbar>
		<uni-card style="margin:0px; padding: 10px; background-color: #fff7fc">
			<image slot='cover' :src="storeImg" mode="aspectFill" style="width: 30%; height: 100px; float: left">
			</image>
			<view style="width: 65%; float: left; padding-left: 5%;">
				<text style="font-weight: bold; font-size: 14px;">{{store.storeName}}\n</text>
				<view>
					<u-icon name="star-fill" color="#ffbc10" v-for="index of store.rank" :key="index"
						style="float: left;"></u-icon>
				</view>
				<text style="text-decoration: underline;">\n地址：{{store.address}}\n\n</text>
				<text>{{store.info}}</text>
			</view>
		</uni-card>
		<u-subsection :list="['精品宠物馆', '宠物用品馆']" mode="subsection" :current="curNow" activeColor="#ffadb1"
			@change="sectionChange"></u-subsection>
		<scroll-view style="height: 1350rpx;" scroll-y="true" refresher-enabled="true" :refresher-triggered="triggered"
			:refresher-threshold="100" refresher-background="#fff7fc" @refresherpulling="onPulling"
			@refresherrefresh="onRefresh" @refresherrestore="onRestore" @refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc; margin-top: 5px">
				<u-list-item v-for="(item, index) in indexList" :key="index">
					<u-grid :border="false" col="2">
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
				<u-loadmore :status="status" />
			</u-list>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				storeId: "5",
				store: {
					storeName: "",
					address: "",
					info: "",
					rank: "",
				},
				triggered: true,
				showBackTop: false,
				scrollTop: 0,
				curNow: 0,
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
				storeImg: "http://150.158.85.93:88/img/Pet Store.png",
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			console.log(option.id); //打印出上个页面传递的参数。
			// console.log(option.name); //打印出上个页面传递的参数。
			this.getStorage();
			this.storeId = option.id;
			this.getStore();
			this.firstLoad();
		},
		mounted() {
			// this.getStorage();
			// this.getStore();
			// this.firstLoad();
			this._freshing = false;
		},
		methods: {
			getStore() {
				uni.request({
					url: this.$baseUrl + `/store/storeOV/${this.storeId}`,
					method: 'GET',
					// header: {
					// 	token: this.token,
					// 	"Content-Type": "application/x-www-form-urlencoded",
					// },
					success: ((res) => {
						console.log(res);
						this.store.storeName = res.data.data.storeName;
						this.store.address = res.data.data.address;
						this.store.info = res.data.data.info;
						this.store.rank = res.data.data.rank;
					}),
				});
			},
			getGood() {
				this.status = "loading";
				uni.request({
					url: this.$baseUrl + '/product/productOV/page',
					method: 'GET',
					data: {
						page: this.currentPage,
						limit: 2,
						productName: this.searchValue,
						memberId: this.memberId,
						storeId: this.storeId,
						// type: -1 * this.curNow + 1,
					},
					header: {
						// token: this.token,
					},
					success: ((res) => {
						this.lineTemp = [];
						for (let j = 0; j < res.data.data.list.length; j++) {
							this.lineTemp.push({
								goodId: res.data.data.list[j].id,
								goodName: res.data.data.list[j].productName,
								img: this.imgUrl + res.data.data.list[j].img,
								price: res.data.data.list[j].price,
								storeName: res.data.data.list[j].storeName,
								isCollect: res.data.data.list[j].isCollect,
							});
						}
						if (res.data.data.list.length == 1) {
							this.lineTemp.push({
								goodName: "广告位出租",
								img: 'http://150.158.85.93:81/pet/1.jpeg',
								price: 8888,
								storeName: "广告店",
								isCollect: 1,
							});
						}
						this.indexList.push(this.lineTemp);
						this.status = "nomore";
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
				uni.request({
					url: this.$baseUrl + '/product/productOV/page',
					method: 'GET',
					data: {
						page: this.currentPage,
						limit: 2,
						productName: this.searchValue,
						storeId: this.storeId,
						// type: -1 * this.curNow + 1,
					},
					header: {
						// token: this.token,
					},
					success: ((res) => {
						this.etc = Math.ceil(res.data.data.total / 2);
						console.log(res.data.data.list.length);
						this.loadmore();
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
					this.firstLoad();
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
