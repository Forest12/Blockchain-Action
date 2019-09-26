package com.bcauction.domain.wrapper;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class AuctionContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610d26380380610d26833981810160405260a081101561003357600080fd5b810190808051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050846000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550836004819055508260038190555081600181905550806002819055505050505050610c4a806100dc6000396000f3fe6080604052600436106100c25760003560e01c80638da5cb5b1161007f578063963e63c711610059578063963e63c7146102af578063d57bde79146102da578063eb54f9ec14610305578063fe67a54b14610330576100c2565b80638da5cb5b146101ea5780638fa8b7901461024157806391f9015714610258576100c2565b806312fa6feb146100c75780631998aeef146100f65780633ccfd60b146101005780634b449cba1461012f5780635b90dbc41461015a5780636ba35e70146101bf575b600080fd5b3480156100d357600080fd5b506100dc610347565b604051808215151515815260200191505060405180910390f35b6100fe61035a565b005b34801561010c57600080fd5b50610115610592565b604051808215151515815260200191505060405180910390f35b34801561013b57600080fd5b5061014461074f565b6040518082815260200191505060405180910390f35b34801561016657600080fd5b506101a96004803603602081101561017d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610755565b6040518082815260200191505060405180910390f35b3480156101cb57600080fd5b506101d461079e565b6040518082815260200191505060405180910390f35b3480156101f657600080fd5b506101ff6107a4565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561024d57600080fd5b506102566107c9565b005b34801561026457600080fd5b5061026d610849565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102bb57600080fd5b506102c461086f565b6040518082815260200191505060405180910390f35b3480156102e657600080fd5b506102ef610875565b6040518082815260200191505060405180910390f35b34801561031157600080fd5b5061031a61087b565b6040518082815260200191505060405180910390f35b34801561033c57600080fd5b50610345610881565b005b600960009054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156103b457600080fd5b600060065411156104315760065460076000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505b3460068190555033600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506008600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550507f9a7ee7c473e470da200bb28a0e3ee1fe516d900eaade5825f7960323b9d77740600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600654604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1565b600080600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054141561062c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526033815260200180610be36033913960400191505060405180910390fd5b6000600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490506000811115610746573373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050506107005780600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550600091505061074c565b6000600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b60019150505b90565b60025481565b6000600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60045481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461082257600080fd5b6001600960006101000a81548160ff0219169083151502179055506108476001610990565b565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60035481565b60065481565b60015481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146108da57600080fd5b6001600960006101000a81548160ff0219169083151502179055506108ff6000610990565b7fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600654604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1565b60008115610a055760065460076000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054019050610a6a565b60076000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b60008090505b600880549050811015610bdd57600060088281548110610a8c57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610b90578073ffffffffffffffffffffffffffffffffffffffff166108fc600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549081150290604051600060405180830381858888f19350505050610b8b57600080fd5b610bcf565b8073ffffffffffffffffffffffffffffffffffffffff166108fc849081150290604051600060405180830381858888f19350505050610bce57600080fd5b5b508080600101915050610a70565b50505056fe4164647265737320646964206e6f742062696420796574206f7220616c726561647920726571756573747320726566756e642ea265627a7a723158200b1639d13951c326e9d09ffe2b45bf94e6e42cd4097533d29605d7da3b7d4c2164736f6c634300050b0032\r\n";

    public static final String FUNC_ENDED = "ended";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_AUCTIONENDTIME = "auctionEndTime";

    public static final String FUNC_GETPENDINGRETURNSBY = "getPendingReturnsBy";

    public static final String FUNC_DIGITALWORKID = "digitalWorkId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_CANCELAUCTION = "cancelAuction";

    public static final String FUNC_HIGHESTBIDDER = "highestBidder";

    public static final String FUNC_MINVALUE = "minValue";

    public static final String FUNC_HIGHESTBID = "highestBid";

    public static final String FUNC_AUCTIONSTARTTIME = "auctionStartTime";

    public static final String FUNC_ENDAUCTION = "endAuction";

    public static final Event HIGHESTBIDINCEREASED_EVENT = new Event("HighestBidIncereased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AuctionContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuctionContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuctionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuctionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> ended() {
        final Function function = new Function(FUNC_ENDED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> bid(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> auctionEndTime() {
        final Function function = new Function(FUNC_AUCTIONENDTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getPendingReturnsBy(String _address) {
        final Function function = new Function(FUNC_GETPENDINGRETURNSBY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> digitalWorkId() {
        final Function function = new Function(FUNC_DIGITALWORKID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> cancelAuction() {
        final Function function = new Function(
                FUNC_CANCELAUCTION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> highestBidder() {
        final Function function = new Function(FUNC_HIGHESTBIDDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> minValue() {
        final Function function = new Function(FUNC_MINVALUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> highestBid() {
        final Function function = new Function(FUNC_HIGHESTBID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> auctionStartTime() {
        final Function function = new Function(FUNC_AUCTIONSTARTTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> endAuction() {
        final Function function = new Function(
                FUNC_ENDAUCTION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<HighestBidIncereasedEventResponse> getHighestBidIncereasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HIGHESTBIDINCEREASED_EVENT, transactionReceipt);
        ArrayList<HighestBidIncereasedEventResponse> responses = new ArrayList<HighestBidIncereasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HighestBidIncereasedEventResponse typedResponse = new HighestBidIncereasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<HighestBidIncereasedEventResponse> highestBidIncereasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, HighestBidIncereasedEventResponse>() {
            @Override
            public HighestBidIncereasedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HIGHESTBIDINCEREASED_EVENT, log);
                HighestBidIncereasedEventResponse typedResponse = new HighestBidIncereasedEventResponse();
                typedResponse.log = log;
                typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<HighestBidIncereasedEventResponse> highestBidIncereasedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HIGHESTBIDINCEREASED_EVENT));
        return highestBidIncereasedEventFlowable(filter);
    }

    public List<AuctionEndedEventResponse> getAuctionEndedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONENDED_EVENT, transactionReceipt);
        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuctionEndedEventResponse>() {
            @Override
            public AuctionEndedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONENDED_EVENT, log);
                AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
                typedResponse.log = log;
                typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventFlowable(filter);
    }

    @Deprecated
    public static AuctionContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuctionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuctionContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AuctionContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuctionContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuctionContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, BigInteger workId, BigInteger minimum, BigInteger startTime, BigInteger endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.generated.Uint256(workId), 
                new org.web3j.abi.datatypes.generated.Uint256(minimum), 
                new org.web3j.abi.datatypes.generated.Uint256(startTime), 
                new org.web3j.abi.datatypes.generated.Uint256(endTime)));
        return deployRemoteCall(AuctionContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AuctionContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, BigInteger workId, BigInteger minimum, BigInteger startTime, BigInteger endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.generated.Uint256(workId), 
                new org.web3j.abi.datatypes.generated.Uint256(minimum), 
                new org.web3j.abi.datatypes.generated.Uint256(startTime), 
                new org.web3j.abi.datatypes.generated.Uint256(endTime)));
        return deployRemoteCall(AuctionContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AuctionContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, BigInteger workId, BigInteger minimum, BigInteger startTime, BigInteger endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.generated.Uint256(workId), 
                new org.web3j.abi.datatypes.generated.Uint256(minimum), 
                new org.web3j.abi.datatypes.generated.Uint256(startTime), 
                new org.web3j.abi.datatypes.generated.Uint256(endTime)));
        return deployRemoteCall(AuctionContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AuctionContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, BigInteger workId, BigInteger minimum, BigInteger startTime, BigInteger endTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.generated.Uint256(workId), 
                new org.web3j.abi.datatypes.generated.Uint256(minimum), 
                new org.web3j.abi.datatypes.generated.Uint256(startTime), 
                new org.web3j.abi.datatypes.generated.Uint256(endTime)));
        return deployRemoteCall(AuctionContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class HighestBidIncereasedEventResponse {
        public Log log;

        public String bidder;

        public BigInteger amount;
    }

    public static class AuctionEndedEventResponse {
        public Log log;

        public String winner;

        public BigInteger amount;
    }
}
