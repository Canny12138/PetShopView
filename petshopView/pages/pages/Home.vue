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
							<image class="scroll-list__line__item__image" :src="menuBaseUrl + item1.icon" mode="">
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
		        }"
				itemStyle="padding-left: 15px; padding-right:15px; padding-top: 5px; padding-bottom: 5px; height: 35px; background-color: #fff7fc;">
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
				<scroll-view style="height: 1300rpx;" scroll-y="true" refresher-enabled="true"
					:refresher-triggered="triggered" :refresher-threshold="100" refresher-background="#fff7fc"
					@refresherpulling="onPulling" @refresherrefresh="onRefresh" @refresherrestore="onRestore"
					@refresherabort="onAbort">
					<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
						:scrollTop="scrollTop" style="background-color: #fff7fc; margin-top: 5px">
						<u-list-item v-for="(item, index) in indexList" :key="index">
							<u-grid :border="false" @click="click" col="2">
								<u-grid-item v-for="(item, index) in item" :key="index">
									<uni-card style="width: 85%; height:250px; margin: 5px; background-color: #fff7fc">
										<image slot='cover' :src="item.url" mode="aspectFill"
											style="width: 150px; height: 130px">
										</image>
										<text>商品-{{index + 1}}口口口口口口口口口口口口口口口口</text>
										<text
											style="color: #ffb300;font-weight: bold; font-size: 18px;">\n¥8888\n</text>
										<text
											style="background-color: #eee7ec;border-radius: 8px; padding: 1px 10px 1px 10px; position: relative; top: 5px">旺角大学城店
											></text>
									</uni-card>
								</u-grid-item>
							</u-grid>
						</u-list-item>
						<u-transition :show="showBackTop" style="position: fixed; right: 50px; bottom: 100px">
							<u-avatar icon="arrow-up" fontSize="22" @click="backTop"></u-avatar>
						</u-transition>
					</u-list>
				</scroll-view>
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
				menuBaseUrl: 'https://cdn.uviewui.com/uview/menu/',
				text1: 'uView UI众多组件覆盖开发过程的各个需求，组件功能丰富，多端兼容。让您快速集成，开箱即用',
				list3: [{
					url: 'https://upos-sz-mirrorcos.bilivideo.com/upgcxcode/82/46/451864682/451864682-1-208.mp4?e=ig8euxZM2rNcNbNghWdVhwdlhbN1hwdVhoNvNC8BqJIzNbfq9rVEuxTEnE8L5F6VnEsSTx0vkX8fqJeYTj_lta53NCM=&uipk=5&nbs=1&deadline=1657011704&gen=playurlv2&os=ali02bv&oi=1866715013&trid=ab29843e88f84dd9aba38c42b0d62110T&mid=0&platform=html5&upsig=d37dbe8373e8b58ade99025aab7095db&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&bvc=vod&nettype=0&bw=221407&orderid=0,1&logo=80000000',
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
							icon: '17.png',
							url: 'Map'
						},
						{
							name: '猫咪',
							icon: '11.png'
						}, {
							name: '狗狗',
							icon: '11.png'
						}, {
							name: '宠物零食',
							icon: '11.png'
						}, {
							name: '宠物用品',
							icon: '11.png'
						}, {
							name: '分类',
							icon: '11.png'
						}, {
							name: '天猫美食',
							icon: '11.png'
						}, {
							name: '阿里健康',
							icon: '11.png'
						}, {
							name: '口碑生活',
							icon: '11.png'
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
			this.loadmore();
			this._freshing = false;
			// setTimeout(() => {
			// this.triggered = true;
			// }, 1000)
		},
		methods: {
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
				this.scrollTop = 0;
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
				this.scrollTop = e;
			},
			// loadmore() {
			// 	for (let i = 0; i < 30; i++) {
			// 		this.indexList.push({
			// 			url: this.urls[uni.$u.random(0, this.urls.length - 1)]
			// 		})
			// 	}
			// },
			loadmore() {
				for (let i = 0; i < 30; i++) {
					this.lineTemp = [];
					for (let j = 0; j < 2; j++) {
						this.lineTemp.push({
							url: this.urls[uni.$u.random(0, this.urls.length - 1)]
						});
					}
					this.indexList.push(this.lineTemp);
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
