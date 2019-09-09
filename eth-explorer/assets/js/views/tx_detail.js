// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var hash = parseQueryString()['hash'];
    
    var detailView = new Vue({
        el: '#tx-detail',
        data: {
            isValid: true,
            tx: {
                hash: "-",
                timestamp: "-"
            }
        },
        mounted: function(){
            if(hash) {
               web3.eth.getTransaction(hash).then(data=>{
                   web3.eth.getBlock(data.blockHash).then(res=>{
                       this.tx=data
                       this.tx.timestamp=res.timestamp
                   })
               })
            } else {
                this.isValid = false;
            }
        }
    });
});