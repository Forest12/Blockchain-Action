package com.bcauction.domain.wrapper;

/**
 * TODO
 * Auction.sol을
 * 솔리디티 컴파일러와 Web3 CLI를 이용하여
 * 자동생성된 솔리디티 스마트 컨트랙트의 Wrapper Class를
 * 해당 패키지에 AuctionContract.java로 추가한다.
 */


// This file is an automatically generated Java binding. Do not modify as any
// change will likely be lost upon the next re-generation!

import org.ethereum.geth.*;
import org.ethereum.geth.internal.*;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
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




public class AuctionContract {
	// ABI is the input ABI used to generate the binding from.
	public final static String ABI = "[{\"constant\":false,\"inputs\":[],\"name\":\"bid\",\"outputs\":[],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"beneficiary\",\"outputs\":[{\"internalType\":\"addresspayable\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"withdraw\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"auctionEndTime\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"internalType\":\"address\",\"name\":\"_address\",\"type\":\"address\"}],\"name\":\"getPendingReturnsBy\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"renounceOwnership\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"isOwner\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"cancelAuction\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"highestBidder\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"minValue\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getTimeLeft\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"highestBid\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"endAuction\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"minimum\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"hoursAfter\",\"type\":\"uint256\"}],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"bidder\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"HighestBidIncereased\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"winner\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"AuctionEnded\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"}]";




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




	// auctionEndTime is a free data retrieval call binding the contract method 0x4b449cba.
	//
	// Solidity: function auctionEndTime() constant returns(uint256)
	public BigInt auctionEndTime(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultBigInt(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "auctionEndTime", args);
		return results.get(0).getBigInt();

	}



	// beneficiary is a free data retrieval call binding the contract method 0x38af3eed.
	//
	// Solidity: function beneficiary() constant returns(address)
	public Address beneficiary(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultAddress(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "beneficiary", args);
		return results.get(0).getAddress();

	}



	// getPendingReturnsBy is a free data retrieval call binding the contract method 0x5b90dbc4.
	//
	// Solidity: function getPendingReturnsBy(address _address) constant returns(uint256)
	public BigInt getPendingReturnsBy(CallOpts opts, Address _address) throws Exception {
		Interfaces args = GoChain.newInterfaces(1);
		args.set(0, GoChain.newInterface()); args.get(0).setAddress(_address);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultBigInt(); results.set(0, result0);
		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "getPendingReturnsBy", args);
		return results.get(0).getBigInt();

	}



	// getTimeLeft is a free data retrieval call binding the contract method 0xc7e284b8.
	//
	// Solidity: function getTimeLeft() constant returns(uint256)
	public BigInt getTimeLeft(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultBigInt(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "getTimeLeft", args);
		return results.get(0).getBigInt();

	}

	// highestBid is a free data retrieval call binding the contract method 0xd57bde79.
	//
	// Solidity: function highestBid() constant returns(uint256)
	public BigInt highestBid(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultBigInt(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "highestBid", args);
		return results.get(0).getBigInt();

	}



	// highestBidder is a free data retrieval call binding the contract method 0x91f90157.
	//
	// Solidity: function highestBidder() constant returns(address)
	public Address highestBidder(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultAddress(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "highestBidder", args);
		return results.get(0).getAddress();

	}



	// isOwner is a free data retrieval call binding the contract method 0x8f32d59b.
	//
	// Solidity: function isOwner() constant returns(bool)
	public boolean isOwner(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultBool(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "isOwner", args);
		return results.get(0).getBool();

	}

	// minValue is a free data retrieval call binding the contract method 0x963e63c7.
	//
	// Solidity: function minValue() constant returns(uint256)
	public BigInt minValue(CallOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		Interfaces results = GoChain.newInterfaces(1);
		Interface result0 = GoChain.newInterface(); result0.setDefaultBigInt(); results.set(0, result0);


		if (opts == null) {
			opts = GoChain.newCallOpts();
		}
		this.Contract.call(opts, results, "minValue", args);
		return results.get(0).getBigInt();

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



	// bid is a paid mutator transaction binding the contract method 0x1998aeef.
	//
	// Solidity: function bid() returns()
	public Transaction bid(TransactOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		return this.Contract.transact(opts, "bid"       , args);
	}

	// cancelAuction is a paid mutator transaction binding the contract method 0x8fa8b790.
	//
	// Solidity: function cancelAuction() returns()
	public Transaction cancelAuction(TransactOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		return this.Contract.transact(opts, "cancelAuction"     , args);
	}

	// endAuction is a paid mutator transaction binding the contract method 0xfe67a54b.
	//
	// Solidity: function endAuction() returns()
	public Transaction endAuction(TransactOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		return this.Contract.transact(opts, "endAuction"        , args);
	}

	// renounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
	//
	// Solidity: function renounceOwnership() returns()
	public Transaction renounceOwnership(TransactOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		return this.Contract.transact(opts, "renounceOwnership" , args);
	}

	// transferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
	//
	// Solidity: function transferOwnership(address newOwner) returns()
	public Transaction transferOwnership(TransactOpts opts, Address newOwner) throws Exception {
		Interfaces args = GoChain.newInterfaces(1);
		args.set(0, GoChain.newInterface()); args.get(0).setAddress(newOwner);


		return this.Contract.transact(opts, "transferOwnership" , args);
	}

	// withdraw is a paid mutator transaction binding the contract method 0x3ccfd60b.
	//
	// Solidity: function withdraw() returns(bool)
	public Transaction withdraw(TransactOpts opts) throws Exception {
		Interfaces args = GoChain.newInterfaces(0);


		return this.Contract.transact(opts, "withdraw"  , args);
	}

}


