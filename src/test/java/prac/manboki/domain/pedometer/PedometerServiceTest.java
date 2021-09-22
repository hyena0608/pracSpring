package prac.manboki.domain.pedometer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prac.manboki.domain.member.Member;
import prac.manboki.domain.member.MemberService;


@SpringBootTest
@Transactional
public class PedometerServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PedometerService pedometerService;
    @Autowired
    private PedometerRepository pedometerRepository;

     @Test
     public void 만보기_이메일로_찾기() throws Exception {
         //given
         Member member1 = new Member("test1", "test1", "test1@test1.com");
         memberService.join(member1);

         Pedometer pedometer1 = new Pedometer(member1, 10, 1000);
         pedometerService.join(pedometer1);

         //when
         Pedometer findByEmailPedomemter = pedometerRepository.findByEmail(member1.getEmail());

         //then
         Assertions.assertThat(findByEmailPedomemter).isEqualTo(pedometer1);
     }

}