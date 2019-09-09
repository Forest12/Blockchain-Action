package com.bcauction.application.impl;

import com.bcauction.application.IMemberService;
import com.bcauction.domain.Member;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.repository.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService implements IMemberService {

    private IMemberRepository memberRepository;

    @Autowired
    public MemberService(IMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public List<Member> 목록조회() {
        return this.memberRepository.목록조회();
    }

    @Override
    public Member 조회(long id) {
        return this.memberRepository.조회(id);
    }

    @Override
    public Member 조회(String 이메일) { return this.memberRepository.조회(이메일); }

    @Override
    public Member 추가(Member member) {
        long id = this.memberRepository.addMember(member);
        return this.memberRepository.조회(id);
    }

    @Override
    public Member 수정(Member 회원) {

        Member found = this.memberRepository.조회(회원.getEmail());
        if(found == null)
            throw new ApplicationException("회원 정보를 찾을 수 없습니다.");

        if(회원.getId() == 0)
            회원.setId(found.getId());
        if(회원.getUsername() == null)
            회원.setUsername(found.getUsername());
        if(회원.getPassword() == null)
            회원.setPassword(found.getPassword());

        int affected = this.memberRepository.수정(회원);
        if(affected == 0)
            throw new ApplicationException("작품정보수정 처리가 반영되지 않았습니다.");

        return this.memberRepository.조회(회원.getId());
    }

    @Override
    public void 삭제(long id) {
        this.memberRepository.삭제(id);
    }
}
