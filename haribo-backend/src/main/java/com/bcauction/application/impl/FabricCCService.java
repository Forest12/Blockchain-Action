package com.bcauction.application.impl;
import com.bcauction.application.IFabricCCService;
import com.bcauction.domain.CommonUtil;
import com.bcauction.domain.FabricAsset;
import com.bcauction.domain.FabricUser;
import com.bcauction.domain.exception.ApplicationException;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;

import org.hyperledger.fabric.protos.peer.Chaincode;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class FabricCCService implements IFabricCCService
{
	private static final Logger logger = LoggerFactory.getLogger(FabricCCService.class);
	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	private HFClient hfClient;
	private HFCAClient caClient;
	private Channel channel;

	/**
	 * 패브릭 네트워크를 이용하기 위한 정보
	 */
	@Value("${fabric.ca-server.url}")
	private String CA_SERVER_URL;
	@Value("${fabric.ca-server.admin.name}")
	private String CA_SERVER_ADMIN_NAME;
	@Value("${fabric.ca-server.pem.file}")
	private String CA_SERVER_PEM_FILE;
	@Value("${fabric.org.name}")
	private String ORG_NAME;
	@Value("${fabric.org.msp.name}")
	private String ORG_MSP_NAME;
	@Value("${fabric.org.admin.name}")
	private String ORG_ADMIN_NAME;
	@Value("${fabric.peer.name}")
	private String PEER_NAME;
	@Value("${fabric.peer.url}")
	private String PEER_URL;
	@Value("${fabric.peer.pem.file}")
	private String PEER_PEM_FILE;
	@Value("${fabric.orderer.name}")
	private String ORDERER_NAME;
	@Value("${fabric.orderer.url}")
	private String ORDERER_URL;
	@Value("${fabric.orderer.pem.file}")
	private String ORDERER_PEM_FILE;
	@Value("${fabric.org.user.name}")
	private String USER_NAME;
	@Value("${fabric.org.user.secret}")
	private String USER_SECRET;
	@Value("${fabric.channel.name}")
	private String CHANNEL_NAME;


	/**
	 * 체인코드를 이용하기 위하여
	 * 구축해놓은 패브릭 네트워크의 채널을 가져오는
	 * 기능을 구현한다.
	 * 여기에서 this.channel의 값을 초기화 한다
	 */
	private void loadChannel() throws Throwable{
		// TODO
		//CA 기관 환경설정
        caClient = HFCAClient.createNewInstance(CA_SERVER_URL,getPropertiesWith(CA_SERVER_PEM_FILE));
       CryptoSuite cs = CryptoSuite.Factory.getCryptoSuite();
        caClient.setCryptoSuite(cs);
        // logger.debug("aaaaaaa");
       //CA 등록을 위한 admin user생성
        FabricUser user= new FabricUser();
        Enrollment en = caClient.enroll("admin", "adminpw");
        // logger.debug("bbbbbbb");
        user.setName(ORG_ADMIN_NAME);
        user.setMspId(ORG_MSP_NAME);
        user.setAffiliation(ORG_NAME);
        user.setEnrollment(en);
        // logger.debug("bbbbbbbbb");
       //Client 생성
        hfClient = HFClient.createNewInstance();
        hfClient.setCryptoSuite(cs);
        // logger.debug("cccccccc");
        //생성한 CA의 정보 Client 등록
        hfClient.setUserContext(user);
        //Peer가 사용자 CA를 사용하는 사람
        Peer peer = hfClient.newPeer(PEER_NAME,PEER_URL,getPropertiesWith(PEER_PEM_FILE));
        //생성자?
        Orderer orderer = hfClient.newOrderer(ORDERER_NAME, ORDERER_URL,getPropertiesWith(ORDERER_PEM_FILE));
        //사용할 채널 생성
        this.channel = hfClient.newChannel(CHANNEL_NAME);
        channel.addPeer(peer);
        channel.addOrderer(orderer);
        channel.initialize();
		
	}

    private Properties getPropertiesWith(String filename) {
        Properties properties = new Properties();
        properties.put("pemBytes", CommonUtil.readString(filename).getBytes());
        properties.setProperty("sslProvider", "openSSL");
        properties.setProperty("negotiationType", "TLS");
        
        return properties;
    }

	/**
	 * 소유권 등록을 위해 체인코드 함수를 차례대로 호출한다.
	 * @param 소유자
	 * @param 작품id
	 * @return FabricAsset
	 */
	@Override
	public FabricAsset registerOwnership(final long 소유자, final long 작품id){
		if(this.channel == null)
			try {
				loadChannel();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		boolean res = false;
		res = registerAsset(작품id, 소유자);

		if(!res)
			return null;
			
		res = confirmTimestamp(작품id);
		// logger.debug("~!~!~!~!~!!~!~!~!~!!~");
		if(!res)
			return null;
		

		return query(작품id);
	}

	/**
	 * 소유권 이전을 위해 체인코드 함수를 차례대로 호출한다.
	 * @param from
	 * @param to
	 * @param 작품id
	 * @return List<FabricAsset
	 */
	@Override
	public List<FabricAsset> transferOwnership(final long from, final long to, final long 작품id) {
		try {
			if (this.channel == null)
				loadChannel();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<FabricAsset> assets = new ArrayList<>();
		boolean res = this.expireAssetOwnership(작품id, from);
		if(!res) return null;
		FabricAsset expired = query(작품id);
		if(expired == null) return null;
		assets.add(expired);

		res = this.updateAssetOwnership(작품id, to);
		if(!res) return null;
		FabricAsset transferred = query(작품id);
		if(transferred == null) return null;
		assets.add(transferred);

		return assets;
	}

	/**
	 * 소유권 소멸을 위해 체인코드 함수를 호출한다.
	 * @param 작품id
	 * @param 소유자id
	 * @return FabricAsset
	 */
	@Override
	public FabricAsset expireOwnership(final long 작품id, final long 소유자id) {
		if(this.channel == null)
			try {
				loadChannel();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		boolean res = this.expireAssetOwnership(작품id, 소유자id);
		if(!res) return null;

		return query(작품id);
	}

	/**
	 * 체인코드 registerAsset 함수를 호출하는 메소드
	 * @param 작품id
	 * @param 소유자
	 * @return boolean
	 */
	private boolean registerAsset(final long 작품id, final long 소유자) {
		// TODO
		TransactionProposalRequest request = hfClient.newTransactionProposalRequest();
		ChaincodeID ccid = ChaincodeID.newBuilder().setName("asset").build();
		
        request.setChaincodeID(ccid);
		request.setFcn("registerAsset");
		
        String[] arguments = { 작품id + "", 소유자 + ""};
        if (arguments != null) {
			request.setArgs(arguments);
		}
		//request.setProposalWaitTime(3000);
		// logger.debug("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
		Collection<ProposalResponse> responses;
		try {
			//Thread.sleep(1000);
			responses = channel.sendTransactionProposal(request);
			CompletableFuture<TransactionEvent> cf = channel.sendTransaction(responses); //return blockevent
			TransactionEvent te = cf.get();
			logger.debug(te.getTransactionID());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return false;
	}

	/**
	 * 체인코드 confirmTimestamp 함수를 호출하는 메소드
	 * @param 작품id
	 * @return
	 */
	private boolean confirmTimestamp(final long 작품id){
		TransactionProposalRequest request = hfClient.newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName("asset").build();
        request.setChaincodeID(ccid);
        request.setFcn("confirmTimestamp");
        String[] arguments = { 작품id + ""};
        if (arguments != null) {
			request.setArgs(arguments);
		}
        //request.setProposalWaitTime(3000);

		Collection<ProposalResponse> responses;
		try {
			responses = channel.sendTransactionProposal(request);
			CompletableFuture<TransactionEvent> cf = channel.sendTransaction(responses); //return blockevent
			TransactionEvent te = cf.get();
			logger.debug(te.getTransactionID());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return false;
	}

	/**
	 * 체인코드 expireAssetOwnership를 호출하는 메소드
	 * @param 작품id
	 * @param 소유자
	 * @return
	 */
	private boolean expireAssetOwnership(final long 작품id, final long 소유자) {
		TransactionProposalRequest request = hfClient.newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName("asset").build();
        request.setChaincodeID(ccid);
        request.setFcn("expireAssetOwnership");
        String[] arguments = { 작품id + "", 소유자 + ""};
        if (arguments != null) {
			request.setArgs(arguments);
		}
        request.setProposalWaitTime(1000);
		
		Collection<ProposalResponse> responses;
		try {
			responses = channel.sendTransactionProposal(request);
			CompletableFuture<TransactionEvent> cf = channel.sendTransaction(responses); //return blockevent
			logger.debug(cf.get().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return request.doVerify();
	}

	/**
	 * 체인코드 updateAssetOwnership를 호출하는 메소드
	 * @param 작품id
	 * @param to
	 * @return
	 */
	private boolean updateAssetOwnership(final long 작품id, final long to) {
		TransactionProposalRequest request = hfClient.newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName("asset").build();
        request.setChaincodeID(ccid);
        request.setFcn("updateAssetOwnership");
        String[] arguments = { 작품id + "", to + ""};
        if (arguments != null) {
			request.setArgs(arguments);
		}
		request.setProposalWaitTime(1000);
		
		Collection<ProposalResponse> responses;
		try {
			responses = channel.sendTransactionProposal(request);
			CompletableFuture<TransactionEvent> cf = channel.sendTransaction(responses); //return blockevent
			logger.debug(cf.get().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return request.doVerify();
	}

	/**
	 * 체인코드 queryHistory 함수를 호출하는 메소드
	 * @param 작품id
	 * @return
	 */
	@Override
	public List<FabricAsset> queryHistory(final long 작품id){
		if(this.hfClient == null || this.channel == null)
			try {
				loadChannel();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				QueryByChaincodeRequest request = hfClient.newQueryProposalRequest();
				ChaincodeID ccid = ChaincodeID.newBuilder().setName("asset").build();
				request.setChaincodeID(ccid);
				request.setFcn("queryHistory");
				String[] arguments = { 작품id + ""};
				if (arguments != null) {
					request.setArgs(arguments);
				}
				Collection<ProposalResponse> response = channel.queryByChaincode(request);
				//Collection<ProposalResponse> response = channel.queryByChaincode("queryHistory",작품id+"");
				
				for (ProposalResponse pres : response) {
					String stringResponse = new String(pres.getChaincodeActionResponsePayload());
					//Logger.getLogger(response.class.getName()).log(Level.INFO, stringResponse);
					logger.debug(stringResponse);
				}
			} catch (Exception e) {
				//TODO: handle exception
				e.printStackTrace();
			}
			//Collection<ProposalResponse> response = channel.queryByChaincode("AssetManagement","queryHistory",작품id+"");
				
			return null;
	}

	/**
	 * 체인코드 query 함수를 호출하는 메소드
	 * @param 작품id
	 * @return
	 */
	@Override
	public FabricAsset query(final long 작품id){
		if(this.hfClient == null || this.channel == null) {
			try {
				loadChannel();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		FabricAsset fa = null;
			try {
				QueryByChaincodeRequest request = hfClient.newQueryProposalRequest();
				ChaincodeID ccid = ChaincodeID.newBuilder().setName("asset").build();
				request.setChaincodeID(ccid);
				request.setFcn("query");
				String[] arguments = { 작품id + ""};
				if (arguments != null) {
					request.setArgs(arguments);
				}
				Collection<ProposalResponse> response = channel.queryByChaincode(request);
				
				for (ProposalResponse pres : response) {
					JsonReader parse = Json.createReader(new ByteArrayInputStream(pres.getChaincodeActionResponsePayload()));	
               		fa=getAssetRecord(parse.readObject());
				}
				return fa;
			} catch (Exception e) {
				//TODO: handle exception
				e.printStackTrace();
			}
			
			return null;
    }

    private static FabricAsset getAssetRecord(final JsonObject rec)
    {
        FabricAsset asset = new FabricAsset();
        asset.setAssetId(rec.getString("assetID"));
        asset.setOwner(rec.getString("owner"));
        asset.setCreatedAt(rec.getString("createdAt"));
        asset.setExpiredAt(rec.getString("expiredAt"));
        logger.info("Work " + rec.getString("assetID") + " by Owner " + rec.getString("owner") + ": "+
                            rec.getString("createdAt") + " ~ " + rec.getString("expiredAt"));
        return asset;
    }
}