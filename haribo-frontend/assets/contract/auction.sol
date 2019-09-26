pragma solidity ^0.5.2;

import "./Ownable.sol";

/// @title Auction
contract Auction is Ownable {

  // 생성자에 의해 정해지는 값
  address public owner;
  uint public auctionStartTime;
  uint public auctionEndTime;
  uint public minValue;
  uint public digitalWorkId;

  // 현재 최고 입찰 상태
  address public highestBidder;
  uint public highestBid;

  mapping(address => uint) pendingReturns;
  address[] bidders;

  bool public ended;

  event HighestBidIncereased(address bidder, uint amount);
  event AuctionEnded(address winner, uint amount);

  /**
   * @dev AuctionFactory의 createAuction함수에서 호출하는 생성자입니다.
   * 경매에서 고려해야하는 제한사항을 고려하여 상태변수를 초기화합니다.
   */
  constructor(address _owner, uint workId, uint minimum, uint startTime, uint endTime) public {
    // todo 내용을 완성 합니다.
    owner = _owner;
    digitalWorkId = workId;
    minValue = minimum;
    auctionStartTime = startTime;
    auctionEndTime = endTime;
  }

  /**
   * @dev 입찰을 위한 함수입니다.
   */
  function bid() public onlyNotOwner payable {
    // todo 내용을 완성 합니다.
    if(highestBid > 0) {
      pendingReturns[highestBidder] += highestBid;
    }

    highestBid = msg.value;
    highestBidder = msg.sender;

    bidders.push(highestBidder);

    emit HighestBidIncereased(highestBidder, highestBid);
  }

  /*
   * @dev 환불을 위한 함수입니다.
   * 환불은 입찰 당사자가 해당 함수를 호출함으로써 가능합니다.
   */
  function withdraw() public returns (bool) {

    require(pendingReturns[msg.sender] != 0, "Address did not bid yet or already requests refund.");
    uint amount = pendingReturns[msg.sender];
    if(amount>0){
        if(!msg.sender.send(amount)){
            pendingReturns[msg.sender] = amount;
            return false;
        }
        pendingReturns[msg.sender] = 0;
    }
    return true;
  }

  /**
   * @dev 경매 종료를 위한 함수입니다.
   * 경매 생성자만이 경매를 종료시킬 수 있습니다.
   * 현재까지의 입찰 중 최고가를 선택하여 경매를 종료합니다.
   */
  function endAuction() public onlyOwner {
    // todo 내용을 완성 합니다.
    ended = true;
    _refund(false);

    emit AuctionEnded(highestBidder, highestBid);

  }

  /**
   * @dev 경매 취소를 위한 함수입니다.
   * 경매 생성자만이 경매를 취소할 수 있습니다.
   * 모든 입찰에 대해 환불을 수행하고 경매를 종료합니다.
   */
  function cancelAuction() public onlyOwner {
    // todo 내용을 완성 합니다.
    ended = true;
    _refund(true);

  }

  /**
   * @dev 이와 같이 추가 함수를 구현해보아도 좋습니다.
   */
  function getPendingReturnsBy(address _address) view public returns (uint){
      return pendingReturns[_address];
  }

  /**
   * @dev 이와 같이 추가 modifier를 구현해보아도 좋습니다.
   */
  modifier onlyNotOwner {
    require(msg.sender != owner);
    _;
  }
    modifier onlyOwner {
    require(msg.sender == owner);
    _;
  }

  /// @dev 환불을 위한 internal 함수
  /// @param isCancelled 경매 취소 인지 낙찰인지
  function _refund(bool isCancelled) internal {
      uint returnToHighestBidder;
      if(isCancelled){
        returnToHighestBidder = pendingReturns[highestBidder] + highestBid;
      } else {
        returnToHighestBidder = pendingReturns[highestBidder];
      }

      for(uint i = 0; i < bidders.length; i++){
        address payable bidder = address(uint160(bidders[i]));
        if(bidder != highestBidder){
            if(!bidder.send(pendingReturns[bidder])){
                revert();
            }
        } else {
            if(!bidder.send(returnToHighestBidder)){
                revert();
            }
        }
      }
  }
}