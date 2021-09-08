package prac.manboki.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String password;

    private String name;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pedometer_id")
    private Pedometer pedometer;

    public Member() {
    }

    public Member(String password, String name) {
        this.password = password;
        this.name = name;
    }
}
