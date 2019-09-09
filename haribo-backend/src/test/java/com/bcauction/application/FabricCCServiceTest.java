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
public class FabricCCServiceTest
{
	// should change 소유자id, 작품id

	@Autowired
	private IFabricCCService fabricCCService;

	@Test
	public void testRegisterOwnership(){
		FabricAsset 새소유권 = this.fabricCCService.registerOwnership(1, 4);
		assert 새소유권 != null;
		assert 새소유권.getAssetId() != null;
		assert 새소유권.getCreatedAt() != null;
		assert 새소유권.getExpiredAt() == null;
	}

	@Test
	public void testTransferOwnership(){
		List<FabricAsset> assets;
		try {
			assets = this.fabricCCService.transferOwnership(1, 3, 3);
			assert assets != null;
			assert assets.size() == 2;
	
			FabricAsset a0 = assets.get(0);
			assert a0 != null;
			assert a0.getAssetId() != null;
			assert a0.getCreatedAt() != null;
			assert a0.getExpiredAt() != null;
	
			FabricAsset a1 = assets.get(1);
	
			assert a1 != null;
			assert a1.getAssetId() != null;
			assert a1.getCreatedAt() != null;
			assert a1.getExpiredAt() == null;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Test
	public void testExpireOwnership() throws Throwable{
		FabricAsset asset = this.fabricCCService.expireOwnership(4, 1);

		assert asset != null;
		assert asset.getAssetId() != null;
		assert asset.getCreatedAt() != null;
		assert asset.getExpiredAt() != null;
	}

	@Test
	public void testQuery(){
		FabricAsset 새소유권 = this.fabricCCService.query(4);

		assert 새소유권 != null;
		assert 새소유권.getAssetId() != null;
		assert !(새소유권.getCreatedAt().equals("FALSE"));
	}

	@Test
	public void testQueryHistory(){
		List<FabricAsset> as = this.fabricCCService.queryHistory(4);

		assert as != null;
		assert as.size() >0;
	}
}
