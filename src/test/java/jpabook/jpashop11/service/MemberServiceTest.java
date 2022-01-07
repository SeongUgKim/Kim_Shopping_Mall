package jpabook.jpashop11.service;

import jpabook.jpashop11.domain.Member;
import jpabook.jpashop11.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    
    @Test
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("Kim");
        // when
        Long savedId = memberService.join(member);
        // then
        em.flush();
        assertThat(member).isEqualTo(memberService.findOne(savedId));
    
    }
    
    @Test(expected = IllegalStateException.class)
    public void duplicate_member_exception() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("Kim1");
        Member member2 = new Member();
        member2.setName("Kim1");
        // when
        memberService.join(member1);
        memberService.join(member2);
        // then
        Assert.fail("Exception occur");
    
    }

}