package com.bcauction.domain.wrapper;


// This file is an automatically generated Java binding. Do not modify as any
// change will likely be lost upon the next re-generation!


import org.ethereum.geth.*;
import org.ethereum.geth.internal.*;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.Transaction;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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



public class AuctionFactoryContract {
	// ABI is the input ABI used to generate the binding from.

    public final static String ABI = "[{\"constant\":false,\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"workId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"minValue\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"startTime\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"endTime\",\"type\":\"uint256\"}],\"name\":\"createAuction\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"auctions\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"auctionContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"numAuctions\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address[]\",\"name\":\"allAuctions\",\"type\":\"address[]\"}],\"name\":\"AuctionCreated\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"auctionContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"workId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"minValue\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"startTime\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"endTime\",\"type\":\"uint256\"}],\"name\":\"NewAuction\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"}]";





	// GoChain address where this contract is located at.
	public final Address Address;

	// GoChain transaction in which this contract was deployed (if known!).
	public final Transaction Deployer;

	// Contract instance bound to a blockchain address.
	private final BoundContract Contract;

	// Creates a new instance of Main, bound to a specific deployed contract.
	public Main(Address address, GoClient client) throws Exception {
		this(GoChain.bindContract(address, ABI, client));
	}




	// auctions is a free data retrieval call binding the contract method 0x571a26a0.
	//
	// Solidity: function auctions(uint256 ) constant returns(address)
	public Address auctions(CallOpts opts, BigInt arg0) throws Exception {
		Interfaces args = GoChain.newInterfaces(1);
		args.set(0, GoChain.newInterface()); args.get(0).setBigInt(arg0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultAddress(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "auctions", args);
		return results.get(0).getAddress();

	}



	// owner is a free data retrieval call binding the contract method 0x8da5cb5b.
	//
	// Solidity: function owner() constant returns(address)
	public Address owner(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultAddress(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "owner", args);
		return results.get(0).getAddress();

	}



	// createAuction is a paid mutator transaction binding the contract method 0x431f21da.
	//
	// Solidity: function createAuction(uint256 workId, uint256 minValue, uint256 startTime, uint256 endTime) return(address)
	public Transaction createAuction(TransactOpts opts, BigInt workId, BigInt minValue, BigInt startTime, BigInt endTime) throws Exception{
		Interfaces args = GoChain.newInterfaces(4);
		args.set(0, GoChain.newInterface()); args.get(0).setBigInt(workId);
		args.set(1, GoChain.newInterface()); args.get(1).setBigInt(minValue);
		args.set(2, GoChain.newInterface()); args.get(2).setBigInt(startTime);
		args.set(3, GoChain.newInterface()); args.get(3).setBigInt(endTime);


		return this.Contract.transact(opts, "createAuction"     , args);
	}

	// transferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
	//
	// Solidity: function transferOwnership(address newOwner) returns()
	public Transaction transferOwnership(TransactOpts opts, Address newOwner) throws Exception {
		Interfaces args = GoChain.newInterfaces(1);
		args.set(0, GoChain.newInterface()); args.get(0).setAddress(newOwner);


		return this.Contract.transact(opts, "transferOwnership" , args);
	}

}




