package com.bcauction.infrastructure.repository;

import com.bcauction.domain.Ownership;
import com.bcauction.domain.repository.IOwnershipRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnershipRepositoryTest
{
	@Autowired
	IOwnershipRepository ownershipRepository;

	@Test
	public void test조회w소유자idand작품id(){
		long 소유자id = 15;
		long 작품id = 28;

		Ownership 소유권 = this.ownershipRepository.조회(소유자id, 작품id);

		assert 소유권 != null;
	}
}