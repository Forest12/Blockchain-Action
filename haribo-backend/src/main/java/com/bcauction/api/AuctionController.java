package com.bcauction.api;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.application.IAuctionService;
import com.bcauction.domain.Auction;
import com.bcauction.domain.AuctionInfo;
import com.bcauction.domain.Bid;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.exception.EmptyListException;
import com.bcauction.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuctionController
{
	public static final Logger logger = LoggerFactory.getLogger(AuctionController.class);

	private IAuctionService auctionService;
	private IAuctionContractService auctionContractService;

	@Autowired
	public AuctionController(IAuctionService auctionService,
	                         IAuctionContractService auctionContractService) {
		Assert.notNull(auctionService, "auctionService 개체가 반드시 필요!");
		Assert.notNull(auctionContractService, "auctionContractService 개체가 반드시 필요!");

		this.auctionService = auctionService;
		this.auctionContractService = auctionContractService;
	}

	@RequestMapping(value = "/auctions", method = RequestMethod.POST)
	public Auction 생성(@RequestBody Auction auction) {
		Auction 경매 = auctionService.생성(auction);
		logger.debug("경매 생성 : "+ 경매.toString());

		return 경매;
	}

	@RequestMapping(value = "/auctions", method = RequestMethod.GET)
	public List<Auction> 목록조회() {
		List<Auction> 목록 = auctionService.경매목록조회();

		if (목록 == null || 목록.isEmpty() )
			throw new EmptyListException("NO DATA");

		return 목록;
	}
	
	
	@RequestMapping(value = "/work/{txsAddress}", method = RequestMethod.GET)
	public AuctionInfo 조회(@PathVariable String txsAddress) {
		AuctionInfo 작품 = this.auctionContractService.경매정보조회(txsAddress);
		if (작품 == null) {
			logger.error("NOT FOUND ID: ", txsAddress);
			throw new NotFoundException(txsAddress + " 작품 정보를 찾을 수 없습니다.");
		}

		return 작품;
	}

	@RequestMapping(value = "/auctions/{id}", method = RequestMethod.GET)
	public AuctionInfo 조회(@PathVariable long id) {
		Auction 경매 = this.auctionService.조회(id);
		if (경매 == null){
			logger.error("NOT FOUND AUCTION: ", id); 
			throw new NotFoundException(id + " 해당 경매를 찾을 수 없습니다.");
		}

		AuctionInfo 경매정보 = this.auctionContractService.경매정보조회(경매.getTxsAddress());
		if(경매정보 == null){
			throw new NotFoundException(id + " 해당 경매 컨트랙트를 찾을 수 없습니다.");
		}
		경매정보.set경매시작시간(경매.getStartTime());
		경매정보.set경매종료시간(경매.getEndTime());

		return 경매정보;
	}

	@RequestMapping(value = "/auctions/{aid}/by/{mid}", method = RequestMethod.DELETE)
	public Auction 경매취소(@PathVariable long aid, @PathVariable long mid) {
		return auctionService.경매취소(aid, mid);
	}

	@RequestMapping(value = "/auctions/{aid}/by/{mid}", method = RequestMethod.PUT)
	public Auction 경매종료(@PathVariable long aid, @PathVariable long mid) { //mid = 최고가 입찰자 id
		return this.auctionService.경매종료(aid, mid);
	}

	@RequestMapping(value = "/auctions/bid", method = RequestMethod.PUT)
	public Bid 입찰(@RequestBody Bid bid) {
		return auctionService.입찰(bid);
	}

	/**
	 * 협업과제
	 * 협업과제
	 * week. 4-7
	 * mission. 3
	 * Req. 1-2
	 */
	@RequestMapping(value = "/auctions/owner/{id}", method = RequestMethod.GET)
	public List<Auction> 사용자경매목록조회(@PathVariable int id){
		List<Auction> auctions = auctionService.경매목록조회();
		List<Auction> ownerAuctions = new ArrayList<Auction>();
		for(int i=0; i<auctions.size(); i++){
			if(auctions.get(i).getAuctionCreatorId()==id){
				Auction tmp = new Auction();
				tmp.setAuctionCreatorId(auctions.get(i).getAuctionCreatorId());
				tmp.setAuctionId(auctions.get(i).getAuctionId());
				tmp.setCreateTime(auctions.get(i).getCreateTime());
				tmp.setEndTime(auctions.get(i).getEndTime());
				tmp.setId(auctions.get(i).getId());
				tmp.setIsValid(auctions.get(i).getIsValid());
				tmp.setLowestPrice(auctions.get(i).getLowestPrice());
				tmp.setStartTime(auctions.get(i).getStartTime());
				tmp.setTxsAddress(auctions.get(i).getTxsAddress());
				ownerAuctions.add(tmp);
			}
		}
		logger.debug("필터후 : "+ownerAuctions);
		return ownerAuctions;
	}

}
