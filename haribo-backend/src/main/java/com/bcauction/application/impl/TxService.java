package com.bcauction.application.impl;

import java.util.List;

import com.bcauction.application.ITxService;
import com.bcauction.domain.Transaction;
import com.bcauction.domain.repository.ITransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxService implements ITxService
{
	public static final Logger logger = LoggerFactory.getLogger(AuctionService.class);

	private ITransactionRepository txRepository;

	@Autowired
	public TxService(ITransactionRepository txRepository) {
		this.txRepository = txRepository;
	}
	
	@Override
	public List<Transaction> 목록조회() {
		return this.txRepository.목록조회();
	}

	@Override
	public List<Transaction> 목록조회10() {
		return this.txRepository.목록조회10();
	}

	@Override
	public Transaction 조회(final String hash) {
		return this.txRepository.조회(hash);
	}

	@Override
	public List<Transaction> 주소조회(final String 주소) {
		return this.txRepository.조회By주소(주소);
	}

}
