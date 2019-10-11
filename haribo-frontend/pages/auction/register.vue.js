/**
 * 화면: 경매 등록하기
 */

var auctionRegisterView = Vue.component('AuctionRegisterView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="경매 등록하기" description="새로운 경매를 등록합니다."></v-breadcrumb>
            <div class="row">
                    <div class="col-md-8 mx-auto" style="height: 400px;" v-if="load === true">
                           <div class="semipolar-spinner" :style="spinnerStyle" style="margin:100px auto;">
                                    <div class="ring"></div>
                                    <div class="ring"></div>
                                    <div class="ring"></div>
                                    <div class="ring"></div>
                                    <div class="ring"></div>
                                </div>
                        </div>
                <div class="col-md-6 mx-auto" v-if="load === false">
                    <div class="card">
                        <div class="card-header">신규 경매 등록하기</div>
                        <div class="card-body">
                            <div v-if="!registered">
                                <div class="form-group">
                                    <label id="privateKey">지갑 개인키</label>
                                    <input id="privateKey" v-model="before.input.privateKey" type="text" class="form-control" placeholder="지갑 개인키를 입력해주세요.">
                                </div>
                                <div class="form-group">
                                    <label id="work">작품 선택</label>
                                    <select v-model="before.selectedWork" class="form-control" @change="selectArt()">
                                        <option v-for="work in before.works" :value="work.id">{{ work['workName'] }}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label id="minPrice">최저가</label>
                                    <div class="input-group">
                                        <input id="minPrice" v-model="before.input.minPrice" type="text" class="form-control" placeholder="최저가를 입력해주세요.">
                                        <div class="input-group-append">
                                            <div class="input-group-text">ETH</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label id="startDateL">경매 시작일시</label>
                                    <input id="startDate" v-model="before.input.startDate" type="text" class="form-control" placeholder="yyyy-MM-dd HH:mm:ss, 예: 2019-04-21 21:00:00">
                                </div>
                                <div class="form-group">
                                    <label id="untilDateL">경매 종료일시</label>
                                    <input id="untilDate" v-model="before.input.untilDate" type="text" class="form-control" placeholder="yyyy-MM-dd HH:mm:ss, 예: 2019-05-03 12:00:00">
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <button class="btn btn-sm btn-primary" v-on:click="register" v-bind:disabled="isCreatingContract">{{ isCreatingContract ? "계약을 생성하는 중입니다." : "경매 등록하기" }}</button>
                                    </div>
                                    <div class="col-md-6 text-right">
                                        <button class="btn btn-sm btn-outline-secondary" v-on:click="goBack">이전으로 돌아가기</button>
                                    </div>
                                </div>
                            </div>
                            <div v-if="registered">
                                <div class="alert alert-success" role="alert">
                                    경매가 생성되었습니다.
                                </div>
                                <table class="table table-bordered mt-5">
                                    <tr>
                                        <th>경매작품</th>
                                        <td>{{ after.work['workName'] }}</td>
                                    </tr>
                                    <tr>
                                        <th>최저가</th>
                                        <td>{{ after.result['lowestPrice'] }} ETH</td>
                                    </tr>
                                    <tr>
                                        <th>시작일시</th>
                                        <td>{{ before.input.startDate }}</td>
                                    </tr>
                                    <tr>
                                        <th>종료일시</th>
                                        <td>{{ before.input.untilDate }}</td>
                                    </tr>
                                    <tr>
                                        <th>컨트랙트 주소</th>
                                        <td>{{ after.result['txsAddress'] }}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            isCreatingContract: false,
            registered: false,
            sharedStates: store.state,
            artidoverlap: false,
            load: false,
            // 경매 등록전 입력값
            before: {
                works: [],
                selectedWork: null,
                input: {

                },
                auctions: [],
            },

            // 경매 등록 후 등록 결과 완료 표시 용도
            after: {
                result: {},
                work: {}
            }
        }
    },
    methods: {
        goBack: function() {
            this.$router.go(-1);
        },
        selectArt: function() {

            var scope = this;
            console.log(scope.before.selectedWork);

            for (let i = 0; i < scope.before.auctions.length; i++) {
                if (scope.before.auctions[i].auctionId == scope.before.selectedWork) {
                    scope.artidoverlap = true;
                    swal({
                        title: "Already Registerd",
                        text: "이미 있는 경매품입니다",
                        icon: "warning",
                    });
                    scope.before.selectedWork = null;
                    return;
                }
            }
            scope.artidoverlap = false;
        },
        register: function() {
            /**
             * 컨트랙트를 호출하여 경매를 생성하고
             * 경매 정보 등록 API를 호출합니다. 
             */

            var scope = this;
            scope.load = true;
            // 1. 내 지갑 주소를 가져옵니다.
            walletService.findAddressById(this.sharedStates.user.id, function(walletAddress) {

                // 2. 경매 컨트랙트를 블록체인에 생성합니다.
                // components/auctionFactory.js의 createAuction 함수를 호출합니다.
                // TODO createAuction 함수의 내용을 완성합니다. 
                var startD = new Date(scope.before.input.startDate);
                var endD = new Date(scope.before.input.untilDate);

                if (startD.getTime() > new Date().getTime()) {
                    // alert("시작 날짜는 현재 시간보다 이후일 수 없습니다!");
                    swal("Start Date", "시작 날짜는 현재 시간보다 이후일 수 없습니다!", "warning");
                    scope.load = false;
                    document.getElementById("startDate").focus();
                    return;
                }
                if (endD.getTime() <= new Date().getTime()) {
                    // alert("종료 날짜는 현재 시간보다 이전일 수 없습니다!");
                    swal("End Date", "종료 날짜는 현재 시간보다 이전일 수 없습니다!", "warning");
                    scope.load = false;
                    document.getElementById("untilDate").focus();
                    return;
                }
                if (scope.artidoverlap) {
                    // alert("종료 날짜는 현재 시간보다 이전일 수 없습니다!");
                    swal("End Date", "이미 있는 작품입니다", "warning");
                    scope.load = false;
                    document.getElementById("work").focus();
                    return;
                }
                this.isCreatingContract = true;

                createAuction({
                    workId: scope.before.selectedWork,
                    minValue: scope.before.input.minPrice,
                    startTime: new Date(scope.before.input.startDate).getTime(),
                    endTime: new Date(scope.before.input.untilDate).getTime()
                }, walletAddress, scope.before.input.privateKey, function(log) {
                    console.log(log[0]);
                    var contractAddress = log[0];

                    var data = {
                        "auctionCreatorId": scope.sharedStates.user.id,
                        "auctionId": scope.before.selectedWork,
                        "startTime": new Date(scope.before.input.startDate),
                        "endTime": new Date(scope.before.input.untilDate),
                        "lowestPrice": Number(scope.before.input.minPrice),
                        "txsAddress": contractAddress,
                    }


                    // 3. 선택한 작업 정보를 가져옵니다.
                    workService.findById(scope.before.selectedWork, function(result) {
                        scope.after.work = result;
                    });

                    // 4. 생성한 경매를 등록 요청 합니다.
                    auctionService.register(data, function(result) {
                        console.log(data);
                        // alert("경매가 등록되었습니다.");
                        swal("Regist Auction", "경매가 등록되었습니다.", "success");
                        scope.registered = true;
                        scope.load = false;
                        scope.after.result = result;
                    });

                    this.isCreatingContract = false;
                });
            });
        }
    },
    mounted: function() {
        var scope = this;

        // 내 작품 목록 가져오기
        workService.findWorksByOwner(this.sharedStates.user.id, function(result) {
            scope.before.works = result;
        });
        auctionService.findAllByUser(this.sharedStates.user.id, function(result) {
            console.log(result);
            scope.before.auctions = result;
        });
    }
})