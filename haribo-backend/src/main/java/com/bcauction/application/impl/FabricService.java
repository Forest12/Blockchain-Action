package com.bcauction.application.impl;

import com.bcauction.application.IFabricCCService;
import com.bcauction.application.IFabricService;
import com.bcauction.domain.DigitalWork;
import com.bcauction.domain.FabricAsset;
import com.bcauction.domain.Ownership;
import com.bcauction.domain.repository.IDigitalWorkRepository;
import com.bcauction.domain.repository.IOwnershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FabricService
 * 작품 소유권 이력관리를 위하여
 * FabricCCService의 함수를 호출하고
 * 소유권을 관리하는 DB 테이블(Ownership)에 이를 동기화한다.
 */
@Service
public class FabricService implements IFabricService
{
	private static final Logger logger = LoggerFactory.getLogger(FabricService.class);

	@Autowired
	private IFabricCCService fabricCCService;

	private IOwnershipRepository ownershipRepository;
	private IDigitalWorkRepository digitalWorkRepository;

	@Autowired
	public FabricService(IOwnershipRepository ownershipRepository,
	                     IDigitalWorkRepository digitalWorkRepository,
	                     IFabricCCService fabricCCService) {
		this.ownershipRepository = ownershipRepository;
		this.digitalWorkRepository = digitalWorkRepository;
		this.fabricCCService = fabricCCService;
	}

	/**
	 * fabricCCService의 registerOwnership을 호출하여
	 * 소유권을 등록하고
	 * DB에 이 정보를 동기화한다.
	 * @param 소유자
	 * @param 작품id
	 * @return Ownership
	 */
	@Override
	public Ownership 소유권등록(final long 소유자, final long 작품id)
	{
		logger.debug("소유권등록----------------------------------------------------");
		FabricAsset asset = this.fabricCCService.registerOwnership(소유자, 작품id);
		if(asset == null) return null;

		Ownership 소유권 = new Ownership();
		소유권.setOwnerId(소유자);
		소유권.setWorkId(작품id);
		소유권.setOwnStartDate(asset.getCreatedAt());
		logger.debug("소유권"+소유권.toString());

		long result = this.ownershipRepository.생성(소유권);
		if(result == 0)
			return null;

		Ownership 조회된소유권 = this.ownershipRepository.조회(소유자, 작품id);
		return 조회된소유권;
	}

	/**
	 * fabricCCService의 transferOwnership을 호출하여
	 * 소유권을 이전하고
	 * DB에 해당 정보를 동기화 한다.
	 * @param from
	 * @param to
	 * @param 작품id
	 * @return Ownership
	 */
	@Override
	public Ownership 소유권이전(final long from, final long to, final long 작품id) {
		List<FabricAsset> assets=null;
		try {
			assets = this.fabricCCService.transferOwnership(from, to, 작품id);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(assets == null) return null;

		Ownership 소멸소유권 = this.ownershipRepository.조회(from, 작품id);
		if(소멸소유권 == null) return null;

		logger.debug("--------------------------------------1111여기냐?" + assets.get(0).getExpiredAt());
		소멸소유권.setOwnEndDate(assets.get(0).getExpiredAt());
		long result = this.ownershipRepository.수정(소멸소유권);
		if(result == 0)
			return null;

		// 작품 정보 update
		DigitalWork 작품정보 = this.digitalWorkRepository.조회(작품id);
		if(작품정보.getMemberId() != from) return null;

		작품정보.setMemberId(to);
		result = this.digitalWorkRepository.수정(작품정보);
		if(result == 0)
			return null;

		logger.debug("--------------------------------------1111여기냐?" + assets.get(1).getCreatedAt());
		Ownership 새소유권 = new Ownership();
		새소유권.setOwnerId(to);
		새소유권.setWorkId(작품id);
		새소유권.setOwnStartDate(assets.get(1).getCreatedAt());

		result = this.ownershipRepository.생성(새소유권);
		if(result == 0)
			return null;

		return this.ownershipRepository.조회(to, 작품id);
	}

	/**
	 * fabricCCService의 expireOwnership을 호출하여 소유권을 소멸하고 DB에 해당 정보를 동기화 한다.
	 * 
	 * @param 소유자id
	 * @param 작품id
	 * @return Ownership
	 * @throws Throwable
	 */
	@Override
	public Ownership 소유권소멸(final long 소유자id, final long 작품id) throws Throwable
	{
		FabricAsset asset = this.fabricCCService.expireOwnership(작품id, 소유자id);
		if(asset == null) return null;

		Ownership 소멸소유권 = this.ownershipRepository.조회(소유자id, 작품id);
		if(소멸소유권 == null)
			return null;

		소멸소유권.setOwnEndDate(asset.getExpiredAt());

		long result = this.ownershipRepository.수정(소멸소유권);
		if(result == 0)
			return null;

		return 소멸소유권;
	}


	/**
	 * fabricCCService의 queryHistory를 호출하여
	 * 작품에 대한 모든 이력을 조회하고
	 * 조회된 정보를 정제하여 반환한다.
	 * @param id 작품id
	 * @return List<FabricAsset>
	 */
	@Override
	public List<FabricAsset> 작품이력조회(final long id){
		List<FabricAsset> history = this.fabricCCService.queryHistory(id);
		for (FabricAsset fa : history) {
			logger.debug(fa.toString());
		}
		// TODO


		return history;
	}

	/**
	 * 소유자가 소유한 작품을 조회하고
	 * 해당 내역이 유효한지 검증하여 소유권 목록을 반환한다.
	 * @param id 회원id
	 * @return List<Ownership>
	 */
	@Override
	public List<Ownership> 소유자별조회(final long id)
	{
		// TODO

		return null;
	}


}
