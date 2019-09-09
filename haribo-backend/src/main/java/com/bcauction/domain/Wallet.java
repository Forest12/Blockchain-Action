package com.bcauction.domain;

import java.math.BigDecimal;

public class Wallet
{
	private long id;
	private long ownerId;
	private String address;
	private BigDecimal balance = BigDecimal.valueOf(0);
	private int chargeNum = 0;

	
	public boolean isRechargeable(){
		return this.chargeNum < 10;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getChargeNum() {
		return chargeNum;
	}

	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
	}

	@Override
	public String toString() {
		return "Wallet [address=" + address + ", balance=" + balance + ", chargeNum=" + chargeNum + ", id=" + id
				+ ", ownerId=" + ownerId + "]";
	}
}
