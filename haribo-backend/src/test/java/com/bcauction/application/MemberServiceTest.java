package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberServiceTest {
    @Autowired
    private IMemberService memberService;


    @Transactional
    @Test
    public void test추가() {
        Member 회원 = new Member();
        회원.setUsername("코인충");
        회원.setEmail("코인충@yahoo.com");

        Member 추가된회원 = this.memberService.추가(회원);
        assert 추가된회원.getId() > 0;
        assert 추가된회원.getUsername().equals("코인충");
        assert 추가된회원.getEmail().equals("코인충@yahoo.com");
    }


    @Test
    public void test목록조회() {
        List<Member> 회원목록 = this.memberService.목록조회();

        assert 회원목록.size() > 0;
    }

    @Test
    public void test조회() {
        long 회원id = 4;
        Member 회원 = this.memberService.조회(회원id);

        assert 회원 != null;
        assert 회원.getUsername().equals("비트맨");
    }


    @Transactional
    @Test
    public void test수정() {
        Member 회원 = new Member();
        회원.setId(4);
        회원.setUsername("비트우먼");
        회원.setEmail("bitwoman@yahoo.com");

        this.memberService.수정(회원);

        Member 수정된회원 = this.memberService.조회(4);
        assert 수정된회원.getEmail().equals("비트우먼");
    }

    @Transactional
    @Test
    public void test삭제() {
        long 회원id = 4;
        this.memberService.삭제(회원id);

        Member 삭제된회원 = this.memberService.조회(회원id);
        assert 삭제된회원 == null;
    }
}
