<template>
	<view style="background-color: #fff7fc;">
		<u-search shape="round" style="padding: 15px; padding-top: 5px; background-color: #ffadb1" @custom="toSearch">
		</u-search>
		<!-- <u-subsection :list="['精品宠物馆', '宠物用品馆']" mode="subsection" :current="curNow" activeColor="#ffadb1"
			@change="sectionChange"></u-subsection> -->
		<view>
			<u-button text="综合" icon="arrow-down"
				style="border: 0px; background-color: #fff7fc; width: 35%; float: left"></u-button>
			<u-button text="销量" style="border: 0px; background-color: #fff7fc; width: 45%; float: left;"></u-button>
			<u-button text="筛选" @click="getAllCategory" shape="circle" icon="list"
				style="border: 0px; background-color: #fff7fc; width: 20%; float: left; z-index: 20; ">
			</u-button>
		</view>
		<u-picker :show="show" ref="uPicker" :columns="columns" @confirm="confirm" @cancel="cancel" @close="close"
			@change="changeHandler" :closeOnClickOverlay="true"></u-picker>

		<scroll-view style="height: 1350rpx;" scroll-y="true" refresher-enabled="true" :refresher-triggered="triggered"
			:refresher-threshold="100" refresher-background="#fff7fc" @refresherpulling="onPulling"
			@refresherrefresh="onRefresh" @refresherrestore="onRestore" @refresherabort="onAbort">
			<u-list @scrolltolower="scrolltolower" @scrolltoupper="scrolltoupper" @scroll="scroll"
				:scrollTop="scrollTop" style="background-color: #fff7fc;">
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
									style="color: #ffb300; font-weight: bold; font-size: 18px;">\n\n¥{{item.price}}\n</text>
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
				member: "",
				isCollect: true,
				imgUrl: "http://150.158.85.93:88",
				show: false,
				tempRes: '',
				tempList: [],
				curCategory: 1,
				columns: [
					['全部', '宠物', '宠物用具'],
					['全部']
				],
				columnData: [
					['全部'],
					['全部', '猫', '狗'],
					['全部', '主粮', '零食', '玩具', '清洁', '服饰', '生活', '其他']
				]
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
					url: this.$baseUrl + '/product/productOV/page',
					method: 'GET',
					data: {
						page: this.currentPage,
						limit: 2,
						productName: this.searchValue,
						memberId: this.memberId,
						categoryId: this.curCategory,
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
						categoryId: this.curCategory,
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
			getAllCategory() {
				this.show = true;
				// this.getCategory(1);
				// this.getCategory(2);
			},
			// getCategory(level) {
			// 	uni.request({
			// 		url: this.$baseUrl + `/product/category/list/${level}`,
			// 		method: 'GET',
			// 		success: ((res) => {
			// 			console.log(res);
			// 			this.tempList = [];
			// 			for (let i = 0; i < res.data.data.length; i++) {
			// 				this.tempList.push(res.data.data[i].name);
			// 			}
			// 			if (level == 1) {
			// 				this.columns.push(this.tempList);
			// 				console.log(this.columns);
			// 			} else {
			// 				this.columnData.push(this.tempList);
			// 				console.log(this.columnData);
			// 			}
			// 		}),
			// 	});
			// },
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
			onAbort() {
				// console.log("onAbort");
			},
			onRestore() {
				this.triggered = 'restore'; // 需要重置
				// console.log("onRestore");
			},
			changeHandler(e) {
				const {
					columnIndex,
					value,
					values, // values为当前变化列的数组内容
					index,
					// 微信小程序无法将picker实例传出来，只能通过ref操作
					picker = this.$refs.uPicker
				} = e
				// 当第一列值发生变化时，变化第二列(后一列)对应的选项
				if (columnIndex === 0) {
					// picker为选择器this实例，变化第二列对应的选项
					picker.setColumnValues(1, this.columnData[index])
				}
			},
			// 回调参数为包含columnIndex、value、values
			confirm(e) {
				console.log('confirm', e);
				this.show = false;
				if (e.indexs[1] == 0) this.curCategory = e.indexs[0];
				else this.curCategory = e.indexs[0] * 2 + e.indexs[1];
				if (e.indexs[0] == 0 && e.indexs[1] == 0) this.curCategory = 1;
				console.log(this.curCategory);
				this.onRefresh();
			},
			cancel(e) {
				console.log('confirm', e)
				this.show = false
			},
			close(e) {
				console.log(e);
				this.show = false;
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
