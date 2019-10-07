package com.bcauction.application.impl;

import com.bcauction.domain.EthInfo;
import com.bcauction.domain.Transaction;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.repository.IEthInfoRepository;
import com.bcauction.domain.repository.ITransactionRepository;
import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * EthBlockListeningService
 * 이더리움 네트워크의 새로 생성된 블록 정보로부터
 * 트랜잭션을 동기화하는 기능 포함
 */
@Service
public class EthBlockListeningService
{
	private static final Logger log = LoggerFactory.getLogger(EthBlockListeningService.class);

	private BigInteger latestBlockHeight = BigInteger.valueOf(0);

	private Web3j web3j;
	private IEthInfoRepository ethInfoRepository;
	private ITransactionRepository transactionRepository;

	@Value("${spring.web3j.client-address}")
	private String ethUrl;

	@Autowired
	public EthBlockListeningService(Web3j web3j,
									IEthInfoRepository ethInfoRepository,
									ITransactionRepository transactionRepository)
	{
		this.web3j = web3j;
		this.ethInfoRepository = ethInfoRepository;
		this.transactionRepository = transactionRepository;
	}

	/**
	 * 구축한 이더리움 네트워크로부터 신규 생성된 블록을 동기화한다.
	 */
	@PostConstruct
	public void listen()
	{
		// TODO
		log.info("New Block Subscribed Here");
		try {
			EthBlock latestBlockResponse;
			EthereumService ethservice;
			latestBlockResponse =web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true)
			.sendAsync().get();
			EthBlock.Block block = latestBlockResponse.getBlock();
			Block bloc = null;
			Block lastBlock = bloc.fromOriginalBlock(latestBlockResponse.getBlock());
			List<EthereumTransaction> list = lastBlock.getTrans();
			for(int i=0; i<list.size();i++){
				EthereumTransaction eth = list.get(i);
				if(this.transactionRepository.조회(eth.getTxHash())==null){
					Transaction tx= null;
					tx.setId(eth.hashCode());
					tx.setHash(eth.getTxHash());
					tx.setBlockHash(block.getHash());
					tx.setBlockNumber(block.getNumberRaw());
					//tx.setTransactionIndex(transactionIndex);
					tx.setFrom(eth.getFrom());
					tx.setTo(eth.getTo());
					tx.setValue(eth.getAmount().toString());
					tx.setGasPrice(block.getGasUsedRaw());
					tx.setGas(block.getGasLimitRaw());
					//tx.setInput();
					tx.setCreates(eth.getTimestamp().toString());
					//tx.setPublicKey(block.ge);
					//tx.setRaw();
					//tx.setR(); //outputs of an ECDSA signature
					//tx.setS(); //outputs of an ECDSA signature
					//tx.setV(); //recovery id
					this.transactionRepository.추가(tx);
				}
			}
			
		} catch (ExecutionException | InterruptedException e) {
			throw new ApplicationException(e.getMessage());
		}

	}
}
