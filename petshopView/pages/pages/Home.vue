<template>
	<view @tap="btnBClick">
		<view style="background-color: #ffadb1">
			<u-icon name="github-circle-fill" color="#ff5558" size="40" label="PetShop" labelColor="#ff5558"
				labelSize="19"></u-icon>
			<u-search shape="round" style="padding: 15px; padding-top: 5px;"></u-search>
		</view>
		<u-notice-bar :text="text1"></u-notice-bar>
		<u-swiper :list="list3" :autoplay="false" indicator indicatorMode="line" circular showTitle></u-swiper>
		<view class="u-demo-block" style="padding-left: 5px; margin-top: -10px; background-color: #fff7fc;">
			<u-scroll-list>
				<view class="scroll-list" style="text-align: center;">
					<view class="scroll-list__line" v-for="(item, index) in menuArr" :key="index">
						<view @click="toPage(item1.url)" class="scroll-list__line__item" v-for="(item1, index1) in item"
							:key="index1"
							:class="[(index1 === item.length - 1) && 'scroll-list__line__item--no-margin-right']">
							<image class="scroll-list__line__item__image" :src="item1.icon" mode="">
							</image>
							<text class="scroll-list__line__item__text">{{ item1.name }}</text>
						</view>
					</view>
				</view>
			</u-scroll-list>
		</view>
		<u-line color="#efedee"></u-line>
		<u-sticky offset-top="-45">
			<u-tabs :list="list4" lineWidth="30" :current="indexN" @change="indexToindexN" lineColor="#f56c6c"
				:activeStyle="{
		            color: '#303133',
		            fontWeight: 'bold',
		            transform: 'scale(1.05)',
		        }" :inactiveStyle="{
		            color: '#606266',
		            transform: 'scale(1)'
		        }" itemStyle="padding-top: 5px; padding-bottom: 5px; height: 35px; background-color: #fff7fc;">
			</u-tabs>
		</u-sticky>
		<!-- <view v-if="indexN == 0">page0</view>
		<view v-if="indexN == 1">page1</view>
		<view v-if="indexN == 2">page2</view> -->
		<swiper :current="indexN" @change="sIndexToindexN" class="swiper" style="margin: 0px">
			<swiper-item style="background-color: #fff7fc;">
				<u-grid :border="true" col="2">
					<u-grid-item v-for="(baseListItem,baseListIndex) in baseList" :key="baseListIndex">
						<u-icon :customStyle="{paddingTop:20+'rpx'}" :name="baseListItem.name" :size="22"></u-icon>
						<text class="grid-text">{{baseListItem.title}}</text>
					</u-grid-item>
				</u-grid>
			</swiper-item>
			<swiper-item style="background-color: #fff7fc;">
				<!-- <scroll-view style="height: 1300rpx;" scroll-y="true" refresher-enabled="true"
					:refresher-triggered="triggered" :refresher-threshold="100" refresher-background="#fff7fc"
					@refresherpulling="onPulling" @refresherrefresh="onRefresh" @refresherrestore="onRestore"
					@refresherabort="onAbort"> -->
				<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
					:scrollTop="scrollTop" :preLoadScreen="2" style="background-color: #fff7fc; margin-top: 5px">
					<u-list-item v-for="(item, index) in indexList" :key="index">
						<u-grid :border="false" col="2" style="height: 260px;">
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
				<!-- </scroll-view> -->
			</swiper-item>
			<swiper-item style="background-color: #fff7fc;">
				<scroll-view style="height: 1300px;" scroll-y="true" refresher-enabled="true"
					:refresher-triggered="triggered" :refresher-threshold="100" refresher-background="#fff7fc"
					@refresherpulling="onPulling" @refresherrefresh="onRefresh" @refresherrestore="onRestore"
					@refresherabort="onAbort">
					<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
						:scrollTop="scrollTop">
						<u-list-item v-for="(item, index) in indexList" :key="index">
							<u-cell :title="`商品-${index + 1}`">
								<u-avatar slot="icon" shape="square" size="80" :src="item.url"
									customStyle="margin: -3px 5px -3px 0"></u-avatar>
							</u-cell>
						</u-list-item>
						<u-transition :show="showBackTop" style="position: fixed; right: 50px; bottom: 100px">
							<u-avatar icon="arrow-up" fontSize="22" @click="backTop"></u-avatar>
						</u-transition>
					</u-list>
				</scroll-view>
			</swiper-item>
			<swiper-item>
				page3
			</swiper-item>
			<swiper-item>
				page4
			</swiper-item>
			<swiper-item>
				page5
			</swiper-item>
			<swiper-item>
				page6
			</swiper-item>
			<swiper-item>
				page7
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				swiperList: ['integral', 'kefu-ermai', 'coupon', 'gift', 'scan', 'pause-circle', 'wifi', 'email', 'list'],
				value1: 2,
				indexN: 1,
				pageIndex: 2,
				triggered: true,
				showBackTop: false,
				scrollTop: 0,
				curNow: 0,
				currentPage: 1,
				indexList: [],
				lineTemp: [],
				etc: 1,
				isLoad: true,
				status: 'loading',
				token: "",
				isCollect: true,
				imgUrl: "http://150.158.85.93:88",
				menuBaseUrl: 'https://cdn.uviewui.com/uview/menu/',
				text1: '欢迎来到PetShop！',
				list3: [{
					url: 'http://150.158.85.93:88/video/default.mp4',
					title: '昨夜星辰昨夜风，画楼西畔桂堂东',
					poster: 'http://150.158.85.93:81/cat.jpg'
				}, {
					url: 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-dc-site/094a9dc0-50c0-11eb-b680-7980c8a877b8.jpg',
					title: '身无彩凤双飞翼，心有灵犀一点通'
				}, {
					url: 'https://cdn.uviewui.com/uview/swiper/swiper3.png',
					title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
				}],
				baseList: [{
						name: 'photo',
						title: '图片'
					},
					{
						name: 'lock',
						title: '锁头'
					},
					{
						name: 'star',
						title: '星星'
					},
					{
						name: 'star',
						title: '星星'
					},
					{
						name: 'star',
						title: '星星'
					},
					{
						name: 'star',
						title: '星星'
					},
					{
						name: 'star',
						title: '星星'
					},
				],
				menuArr: [
					[{
							name: '附近',
							icon: 'https://cdn.uviewui.com/uview/menu/17.png',
							url: 'Map'
						},
						{
							name: '店铺',
							icon: 'http://150.158.85.93:88/img/Pet Store wide.png',
							url: 'Store'
						}, {
							name: '推荐',
							icon: 'https://cdn.uviewui.com/uview/menu/11.png',
							url: 'Recommend'
						}, {
							name: '特价秒杀',
							icon: 'https://cdn.uviewui.com/uview/menu/9.png',
							url: 'Sale'
						}, {
							name: '宠物用品',
							icon: 'https://cdn.uviewui.com/uview/menu/11.png',
							url: 'Test'
						}, {
							name: '分类',
							icon: 'https://cdn.uviewui.com/uview/menu/11.png'
						}, {
							name: '天猫美食',
							icon: 'https://cdn.uviewui.com/uview/menu/11.png'
						}, {
							name: '阿里健康',
							icon: 'https://cdn.uviewui.com/uview/menu/11.png'
						}, {
							name: '口碑生活',
							icon: 'https://cdn.uviewui.com/uview/menu/11.png'
						}
					],
				],
				list4: [{
					name: '关注'
				}, {
					name: '推荐',
					badge: {
						isDot: true
					}
				}, {
					name: '电影',
				}, {
					name: '科技'
				}, {
					name: '音乐'
				}, {
					name: '美食'
				}, {
					name: '文化'
				}, {
					name: '财经'
				}, {
					name: '手工'
				}],
				indexList: [],
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
			this.getStorage();
			this.firstLoad();
			// this.testGood();
			this._freshing = false;
			// setTimeout(() => {
			// this.triggered = true;
			// }, 1000)
		},
		methods: {
			testGood() {
				for (let j = 0; j < 20; j++) {
					this.lineTemp = [];
					for (let i = 0; i < 2; i++) {
						this.lineTemp.push({
							goodId: 111,
							goodName: "Test",
							img: "http://150.158.85.93:88/img/猫.jpg",
							price: 123,
							storeName: "旺角大学城店",
							isCollect: 1,
						});
					}
					this.indexList.push(this.lineTemp);
				}
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
						// categoryId: 1,
						// type: -1 * this.curNow + 1,
					},
					header: {
						// token: this.token,
					},
					success: ((res) => {
						// console.log(res);
						// this.lineTemp = res.data.data;
						// console.log(this.lineTemp);
						// console.log(this.lineTemp[0].good.goodName);
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
					url: 'pages/Good?id=' + id
				});
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
			click1(e) {
				console.log('click1', e);
			},
			click(name) {
				// this.$refs.uToast.success(`点击了第${name}个`)
			},
			toPage(url) {
				console.log(url);
				uni.navigateTo({
					url: "pages/" + url,
				});
			},
			backTop() {
				this.scrollTop = this.scrollTop == 0 ? -1 : 0;
			},
			indexToindexN(index) {
				this.indexN = index.index;
				// console.log(this.indexN);
			},
			sIndexToindexN(index) {
				this.indexN = index.detail.current;
				// console.log(this.indexN);
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
						// categoryId: 1,
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
					console.log("aaa");
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
