var auctionView = Vue.component('AuctionView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="경매 참여하기" description="경매 중인 작품을 보여줍니다."></v-breadcrumb>
            <div id="auction-list" class="container">
                <div class="row">
                    <div class="col-md-12 text-right" style="margin-bottom:20px;">
                        <router-link :to="{ name: 'auction.regsiter' }" class="btn btn-outline-secondary">경매 생성하기</router-link>
                    </div>
                </div>
                <div class="row" class="ddiivv1">
                    <div class="auction ddiivv2" v-for="item in auctions">
                        <div class="ddiivv3">
                            <img :src="item['작품정보']['work_url']" style="width:300px"/>
                        </div>
                        <div class="ddiivv4">
                            <h4 style="padding:5px 0 0 0">{{ item['작품정보']['workName'] }}</h4>
                            <p style="padding:0;margin:0">{{ calculateDate(item['endTime']) }}</p>
                            <router-link :to="{ name: 'auction.detail', params: { id: item['id'] }}" class="btn btn-block btn-secondary">자세히보기</router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `
        ,
    data() {
        return {
            auctions: []
        }
    },
    methods: {
        calculateDate(date) {
            var now = new Date();
            var endDate = new Date(date);
            var diff = endDate.getTime() - now.getTime();

            // 만약 종료일자가 지났다면 "경매 마감"을 표시한다.
            if (diff < 0) {
                return "경매 마감";
            } else {
                // UNIX Timestamp를 자바스크립트 Date객체로 변환한다.
                var d = new Date(diff);
                var days = d.getDate();
                var hours = d.getHours();
                var minutes = d.getMinutes();

                return "남은시간: " + days + "일 " + hours + "시간 " + minutes + "분";
            }
        }
    },
    mounted: function() {
        var scope = this;

        auctionService.findAll(function(data) {
            var result = data;

            // 각 경매별 작품 정보를 불러온다.
            function fetchData(start, end) {
                if (start == end) {
                    scope.auctions = result;
                } else {
                    var id = result[start]['auctionId'];
                    workService.findById(id, function(work) {

                        result[start]['작품정보'] = work;
                        fetchData(start + 1, end);
                    });
                }
            }
            fetchData(0, result.length);
        });
    }
});