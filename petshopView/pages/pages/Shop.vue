<template>
	<view @tap="btnBClick">
		<u-search shape="round" style="padding: 15px; padding-top: 5px;background-color: #ffadb1"></u-search>
		<scroll-view style="height: 1300rpx;" scroll-y="true" refresher-enabled="true" :refresher-triggered="triggered"
			:refresher-threshold="100" refresher-background="white" @refresherpulling="onPulling"
			@refresherrefresh="onRefresh" @refresherrestore="onRestore" @refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" :scrollTop="currentN">
				<u-transition :show="true" style="position: fixed; right: 50px; bottom: 100px">
					<u-avatar icon="arrow-up" fontSize="22" @click="backTop"></u-avatar>
				</u-transition>
				<u-list-item v-for="(item, index) in indexList" :key="index">
					<u-cell :title="`商品-${index + 1}`">
						<u-avatar slot="icon" shape="square" size="80" :src="item.url"
							customStyle="margin: -3px 5px -3px 0"></u-avatar>
					</u-cell>
				</u-list-item>
			</u-list>
		</scroll-view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				triggered: true,
				indexList: [],
				scrollTop: 0,
				currentN: 0,
				urls: [
					'https://cdn.uviewui.com/uview/album/1.jpg',
					'https://cdn.uviewui.com/uview/album/2.jpg',
					'https://cdn.uviewui.com/uview/album/3.jpg',
					'https://cdn.uviewui.com/uview/album/4.jpg',
					'https://cdn.uviewui.com/uview/album/5.jpg',
					'https://cdn.uviewui.com/uview/album/6.jpg',
					'https://cdn.uviewui.com/uview/album/7.jpg',
					'https://cdn.uviewui.com/uview/album/8.jpg',
					'https://cdn.uviewui.com/uview/album/9.jpg',
					'https://cdn.uviewui.com/uview/album/10.jpg',
				]
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		mounted() {
			this.loadmore();
			this._freshing = false;
			// setTimeout(() => {
			// this.triggered = true;
			// }, 1000)
		},
		methods: {
			scrolltolower() {
				this.loadmore()
			},
			backTop() {
				// console.log(this.currentN);
				// this.currentN = 0;
				// uni.pageScrollTo({
				// 	scrollTop: 0,
				// 	duration: 300
				// });
			},
			loadmore() {
				for (let i = 0; i < 30; i++) {
					this.indexList.push({
						url: this.urls[uni.$u.random(0, this.urls.length - 1)]
					})
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
