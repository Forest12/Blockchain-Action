package com.bcauction.application;

import com.bcauction.domain.Address;
import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface IEthereumService {
    List<Block> 최근블록조회();
    List<EthereumTransaction> 최근트랜잭션조회();

    Block 블록검색(String 블록Id);
    EthereumTransaction 트랜잭션검색(String 트랜Id);

    String 충전(String 주소);

    Address 주소검색(String 주소);
    BigDecimal 잔액갱신(String 주소);
}
