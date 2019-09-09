package com.bcauction.api;

import com.bcauction.application.IWalletService;
import com.bcauction.domain.Wallet;
import com.bcauction.domain.exception.EmptyListException;
import com.bcauction.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class WalletController {
	public static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	private IWalletService walletService;

	@Autowired
	public WalletController(IWalletService walletService) {
		Assert.notNull(walletService, "walletService 개체가 반드시 필요!");
		this.walletService = walletService;
	}

	@RequestMapping(value = "/wallets", method = RequestMethod.POST)
	public Wallet 등록(@Valid @RequestBody Wallet wallet) {
		logger.debug(wallet.getAddress());
		logger.debug(String.valueOf(wallet.getOwnerId()));

		this.walletService.등록(wallet);
		Wallet 새지갑 = walletService.조회_ETH잔액동기화(wallet.getAddress());

		if(새지갑 == null)
			throw new NotFoundException(wallet.getAddress() + " 해당 주소 지갑을 찾을 수 없습니다.");

		return 새지갑;
	}

	@RequestMapping(value = "/wallets", method = RequestMethod.GET)
	public List<Wallet> 목록조회() {
		List<Wallet> 목록 = walletService.목록조회();

		if (목록 == null || 목록.isEmpty() )
			throw new EmptyListException("NO DATA");

		return 목록;
	}

	@RequestMapping(value = "/wallets/{address}", method = RequestMethod.GET)
	public Wallet 조회(@PathVariable String address) {
		return walletService.조회_ETH잔액동기화(address);
	}

	@RequestMapping(value = "/wallets/of/{mid}", method = RequestMethod.GET)
	public Wallet 조회By소유자(@PathVariable long mid) {
		Wallet 지갑 = this.walletService.조회(mid);
		if(지갑 == null)
			throw new EmptyListException("[소유자id] " + mid + " 해당 지갑을 찾을 수 없습니다.");
		// logger.debug(지갑.toString());
		return walletService.조회_ETH잔액동기화(지갑.getAddress());
	}

	@RequestMapping(value ="/wallets/{address}", method = RequestMethod.PUT)
	public Wallet 충전(@PathVariable String address){ // 테스트 가능하도록 일정 개수의 코인을 충전해준다.
		return this.walletService.충전(address);
	}
}
