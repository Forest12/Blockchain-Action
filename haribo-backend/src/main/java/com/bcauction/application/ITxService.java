package com.bcauction.application;

import com.bcauction.domain.Transaction;

import java.util.List;

public interface ITxService
{

	List<Transaction> 목록조회();
	List<Transaction> 목록조회10();
	Transaction 조회(String hash);
	List<Transaction> 주소조회(String 주소);

}
