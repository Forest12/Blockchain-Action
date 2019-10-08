var explorerAuctionView = Vue.component('ExplorerView', {
    template: `
    <div>
        <v-nav></v-nav>
        <v-breadcrumb title="Auction Explorer" description="블록체인에 기록된 경매내역을 보여줍니다."></v-breadcrumb>
        <div class="container">
            <explorer-nav></explorer-nav>
            <div class="row">
                <div class="col-md-12">
                <div style="height: 400px;" v-if="load === true">
                                    <div class="semipolar-spinner" :style="spinnerStyle" style="margin:100px auto;">
                                        <div class="ring"></div>
                                        <div class="ring"></div>
                                        <div class="ring"></div>
                                        <div class="ring"></div>
                                        <div class="ring"></div>
                                    </div>
                                </div>
                    <table class="table table-bordered table-striped" v-if="load === false">
                        <thead>
                            <tr>
                                <th>Auction</th>
                                <th>Status</th>
                                <th>Highest Bid</th>
                                <th>Highest Bidder</th>
                                <th>Expire Date</th>
                            </tr>
                        </thead>
                        <tbody>          
                            <tr v-for="(item, index) in contracts">
                                <td><router-link :to="{ name: 'explorer.auction.detail', params: { txsAddress: item }}">{{ item | truncate(15) }}</router-link></td>
                                <td>
                                    <span class="badge badge-primary" v-if="items[index] && !items[index].ended">Processing</span>
                                    <span class="badge badge-danger" v-if="items[index] && items[index].ended">Ended</span>
                                </td>
                                <td>{{ items[index] && items[index].higestBid }} ETH</td>
                                <td>
                                    <span v-if="items[index] && items[index].higestBid != 0">{{ items[index] && items[index].higestBidder | truncate(15) }}</span>
                                    <span v-if="items[index] && items[index].higestBid == 0">-</span>
                                </td>
                                <td>{{ items[index] && items[index].endTime.toLocaleString() }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    `,
    data(){
        return {
            contracts: [],
            items: [],
            load:true
        }
    },
    mounted: async function(){
        /**
         * TODO 
         * 1. AuctionFactory 컨트랙트로부터 경매컨트랙트 주소 리스트를 가져옵니다.
         * 2. 각 컨트랙트 주소로부터 경매의 상태(state) 정보를 가져옵니다. 
         * */ 
        var scope = this;
        scope.load=true;
        auctionService.findAll(function(data){
                var result = data;
                console.log(result);
                var address=[];
                // 각 경매별 작품 정보를 불러온다.
                function fetchData(start, end){
                    if(start == end) {
                        scope.contracts = address;
                    } else {
                        scope.load=false;
                        address.push(result[start]['txsAddress']);
                        var id = result[start]['id'];

                        auctionService.findById(id, function(work){
                            // console.log(work);
                            var higestBidder;
                            userService.findById(work.최고입찰자id,function(user){
                                console.log(user);
                                higestBidder = user.username;
                                scope.items.push({
                                    "ended" : work.종료, "higestBid":(work.최고입찰액)/10**18, "higestBidder":higestBidder,"endTime":work.경매종료시간 
                                });
                            });
                            
                            fetchData(start+1, end);
                        });
                    }
                }
                fetchData(0, data.length);
            
        });
    }
})