package prac.manboki.domain.pedometer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import prac.manboki.domain.member.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Pedometer {

    @Id @GeneratedValue
    @Column(name = "pedometer_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    private Member member;

    @Column(name = "today_steps")
    private int todaySteps;

    @Column(name = "total_steps")
    private int totalSteps;

    public Pedometer() {
    }

    public Pedometer(Member member, int todaySteps, int totalSteps) {
        this.member = member;
        this.todaySteps = todaySteps;
        this.totalSteps = totalSteps;
    }

    private void setTodaySteps(int todaySteps) {
        this.todaySteps = todaySteps;
    }

    private void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public void setSteps(int todaySteps, int totalSteps) {
        this.todaySteps = todaySteps;
        this.totalSteps = totalSteps;
    }
}
