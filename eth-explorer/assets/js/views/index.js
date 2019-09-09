const NUMBER_OF_CONTENTS_TO_SHOW = 3;           // 한 번에 보여줄 정보의 개수
const REFRESH_TIMES_OF_OVERVIEW = 1000;         // 개요 정보 갱신 시간 1초
const REFRESH_TIMES_OF_BLOCKS = 5000;           // 블록 정보 갱신 시간 5초
const REFRESH_TIMES_OF_TRANSACTIONS = 3000;     // 트랜잭션 정보 갱신 시간 3초

// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var dashboardOverview = new Vue({
        el: '#dashboard-overview',
        data: {
            latestBlock: 0,
            latestTxCount: 0
        },
        methods: {
            updateLatestBlock: function(){
                fetchLatestBlock()
                .then(res=>{
                    this.latestBlock=res
                });
            },           
            updateLatestTxCount: function(){
                web3.eth.getBlockTransactionCount(this.latestBlock).then(res=>{
                    this.latestTxCount=res
                });
            }
        },
        mounted: function(){
            this.$nextTick(function () {
                window.setInterval(() => {
                    this.updateLatestBlock();
                    this.updateLatestTxCount();
                }, REFRESH_TIMES_OF_OVERVIEW);
            });
        }
    });

    var blocksView = new Vue({
        el: '#blocks',
        data: {
            lastReadBlock: 0,
            blocks: [],
            tmplist:[],
        },
        methods: {
            fetchBlocks: function(){
                // TODO 최근 10개의 블록 정보를 가져와서 계속 업데이트 한다.
                fetchLatestBlock()
                .then(res=>{
                    this.lastReadBlock=res;
                    fetchBlocks(Number(this.lastReadBlock-10) , Number(this.lastReadBlock-1) , data => {
                        data.timestamp=timeSince(data.timestamp)
                        this.tmplist.push(data)     
                        if(this.tmplist.length==10){
                            this.blocks=this.tmplist
                            this.tmplist=[]
                        }
                    });
                });
            }
        },
        mounted: function(){
            this.fetchBlocks();

            this.$nextTick(function () {
                window.setInterval(() => {
                    this.fetchBlocks();
                }, REFRESH_TIMES_OF_BLOCKS);
            })
        }
    })

    var txesView = new Vue({
        el: '#transactions',
        data: {
            transactions: [],
            tmplist:[]
        },
        methods: {
            fetchTxes: function(){
                // TODO 최근 블록에 속한 10개의 트랜잭션 정보를 가져와서 계속 업데이트 한다.
                fetchLatestBlock()
                .then(res=>{
                    web3.eth.getBlock(res)
                    .then(res1=>{
                        var transaction =res1;
                        if(transaction.transactions.length!=0){
                            fetchTxes(transaction, transaction.transactions.length-10 , transaction.transactions.length-1, data=> {
                                data.timeSince=timeSince(res1.timestamp)
                                this.tmplist.push(data)
                                if(this.tmplist.length==10){
                                    this.transactions=this.tmplist
                                    this.tmplist=[]
                                }                
                            });
                        }
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
    });
});