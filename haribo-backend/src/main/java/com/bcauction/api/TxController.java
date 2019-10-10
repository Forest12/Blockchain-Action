package com.bcauction.api;

import com.bcauction.application.ITxService;
import com.bcauction.domain.Transaction;
import com.bcauction.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TxController
{
	public static final Logger logger = LoggerFactory.getLogger(TxController.class);
		private ITxService txService;

	@Autowired
	public TxController(ITxService txService) {
		Assert.notNull(txService, "txService 개체가 반드시 필요!");
		
		this.txService = txService;
	}

	@RequestMapping(value = "/tx", method = RequestMethod.GET)
	public List<Transaction> 목록조회() {
		List<Transaction> 목록 = txService.목록조회();
		logger.debug("목록 생성 : "+ 목록.toString());

		return 목록;
	}
	@RequestMapping(value = "/tx10", method = RequestMethod.GET)
	public List<Transaction> 목록조회10() {
		List<Transaction> 목록 = txService.목록조회10();
		logger.debug("목록 생성 : "+ 목록.toString());

		return 목록;
	}
	
	@RequestMapping(value = "/txfind/{hash}", method = RequestMethod.GET)
	public Transaction 조회(@PathVariable String hash) {
		Transaction 트랜젝션 = this.txService.조회(hash);
		if (트랜젝션 == null){
			logger.error("NOT FOUND AUCTION: ", hash); 
			throw new NotFoundException(hash + " 해당 트랜젝션을 찾을 수 없습니다.");
		}
		return 트랜젝션;
	}

	@RequestMapping(value = "/txaddress/{address}", method = RequestMethod.GET)
	public List<Transaction> 주소조회(@PathVariable String address) {
		List<Transaction> 트랜젝션 = this.txService.주소조회(address);
		if (트랜젝션 == null){
			logger.error("NOT FOUND AUCTION: ", address); 
			throw new NotFoundException(address + " 해당 주소의 트랜젝션을 찾을 수 없습니다.");
		}
		return 트랜젝션;
	}
	
}
