package com.bcauction.application.impl;

import com.bcauction.application.IDigitalWorkService;
import com.bcauction.application.IFabricService;
import com.bcauction.domain.DigitalWork;
import com.bcauction.domain.Ownership;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.exception.NotFoundException;
import com.bcauction.domain.repository.IDigitalWorkRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DigitalWorkService implements IDigitalWorkService
{
	public static final Logger logger = LoggerFactory.getLogger(DigitalWorkService.class);

	private IDigitalWorkRepository digitalWorkRepository;
	private IFabricService fabricService;

	@Autowired
	public DigitalWorkService(IFabricService fabricService,
	                          IDigitalWorkRepository digitalWorkRepository) {
		this.fabricService = fabricService;
		this.digitalWorkRepository = digitalWorkRepository;
	}

	@Override
	public List<DigitalWork> 목록조회()
	{
		return this.digitalWorkRepository.목록조회();
	}

	@Override
	public List<DigitalWork> 사용자작품목록조회(final long id)
	{
		return this.digitalWorkRepository.사용자작품목록조회(id);
	}

	@Override
	public DigitalWork 조회(final long id)
	{
		return this.digitalWorkRepository.조회(id);
	}

	/**
	 * 작품 등록 시 작품 정보를 저장하고
	 * 패브릭에 작품 소유권을 등록한다.
	 * @param 작품
	 * @return DigitalWork
	 */
	@Override
	public DigitalWork 작품등록(final DigitalWork 작품) {
		// TODO
		// fabricService.소유권등록(owner, 작품id)
		long temp=this.digitalWorkRepository.추가(작품);
		logger.debug("temp in~~"+ " "+ temp+" "+this.조회(temp));

		fabricService.소유권등록(작품.getMemberId(), temp);
		
		return this.조회(temp);
	}

	/**
	 * 작품 삭제 시, 작품의 상태를 업데이트하고
	 * 패브릭에 작품 소유권 소멸 이력을 추가한다.
	 * @param id 작품id
	 * @return DigitalWork
	 */
	@Override
	public DigitalWork 작품삭제(final long id)
	{	
		digitalWorkRepository.삭제(id);
		DigitalWork delwork = digitalWorkRepository.조회(id);
		try {
			Ownership delowner = fabricService.소유권소멸(delwork.getMemberId(), id);
			logger.debug("del info : " + delowner.toString());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return delwork;
	}

	@Override
	public DigitalWork 작품정보수정(final DigitalWork 작품) {
		DigitalWork workStored = this.digitalWorkRepository.조회(작품.getId());
		if (workStored == null)
			throw new ApplicationException("해당 작품을 찾을 수 없습니다.");

		if (작품.getMemberId() != 0 && 작품.getMemberId() != workStored.getMemberId())
			throw new ApplicationException("잘못된 접근입니다.");

		if(작품.getWorkName() == null || "".equals(작품.getWorkName()))
			작품.setWorkName(workStored.getWorkName());
		if(작품.getDescription() == null || "".equals(작품.getDescription()))
			작품.setDescription(workStored.getDescription());
		if(작품.getIsDisclosure() == null || "".equals(작품.getIsDisclosure()))
			작품.setIsDisclosure(workStored.getIsDisclosure());
		if(작품.getIsValid() == null || "".equals(작품.getIsValid()))
			작품.setIsValid(workStored.getIsValid());
		if(작품.getMemberId() == 0)
			작품.setMemberId(workStored.getMemberId());

		int affected = this.digitalWorkRepository.수정(작품);
		if(affected == 0)
			throw new ApplicationException("작품정보수정 처리가 반영되지 않았습니다.");

		return 작품;
	}


}
