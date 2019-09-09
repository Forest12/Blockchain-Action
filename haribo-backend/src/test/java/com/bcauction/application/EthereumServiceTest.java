package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.domain.Address;
import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EthereumServiceTest
{
	@Autowired
	private IEthereumService ethereumService;

	@Test
	public void test최근블록조회()
	{
		List<Block> blocks = this.ethereumService.최근블록조회();

		assert blocks != null;
		assert blocks.size() == 20;
	}

	@Test
	public void test최근트랜잭션조회()
	{
		List<EthereumTransaction> txs = this.ethereumService.최근트랜잭션조회();

		assert txs != null;
	}

	@Test
	public void test블록검색()
	{
		Block b = this.ethereumService.블록검색("100");

		assert b != null;
		assert b.getBlockNo().intValue() == 100;
	}

	@Test
	public void test트랜잭션검색()
	{
		EthereumTransaction tx = this.ethereumService.트랜잭션검색("0xca00d3af3e89495eba77b7a2e44a5d80bab716695570ab63eff17c12e40ac7f2");

		assert tx != null;
		assert !tx.getBlockId().isEmpty();
		assert Integer.valueOf(tx.getBlockId()) > 0;
		System.out.println(tx.getFrom() + " --> " + tx.getTo() + " : " + tx.getAmount());
	}

	@Test
	public void test충전()
	{
		String 충전받을주소 = "0x6190E280834C9a3414EC2b93B268b629206ab65C";
		String txhash = this.ethereumService.충전(충전받을주소);

		System.out.println(txhash);
		assert txhash != null || !txhash.equals("");
	}

	@Test
	public void test주소검색()
	{
		String 주소 = "0x66eDaFE1d6073Fb3bB8DD6eCFEE95319FEb2787D";
		Address addr = this.ethereumService.주소검색(주소);

		assert addr != null;
	}
}