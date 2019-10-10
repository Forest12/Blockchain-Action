var explorerTxListView = Vue.component('ExplorerTxListView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다."></v-breadcrumb>
            <div class="container">
                <explorer-nav></explorer-nav>
                <div class="row" v-if="transactions.length == 0">
                <div class="col-md-8 mx-auto" style="height: 400px;" v-if="load === true">
                <div class="semipolar-spinner" :style="spinnerStyle" style="margin:100px auto;">
                    <div class="ring"></div>
                    <div class="ring"></div>
                    <div class="ring"></div>
                    <div class="ring"></div>
                    <div class="ring"></div>
                </div>
            </div>
                </div>
                <div class="row">
                    <div id="transactions" class="col-md-8 mx-auto">
                        <div class="card shadow-sm" v-if="load === false">
                            <div class="card-header">Transactions
                                <input type="text" style="float:right" placeholder="search.." v-on:keyup="search" v-model="searchTx"></input>
                            </div>
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
            block: {},
            load:true,
            searchTx:'',
        };
    },
    mounted: function() {
        var scope = this;

            txService.find10(function(data) {
                var x = [];
                for(var i=0;i<data.length;i++){
                    x[i] = {};
                    //console.log(data[i].hash);
                    x[i].hash = data[i].hash;
                    x[i].from = data[i].from;
                    x[i].to = data[i].to;
//                    x[i]=[hash, from, to];
                    scope.transactions.unshift(x[i]);
//                    scope.transactions.push(x);
                }
                scope.load = false;
        });
    },
    methods:{
        search:function(){
            console.log(this.searchTx);
        }
    }
});