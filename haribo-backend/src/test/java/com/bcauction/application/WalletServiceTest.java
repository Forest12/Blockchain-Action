package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.domain.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WalletServiceTest
{
	@Autowired
	private IWalletService walletService;
	private static String 테스트지갑주소 = "fakeaddress00";
	private static String 임시지갑주소 = "fakeaddress01";

	@Transactional
	@Test
	public void test지갑등록(){
		Wallet 지갑 = new Wallet();
		지갑.setAddress(임시지갑주소);
		지갑.setOwnerId(15);
		지갑.setBalance(BigDecimal.valueOf(1000));

		Wallet 새지갑 = this.walletService.등록(지갑);

		assert 새지갑 != null;
		assert 새지갑.getOwnerId() == 지갑.getOwnerId();
		assert 새지갑.getAddress().equals(임시지갑주소);
	}

	@Test
	public void test지갑조회(){
		Wallet 지갑 = this.walletService.조회_ETH잔액동기화(테스트지갑주소);

		assert 지갑 != null;
		assert 지갑.getAddress().equals(테스트지갑주소);
	}

	@Test
	public void test목록조회(){
		List<Wallet> 지갑목록 = this.walletService.목록조회();

		assert 지갑목록.size() > 0;
	}

	@Transactional
	@Test
	public void test잔액갱신(){
		Wallet 지갑 = this.walletService.잔액갱신(테스트지갑주소, BigDecimal.valueOf(5000));

		assert 지갑 != null;
		assert 지갑.getAddress().equals(테스트지갑주소);
		assert 지갑.getBalance().compareTo(new BigDecimal(5000)) == 0;
	}

}
