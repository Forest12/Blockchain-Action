package com.bcauction.application.impl;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.application.IAuctionService;
import com.bcauction.application.IFabricService;
import com.bcauction.domain.Auction;
import com.bcauction.domain.AuctionInfo;
import com.bcauction.domain.Bid;
import com.bcauction.domain.Ownership;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.exception.NotFoundException;
import com.bcauction.domain.repository.IAuctionRepository;
import com.bcauction.domain.repository.IBidRepository;
import com.bcauction.domain.repository.IOwnershipRepository;
import com.bcauction.infrastructure.repository.OwnershipRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService implements IAuctionService
{
	public static final Logger logger = LoggerFactory.getLogger(AuctionService.class);

	private IAuctionContractService auctionContractService;
	private IFabricService fabricService;
	private IAuctionRepository auctionRepository;
	private IBidRepository bidRepository;
	private IOwnershipRepository ownerRepository;

	@Autowired
	public AuctionService(IAuctionContractService auctionContractService,
						  IFabricService fabricService,
							IAuctionRepository auctionRepository, IBidRepository bidRepository) {
		this.auctionContractService = auctionContractService;
		this.fabricService = fabricService;
		this.auctionRepository = auctionRepository;
		this.bidRepository = bidRepository;

		// ownerRepository = new OwnershipRepository();
	}

	@Override
	public List<Auction> 경매목록조회() {
		return this.auctionRepository.목록조회();
	}

	@Override
	public Auction 조회(final long 경매id) {
		return this.auctionRepository.조회(경매id);
	}

	@Override
	public Auction 조회(final String 컨트랙트주소) {
		return this.auctionRepository.조회(컨트랙트주소);
	}

	@Override
	public Auction 생성(final Auction 경매) {
		if(경매.getStartTime() == null) return null;
		if(경매.getEndTime() == null) return null;
		if(경매.getAuctionCreatorId() == 0) return null;
		if(경매.getAuctionId() == 0) return null;
		if(경매.getTxsAddress() == null) return null;
		if(경매.getLowestPrice() == null) return null;

		경매.setCreateTime(LocalDateTime.now());
		long id = this.auctionRepository.생성(경매);
		logger.debug("AuctionService 생성 " + id);
		return this.auctionRepository.조회(id);
	}

	@Override
	public Bid 입찰(Bid 입찰) {
		logger.debug("입찰" + 입찰.getBidAmount());
		long id = this.bidRepository.생성(입찰);
		logger.debug("입찰 생성 후" + id);
		return this.bidRepository.조회(id);
	}

	@Override
	public Bid 낙찰(final long 경매id, final long 낙찰자id, final BigInteger 입찰최고가)
	{
		logger.debug(경매id + ", " + 낙찰자id + ", " + 입찰최고가);
		int affected = this.bidRepository.수정(경매id, 낙찰자id, 입찰최고가);
		if(affected == 0)
			return null;

		return this.bidRepository.조회(경매id, 낙찰자id, 입찰최고가);
	}

	/**
	 * 프론트엔드에서 스마트 컨트랙트의 경매종료(endAuction) 함수 직접 호출 후
	 * 백엔드에 경매 상태 동기화를 위해 호출되는 메소드
	 * @param 경매id
	 * @param 회원id
	 * @return Auction
	 * 1. 해당 경매의 상태가 E(ended)로 바뀌고,
	 * 2. 입찰 정보 중 최고 입찰 정보를 '낙찰'로 업데이트해야 한다.
	 * 3. 데이터베이스의 소유권정보를 업데이트 한다.
	 * 4. 패브릭 상에도 소유권 이전 정보가 추가되어야 한다.
	 * 5. 업데이트 된 경매 정보를 반환한다.
	 * */
	@Override
	public Auction 경매종료(final long 경매id, final long 회원id)
	{

		// TODO
		Auction auction = 조회(경매id);
		logger.debug(auction.getIsValid() + ", " + auction.getAuctionId());
		auction.setIsValid("E");
		auctionRepository.수정(auction);
		logger.debug(auction.getIsValid() + ", " + auction.getAuctionId());
		// logger.debug(auction.toString());

		Bid bid = 낙찰(경매id, 회원id, auctionContractService.현재최고가(auction.getTxsAddress()));
		AuctionInfo ai = auctionContractService.경매정보조회(auction.getTxsAddress());

		// Ownership os = ownerRepository.조회(auction.getAuctionCreatorId(), ai.get작품id());
		// os.setOwnerId(ai.get최고입찰자id());
		// ownerRepository.수정(os);

		this.fabricService.소유권이전(auction.getAuctionCreatorId(), ai.get최고입찰자id(), ai.get작품id());

		return auction;
	}

	/**
	 * 프론트엔드에서 스마트 컨트랙트의 경매취소(cancelAuction) 함수 직접 호출 후
	 * 백엔드에 경매 상태 동기화를 위해 호출되는 메소드
	 * @param 경매id
	 * @param 회원id
	 * @return Auction
	 * 1. 해당 경매의 상태와(C,canceled) 종료일시를 업데이트 한다.
	 * 2. 입찰 정보 중 최고 입찰 정보를 '낙찰'로 업데이트해야 한다.
	 * 3. 업데이트 된 경매 정보를 반환한다.
	 * */
	@Override
	public Auction 경매취소(final long 경매id, final long 회원id)
	{   logger.debug("****취소****");
	
	    Auction auction = this.auctionRepository.조회(경매id);
		auction.setIsValid("C");
		auction.setEndTime(LocalDateTime.now());
		this.auctionRepository.수정(auction);
		 Bid bid=this.bidRepository.조회(경매id);
		if(bid !=null){
		 bid.setIsBid("Y");
		this.bidRepository.수정(bid);
		 }
		 logger.debug("****취소2****");
		// TODO
		auction=this.auctionRepository.조회(경매id);
		return auction;
	}
}
