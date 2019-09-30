// Web3 Object 생성
function createWeb3(){
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
    return web3;
}

// AuctionFactory 컨트랙트 Object 생성
function createFactoryContract(web3){
    var auctionContract = new web3.eth.Contract(AUCTION_FACTORY_CONTRACT_ABI, AUCTION_CONTRACT_ADDRESS);
    return auctionContract;
}

// Auction Object 컨트랙트 생성
function createAuctionContract(web3, contractAddress){
    var auctionContract = new web3.eth.Contract(AUCTION_CONTRACT_ABI, contractAddress);
    return auctionContract;
}

/**
 * TODO [경매 생성] 
 * AuctionFactory의 createAuction 함수를 호출하여 경매를 생성합니다.
 * 경매 생성 시, (작품id, 최소입찰가, 경매시작시간, 경매종료시간)을 반드시 지정해야합니다. 
 *  */ 
function createAuction(options, walletAddress, privateKey, onConfirm){
    var web3 = createWeb3();
    var contract = createFactoryContract(web3);
    var createAuctionCall=contract.methods.createAuction(options.workId,options.minValue,options.startTime,options.endTime); // 함수 호출 Object 초기화
    var encodedABI = createAuctionCall.encodeABI();
    console.log(createAuctionCall)

    /**
     * 트랜잭션 생성
     *  var tx = {
        from: walletAddress,
        to: AUCTION_CONTRACT_ADDRESS,
        gas: 2000000,
        data: encodedABI
    }
    */
    var tx={
        from:walletAddress,
        to:AUCTION_CONTRACT_ADDRESS,
        gas:3000001,
        data:encodedABI
    }
    /**
      * 트랜잭션 전자 서명 후 트랜잭션 전송/처리
    */
    var signPromise = web3.eth.accounts.signTransaction(tx, privateKey);
    signPromise.then((signedTx) => {
        // raw transaction string may be available in .raw or 
        // .rawTransaction depending on which signTransaction
        // function was called
        const sentTx = web3.eth.sendSignedTransaction(signedTx.raw || signedTx.rawTransaction);
        sentTx.on("receipt", receipt => {
          var newaddress = web3.eth.abi.decodeParameters(['address','uint256','uint256','uint256','uint256'], receipt.logs[0].data);
          console.log(newaddress);
           onConfirm(newaddress);
        });
        sentTx.on("error", err => {
          console.log(err)
        });
      }).catch((err) => {
        alert("최저가를 확인해주세요")
      });
      
}

/**
 * TODO [입찰] 
 * 해당 컨트랙트 주소의 bid함수를 호출하여 입찰합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */ 
function auction_bid(options, onConfirm)
{
  var web3 = createWeb3();
  var contract = createAuctionContract(web3, options.contractAddress);
  var createBidCall = contract.methods.bid();
  var encodedABI = createBidCall.encodeABI();

  console.log(encodedABI);

  var tx={
    from : options.walletAddress,
    to : AUCTION_CONTRACT_ADDRESS,
    gas : 3000001,
    data : encodedABI
  }

  var signPromise = web3.eth.accounts.signTransaction(tx, options.privateKey);
    signPromise.then((signedTx) => {
        
        const sentTx = web3.eth.sendSignedTransaction(signedTx.raw || signedTx.rawTransaction);
        sentTx.on("receipt", receipt => {
          var newaddress = web3.eth.abi.decodeParameters(['address','uint256','uint256','uint256','uint256'], receipt.logs[0].data);
          console.log(newaddress);
           onConfirm(newaddress);
        });
        sentTx.on("error", err => {
          console.log(err)
        });
      }).catch((err) => {
        alert("최저가를 확인해주세요") 
      });

  
}

/**
 * TODO [경매 종료] 
 * 해당 컨트랙트 주소의 endAuction함수를 호출하여 경매를 종료합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */ 
function auction_close(options, onConfirm){
  var web3 = createWeb3();
  var contract = createAuctionContract(web3, options.contractAddress);
  var createCloseCall = contract.methods.endAuction();
  var encodedABI = createCloseCall.encodeABI();

  console.log(encodedABI);

  var tx={
    from : options.walletAddress,
    to : AUCTION_CONTRACT_ADDRESS,
    gas : 3000001,
    data : encodedABI
  }

  var signPromise = web3.eth.accounts.signTransaction(tx, options.privateKey);
    signPromise.then((signedTx) => {
        const sentTx = web3.eth.sendSignedTransaction(signedTx.raw || signedTx.rawTransaction);
        sentTx.on("receipt", receipt => {
          var newaddress = web3.eth.abi.decodeParameters(['address','uint256','uint256','uint256','uint256'], receipt.logs[0].data);
          console.log(newaddress);
          onConfirm(newaddress);
        });
        sentTx.on("error", err => {
          console.log(err)
        });
      }).catch((err) => {
        alert("최저가를 확인해주세요")
      });

  // onConfirm(encodedABI);
}

/**
 * TODO [경매 취소] 
 * 해당 컨트랙트 주소의 cancelAuction함수를 호출하여 경매를 종료합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */ 
function auction_cancel(options, onConfirm) {

  var web3 = createWeb3();
  var contract = createAuctionContract(web3, options.contractAddress);

  console.log(options)
  var createCloseCall = contract.methods.cancelAuction();
  console.log(createCloseCall)
  var encodedABI = createCloseCall.encodeABI();
  var tx = {
      from: options.walletAddress,
      to: options.contractAddress,
      gas: 3000000,
      data: encodedABI
  }

  var signPromise = web3.eth.accounts.signTransaction(tx, options.privateKey);
  signPromise.then((signedTx) => {
      // raw transaction string may be available in .raw or 
      // .rawTransaction depending on which signTransaction
      // function was called
      const sentTx = web3.eth.sendSignedTransaction(signedTx.raw || signedTx.rawTransaction);
      sentTx.on("receipt", receipt => {
          console.log(receipt)
          onConfirm(receipt);
      });
      sentTx.on("error", err => {
          console.log(err)
      });
  }).catch((err) => {
      alert("최저가를 확인해주세요")
  });

}