var walletInfoView = Vue.component('walletInfoView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="마이페이지" description="지갑을 생성하거나 작품을 업로드 할 수 있습니다."></v-breadcrumb>
            <div class="container">
                <v-mypage-nav></v-mypage-nav>
                <div id="mywallet-info" class="row">
                    <div class="col-md-12 mt-5">
                        <div class="card">
                            <table class="table table-bordered">
                                <tr>
                                    <th>총보유 ETH</th>
                                    <td class="text-right">{{ wallet['balance'] }} ETH</td>
                                    <td colspan="2">
                                        <button type="button" class="btn btn-secondary" v-on:click="charge()" v-bind:disabled="isCharging">{{ isCharging ? "충전중" : "ETH 충전하기"}}</button>
                                    </td>
                                </tr>
                                <tr>
                                    <th>내 지갑주소</th>
                                    <td class="text-right">{{ wallet['address'] }}</td>
                                    <th>충전 회수</th>
                                    <td class="text-right">{{ wallet['chargeNum'] }}회</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            wallet: {
                id: 0,
                "ownerId": 15,
                "address": "",
                "balance": 0,
                "charge_num": 0
            },
            isCharging: false, // 현재 코인을 충전하고 있는 중인지 확인하는 변수
            sharedState: store.state
        }
    },
    methods: {
        // ETH 충전하기
        charge: function() {
            var scope = this;
            scope.isCharging = true;
            console.log("옴");
            console.log(this.wallet['address']);
            walletService.chargeEther(this.wallet['address'], function(response) {
                scope.isCharging = false;

                scope.wallet.chargeNum = response.chargeNum;
                scope.wallet.balance = response.balance

                // alert("코인이 충전 되었습니다.");
                swal({
                    title: "Coin Charge",
                    text: "코인이 충전 되었습니다.",
                    icon: "success",
                });
                
                scope.fetchWalletInfo();
            })
        },
        // 지갑 정보 가져오기
        fetchWalletInfo: function() {
            var scope = this;

            walletService.findById(this.sharedState.user.id, function(data) {
                // TODO API 호출로 지갑 정보를 가져와 보여줍니다. 
                // web3를 사용하여 잔액을 조회해 보는 것도 포함해보도록 합니다. 
                scope.wallet.ownerId = data.ownerId;
                scope.wallet.address = data.address;
                scope.wallet.balance = data.balance;
                scope.wallet.chargeNum = data.chargeNum;
            });
        }
    },
    mounted: function() {
        this.fetchWalletInfo();
    }
})