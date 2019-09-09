package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.application.impl.AuctionContractService;
import com.bcauction.domain.AuctionInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AuctionContractServiceTest
{
	private static final Logger log = LoggerFactory.getLogger(AuctionContractServiceTest.class);

	@Autowired
	private IAuctionContractService auctionContractService;

	@Test
	public void test경매컨트랙트주소리스트(){
		List<String> auctions = this.auctionContractService.경매컨트랙트주소리스트();

		log.info(auctions.size() + "");
		log.info(auctions.get(0));

		assert auctions != null;
	}

	@Test
	public void test경매정보조회(){
		String 경매컨트랙트주소 = "0x99385252d33fa884c74920111FD75f262b9F06ed";

		AuctionInfo 경매정보 = this.auctionContractService.경매정보조회(경매컨트랙트주소);

		assert 경매정보 != null;
		assert 경매정보.get경매시작시간() != null;
		assert 경매정보.get경매종료시간() != null;
		assert 경매정보.get경매컨트랙트주소() != null;
		assert 경매정보.get작품id() != 0;
		log.info(경매정보.get최소금액().longValue() + "");
		assert 경매정보.get최소금액().longValue() > 0;
	}

	@Test
	public void test현재최고가(){}

	@Test
	public void test현재최고입찰자주소(){}
}