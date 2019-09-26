package com.bcauction.application.impl;

import com.bcauction.application.IAuctionContractService;
import com.bcauction.domain.*;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.exception.DomainException;
import com.bcauction.domain.repository.IDigitalWorkRepository;
import com.bcauction.domain.repository.IWalletRepository;
import com.bcauction.domain.wrapper.AuctionContract;
import com.bcauction.domain.wrapper.AuctionFactoryContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import static com.bcauction.domain.wrapper.AuctionContract.load;

import java.math.BigInteger;
import java.util.List;

/**
 * AuctionContractService 작성, 배포한 AuctionFactory.sol Auction.sol 스마트 컨트랙트를 이용한다.
 */
@Service
public class AuctionContractService implements IAuctionContractService {
	private static final Logger log = LoggerFactory.getLogger(AuctionContractService.class);

	@Value("${eth.auction.factory.contract}")
	private String AUCTION_FACTORY_CONTRACT;

	@Value("${eth.admin.address}")
	private String ADMIN_ADDRESS;

	@Value("${eth.admin.wallet.filename}")
	private String WALLET_RESOURCE;

	@Value("${eth.encrypted.password}")
	private String PASSWORD;

	private AuctionFactoryContract auctionFactoryContract;
	private ContractGasProvider contractGasProvider = new DefaultGasProvider();
	private Credentials credentials;

	@Autowired
	private Web3j web3j;

	private IWalletRepository walletRepository;
	private IDigitalWorkRepository digitalWorkRepository;

	@Autowired
	public AuctionContractService(IWalletRepository walletRepository, IDigitalWorkRepository digitalWorkRepository) {
		this.walletRepository = walletRepository;
		this.digitalWorkRepository = digitalWorkRepository;
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 경매 정보를 조회한다.
	 * 
	 * @param 컨트랙트주소
	 * @return AuctionInfo 1. web3j API를 이용하여 해당 컨트랙트주소의 스마트 컨트랙트를 로드(load)한다. 2.
	 *         highestBidder의 정보를 가지고 최고입찰자 회원의 id를 찾아 3. AuctionInfo의 인스턴스를 생성하여
	 *         반환한다.
	 */
	@Override
	public AuctionInfo 경매정보조회(final String 컨트랙트주소) {

		// TODO
<<<<<<< HEAD
		// log.debug("경매정보조회" + 컨트랙트주소);
		credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);
		AuctionContract auctionContract = AuctionContract.load(컨트랙트주소, web3j, credentials, contractGasProvider);
		
=======
		try {
			log.debug("AuctionContractService : " + 컨트랙트주소);
			credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);

			AuctionContract auctioncontract = load(컨트랙트주소, web3j, credentials, contractGasProvider.getGasPrice(),
					contractGasProvider.getGasLimit());

			Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean> info;
			BigInteger value1= auctioncontract.auctionStartTime().send();
			BigInteger value2 = auctioncontract.auctionEndTime().send();
			BigInteger value3 = auctioncontract.minValue().send();
			BigInteger value4 = auctioncontract.digitalWorkId().sendAsync().get();
			String value5="";
			if(value4 != null){
				DigitalWork dw = digitalWorkRepository.조회(value4.longValue());
				value5=dw.getMemberId()+"";
			}

			BigInteger value6 = auctioncontract.highestBid().send();
			Boolean value7 = auctioncontract.isValid();

			info = new Tuple7<BigInteger,BigInteger,BigInteger,BigInteger,String,BigInteger,Boolean>(value1, value2, value3, value4, value5, value6, value7);
			
			log.debug("info"+info);

			AuctionInfo ai = AuctionInfoFactory.생성(컨트랙트주소, 1,info);
			return ai;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
>>>>>>> 84c3daa6b34e6a6f8f1088fe90a78b1250751f8b
		return null;
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 해당 경매의 현재 최고 입찰가를 조회한다.
	 * @param 컨트랙트주소
	 * @return BigInteger 현재최고가
	 * */
	@Override
	public BigInteger 현재최고가(final String 컨트랙트주소)
	{
		// TODO
		try {
			log.debug("AuctionContractService - now highestBid : " + 컨트랙트주소);
			credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);

			AuctionContract auctioncontract = load(컨트랙트주소, web3j, credentials, contractGasProvider.getGasPrice(),
					contractGasProvider.getGasLimit());

			BigInteger highestBid = auctioncontract.highestBid().send();
			
			log.debug("highestBid : "+highestBid);

			return highestBid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BigInteger.ZERO;
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 해당 경매의 현재 최고 입찰 주소를 조회한다.
	 * @param 컨트랙트주소
	 * @return String 최고 입찰한 이더리움 주소(EOA)
	 * */
	@Override
	public String 현재최고입찰자주소(final String 컨트랙트주소)
	{
		// TODO
		try {
			log.debug("AuctionContractService - now highestBidder : " + 컨트랙트주소);
			credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);

			AuctionContract auctioncontract = load(컨트랙트주소, web3j, credentials, contractGasProvider.getGasPrice(),
					contractGasProvider.getGasLimit());

			String highestBidder = auctioncontract.highestBidder().send();
			
			log.debug("highestBidder : "+highestBidder);

			return highestBidder;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 이더리움 컨트랙트 주소를 이용하여 생성된 모든 경매 컨트랙트의 주소 목록을 조회한다.
	 * @return List<String> 경매 컨트랙트의 주소 리스트
	 * */
	@Override
	public List<String> 경매컨트랙트주소리스트()
	{
		// TODO
		return null;
	}
}
