package com.bcauction.domain.wrapper;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
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
public class AuctionFactoryContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5061128a806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063431f21da1461003b578063571a26a0146100c7575b600080fd5b6100856004803603608081101561005157600080fd5b8101908080359060200190929190803590602001909291908035906020019092919080359060200190929190505050610135565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6100f3600480360360208110156100dd57600080fd5b81019080803590602001909291905050506102e3565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008033868686866040516101499061031f565b808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200185815260200184815260200183815260200182815260200195505050505050604051809103906000f0801580156101b7573d6000803e3d6000fd5b50905060008190806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550507f932f617c6c51dafe060274dfe3c3cb3e02ba867eede9a628e7dfe49eaa2bfa89813388888888604051808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001858152602001848152602001838152602001828152602001965050505050505060405180910390a180915050949350505050565b600081815481106102f057fe5b906000526020600020016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b610f298061032d8339019056fe608060405234801561001057600080fd5b50604051610f29380380610f29833981810160405260a081101561003357600080fd5b810190808051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555084600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550836005819055508260048190555081600281905550806003819055505050505050610e0c8061011d6000396000f3fe6080604052600436106100dd5760003560e01c80638fa8b7901161007f578063d57bde7911610059578063d57bde79146102f5578063eb54f9ec14610320578063f2fde38b1461034b578063fe67a54b1461039c576100dd565b80638fa8b7901461025c57806391f9015714610273578063963e63c7146102ca576100dd565b80634b449cba116100bb5780634b449cba1461014a5780635b90dbc4146101755780636ba35e70146101da5780638da5cb5b14610205576100dd565b806312fa6feb146100e25780631998aeef146101115780633ccfd60b1461011b575b600080fd5b3480156100ee57600080fd5b506100f76103b3565b604051808215151515815260200191505060405180910390f35b6101196103c6565b005b34801561012757600080fd5b506101306105ff565b604051808215151515815260200191505060405180910390f35b34801561015657600080fd5b5061015f6107bc565b6040518082815260200191505060405180910390f35b34801561018157600080fd5b506101c46004803603602081101561019857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506107c2565b6040518082815260200191505060405180910390f35b3480156101e657600080fd5b506101ef61080b565b6040518082815260200191505060405180910390f35b34801561021157600080fd5b5061021a610811565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561026857600080fd5b50610271610837565b005b34801561027f57600080fd5b506102886108b8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102d657600080fd5b506102df6108de565b6040518082815260200191505060405180910390f35b34801561030157600080fd5b5061030a6108e4565b6040518082815260200191505060405180910390f35b34801561032c57600080fd5b506103356108ea565b6040518082815260200191505060405180910390f35b34801561035757600080fd5b5061039a6004803603602081101561036e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506108f0565b005b3480156103a857600080fd5b506103b1610a42565b005b600a60009054906101000a900460ff1681565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561042157600080fd5b6000600754111561049e5760075460086000600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505b3460078190555033600660006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506009600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550507f9a7ee7c473e470da200bb28a0e3ee1fe516d900eaade5825f7960323b9d77740600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600754604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1565b600080600860003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541415610699576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526033815260200180610da56033913960400191505060405180910390fd5b6000600860003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905060008111156107b3573373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f1935050505061076d5780600860003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060009150506107b9565b6000600860003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b60019150505b90565b60035481565b6000600860008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60055481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461089157600080fd5b6001600a60006101000a81548160ff0219169083151502179055506108b66001610b52565b565b600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60045481565b60075481565b60025481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461094a57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141561098457600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a9c57600080fd5b6001600a60006101000a81548160ff021916908315150217905550610ac16000610b52565b7fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600754604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1565b60008115610bc75760075460086000600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054019050610c2c565b60086000600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b60008090505b600980549050811015610d9f57600060098281548110610c4e57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610d52578073ffffffffffffffffffffffffffffffffffffffff166108fc600860008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549081150290604051600060405180830381858888f19350505050610d4d57600080fd5b610d91565b8073ffffffffffffffffffffffffffffffffffffffff166108fc849081150290604051600060405180830381858888f19350505050610d9057600080fd5b5b508080600101915050610c32565b50505056fe4164647265737320646964206e6f742062696420796574206f7220616c726561647920726571756573747320726566756e642ea265627a7a72315820aabba7c931ae89c5deaed4fb26f8ce87999bb0d36dc19685a631a374c1f992f264736f6c634300050b0032a265627a7a723158207700c4a6ab3b4ff2e989eac0d5accc188bf71056ffa1e0ae7c8951354df181f764736f6c634300050b0032";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_AUCTIONS = "auctions";

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
    ;

    public static final Event NEWAUCTION_EVENT = new Event("NewAuction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected AuctionFactoryContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuctionFactoryContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuctionFactoryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuctionFactoryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> createAuction(BigInteger workId, BigInteger minValue, BigInteger startTime, BigInteger endTime) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(workId), 
                new org.web3j.abi.datatypes.generated.Uint256(minValue), 
                new org.web3j.abi.datatypes.generated.Uint256(startTime), 
                new org.web3j.abi.datatypes.generated.Uint256(endTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> auctions(BigInteger param0) {
        final Function function = new Function(FUNC_AUCTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<AuctionCreatedEventResponse> getAuctionCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
        ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.numAuctions = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.allAuctions = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AuctionCreatedEventResponse>() {
            @Override
            public AuctionCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONCREATED_EVENT, log);
                AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.auctionContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.numAuctions = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.allAuctions = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONCREATED_EVENT));
        return auctionCreatedEventFlowable(filter);
    }

    public List<NewAuctionEventResponse> getNewAuctionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWAUCTION_EVENT, transactionReceipt);
        ArrayList<NewAuctionEventResponse> responses = new ArrayList<NewAuctionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewAuctionEventResponse typedResponse = new NewAuctionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.auctionContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.workId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.minValue = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewAuctionEventResponse> newAuctionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewAuctionEventResponse>() {
            @Override
            public NewAuctionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWAUCTION_EVENT, log);
                NewAuctionEventResponse typedResponse = new NewAuctionEventResponse();
                typedResponse.log = log;
                typedResponse.auctionContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.workId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.minValue = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewAuctionEventResponse> newAuctionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWAUCTION_EVENT));
        return newAuctionEventFlowable(filter);
    }

    @Deprecated
    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionFactoryContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionFactoryContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AuctionFactoryContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionFactoryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuctionFactoryContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AuctionFactoryContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionFactoryContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AuctionCreatedEventResponse {
        public Log log;

        public String auctionContract;

        public String owner;

        public BigInteger numAuctions;

        public List<String> allAuctions;
    }

    public static class NewAuctionEventResponse {
        public Log log;

        public String auctionContract;

        public String owner;

        public BigInteger workId;

        public BigInteger minValue;

        public BigInteger startTime;

        public BigInteger endTime;
    }
}
