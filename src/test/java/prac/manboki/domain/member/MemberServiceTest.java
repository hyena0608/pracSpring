package prac.manboki.domain.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

     @Test
     public void 이메일검색() throws Exception {
         Member member1 = new Member("test1", "test1", "test1@test1.com");
         memberService.join(member1);

         assertThat(member1.getEmail()).isEqualTo("test1@test1.com");
     }


}