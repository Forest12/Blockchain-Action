var explorerAuctionDetailView = Vue.component('ExplorerDetailView', {
    template: `
    <div>
        <v-nav></v-nav>
        <v-breadcrumb title="Auction Explorer" description="블록체인에 기록된 경매내역을 보여줍니다."></v-breadcrumb>
        <div class="container">
            <explorer-nav></explorer-nav>
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-warning" v-if="contract && contract.highestBid == 0">
                        아직 경매에 참여한 이력이 없습니다.
                    </div>
                    <table class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th colspan="2"># {{ txsAddress }}</th>
                            </tr> 
                        </thead>
                        <tbody>
                            <tr>
                                <th width="20%">Contract Address</th>
                                <td>{{ txsAddress }}</td>
                            </tr>
                            <tr>
                                <th width="20%">작품</th>
                                <td><router-link :to="{ name: 'work.detail', params: { id: work['id'] }}">{{ work && work['이름'] }}</router-link></td>
                            </tr>
                            <tr>
                                <th>Status</th>
                                <td>
                                    <span class="badge badge-primary" v-if="contract && !contract.종료">Processing</span>
                                    <span class="badge badge-danger" v-if="contract && contract.종료">Ended</span>
                                </td>
                            </tr>
                            <tr>
                                <th>Start Time Time</th>
                                <td>{{ contract && contract.startTime.toLocaleString() }}</td>
                            </tr>
                            <tr>
                                <th>Expire Time</th>
                                <td>{{ contract && contract.endTime.toLocaleString() }}</td>
                            </tr>
                            <tr>
                                <th>Highest Bid</th>
                                <td>{{ contract && contract.highestBid }} ETH</td>
                            </tr>
                            <tr>
                                <th>Highest Bidder</th>
                                <td>
                                    <span v-if="contract && contract.highestBid == 0">-</span>
                                    <span v-if="contract && contract.highestBid != 0">{{ contract && contract.highestBidder }}</span>
                                </td>
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
            txsAddress: "",
            contract: null,
            work: {
                id: 0
            }
        }
    },
    mounted: async function(){
        var scope = this;
        /**
         * TODO 경매 컨트랙트로부터 경매 정보를 가져옵니다. 
         */
        scope.txsAddress = scope.$route.params.txsAddress;
        
        workService.findByTxs(scope.txsAddress, function(data){
            console.log(data);
            var user=""
            workService.findById(data.작품id, function(work){
                console.log(work);
                scope.work={"id": work.id, "이름":work.workName};
            })
            userService.findById(data.최고입찰자id, function(user){
                user=user.username
            })
            scope.contract ={"ended":data.종료,"startTime":data.경매시작시간,"endTime":data.경매종료시간,"highestBid":data.최고입찰액,"highestBidder":user}
        })
    }
})