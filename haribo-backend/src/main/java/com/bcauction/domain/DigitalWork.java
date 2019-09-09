package com.bcauction.domain;

public class DigitalWork
{
	private long id;
	private String workName;
	private String description;
	private String isDisclosure = "Y";
	private String isValid = "Y";
	private long memberId;



	@Override
	public String toString()
	{
		return new StringBuilder()
				.append("{ id: " + id)
				.append("\n\tworkName: " + workName)
				.append("\n\tdescription: " + description)
				.append("\n\tisDisclosure: " + isDisclosure)
				.append("\n\tisVaild: " + isValid)
				.append("\n\tmemberId: " + memberId)
				.append(" }")
				.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsDisclosure() {
		return isDisclosure;
	}

	public void setIsDisclosure(String isDisclosure) {
		this.isDisclosure = isDisclosure;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
}
