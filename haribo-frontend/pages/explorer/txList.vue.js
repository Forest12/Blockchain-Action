var explorerTxListView = Vue.component('ExplorerTxListView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다."></v-breadcrumb>
            <div class="container">
                <explorer-nav></explorer-nav>
                <div class="row" v-if="transactions.length == 0">
                    <div class="col-md-8 mx-auto">
                        <div class="alert alert-warning">No transaction recorded at. #{{ block && block.number }} blocks</div>
                    </div>
                </div>
                <div class="row">
                    <div id="transactions" class="col-md-8 mx-auto">
                        <div class="card shadow-sm">
                            <div class="card-header">Transactions</div>
                            <div class="card-body">
                                <div class="row tx-info" v-for="item in transactions">
                                    <div class="col-md-2">Tx</div>
                                    <div class="col-md-4">
                                        <router-link :to="{name: 'explorer.tx.detail', params: { hash: item.hash }}" class="tx-number">{{ item.hash | truncate(10) }}</router-link>
                                        <p class="tx-timestamp">{{ item.timeSince }}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><label class="text-secondary">From</label> {{ item.from | truncate(10) }}</p>
                                        <p><label class="text-secondary">To</label> {{ item.to | truncate(10) }}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data(){
        return {
            lastReadBlock : 0,
            transactions: [],
            tmpblock: [],
            block: {}
        };
    },
    methods: {
        fetchTxes: function(){
            /**
             * TODO 최근 블록에 포함된 트랜잭션 리스트를 반환합니다. 
             */
            var scope = this;
            // fetchLatestBlock().then(data =>{
            //    scope.lastReadBlock = data;
            //    console.log(scope.lastReadBlock);
            //    for(var i=0;i<100;i++){
                //    getBlock(scope.lastReadBlock-i).then(datagb=>{
                    // scope.transactions = datagb;
                    //    console.log(datagb);
                    //    console.log(datagb.hash);
                       // console.log("tx.length : "+ data.transactions.length);
                    //    for(var i=0;i<datagb.transactions.length;i++){
                    //     // console.log(datagb.transactions);
                    //        getTransactionFromBlock(datagb.hash, i).then(data1=>{
                    //         //    console.log(data1);
                    //            data1.timestamp = timeSince(datagb.timestamp);
                    //            // console.log("tx : ");
                    //            scope.transactions.unshift(data1);
                    //        })
                    //    }
                //    })
                //    if(scope.transactions.length>9){
                    //    break;
                //    }
            //    }   
        //    })
            fetchLatestBlock().then(res0=>{
                    getBlock(res0)
                    .then(res1=>{
                        var transaction =res1;
                        // console.log("hi~",res1)
                        fetchTxesList(transaction, transaction.transactions.length-10 , transaction.transactions.length-1, data => {
                            // console.log("dataAAaaaaaa", data)
                            data.timeSince=timeSince(res1.timestamp)
                            data.from=data.from
                            this.tmpblock.push(data);
                            if(this.tmpblock.length == 10){
                                this.transactions = this.tmpblock;
                                this.tmpblock = [];
                            } 
                            // console.log("check=", this.transactions)
                        });
                    })
            });

        }
    },
    mounted: function(){
        this.fetchTxes();

        this.$nextTick(function () {
            window.setInterval(() => {
                this.fetchTxes();
            }, REFRESH_TIMES_OF_TRANSACTIONS);
        })
    }
})
