const NUMBER_OF_CONTENTS_TO_SHOW = 10;           // 한 번에 보여줄 정보의 개수
const REFRESH_TIMES_OF_OVERVIEW = 1000;         // 개요 정보 갱신 시간 1초
const REFRESH_TIMES_OF_BLOCKS = 5000;           // 블록 정보 갱신 시간 5초
const REFRESH_TIMES_OF_TRANSACTIONS = 3000;     // 트랜잭션 정보 갱신 시간 3초

// 실제 Vue 템플릿 코드 작성 부분
$(function(){
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
                    fetchBlocks(Number(this.lastReadBlock-10) , Number(this.lastReadBlock) , data => {
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
    });
});