package com.bcauction.application;

import com.bcauction.domain.FabricAsset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FabricServiceTest
{
	@Autowired
	private IFabricService fabricService;

	@Test
	public void test작품이력조회(){
		List<FabricAsset> history = this.fabricService.작품이력조회(32);
		history.forEach(h -> System.out.println(h.getAssetId() + ", " +
		h.getOwner() + ", " + h.getCreatedAt() + "~" + h.getExpiredAt()));
	}
}
