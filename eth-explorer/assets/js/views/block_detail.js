// 실제 Vue 템플릿 코드 작성 부분
$(function(){
    var blockNumber = parseQueryString()['blockNumber'];
    
    var detailView = new Vue({
        el: '#block-detail',
        data: {
            isValid: true,
            block: {
                number: 0
            }
        },
        mounted: function(){
            if(blockNumber) {
               console.log(blockNumber)
               web3.eth.getBlock(blockNumber).then(data=>{
                    data.timestamp=timeSince(data.timestamp)
                    this.block=data
               })
            } else {
                this.isValid = false;
            }
        }
    });
});