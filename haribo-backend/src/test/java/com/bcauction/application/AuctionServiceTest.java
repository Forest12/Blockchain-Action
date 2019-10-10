package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.domain.Auction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AuctionServiceTest
{
	@Autowired
	private IAuctionService auctionService;

	@Test
	public void test경매생성() {
		Auction auction = new Auction();
		auction.setAuctionCreatorId(4);
		auction.setAuctionId(1);
		auction.setIsValid("Y");
		auction.setCreateTime(LocalDateTime.of(2019, 4,17,7,00,00));
		auction.setStartTime(LocalDateTime.of(2019, 4,17,12,00,00));
		auction.setEndTime(LocalDateTime.of(2019, 4,20,23,59,59));
		auction.setLowestPrice(BigInteger.valueOf(1000_000_000_000_000_000L));
		auction.setTxsAddress("0x80663fa544ADfa0A33447999C0F4EAC5B1f48a00");

		Auction 새경매 = this.auctionService.생성(auction);
		assert 새경매.getTxsAddress().equals("0x80663fa544ADfa0A33447999C0F4EAC5B1f48a00");
		assert 새경매.getLowestPrice().equals(BigInteger.valueOf(1000_000_000_000_000_000L));
	}

	@Test
	public void test경매목록조회() {
		List<Auction> 경매목록 = this.auctionService.경매목록조회();

		assert 경매목록.size() > 0;
	}

	@Test
	public void test조회(){
		int 경매id = 2;
		Auction 경매 = this.auctionService.조회(경매id);

		System.out.println(경매.getTxsAddress());
		assert 경매 != null;
		assert 경매.getTxsAddress() != null;
		assert 경매.getId() == 경매id;
	}

}
