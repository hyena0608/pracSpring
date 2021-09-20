package prac.manboki.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import prac.manboki.domain.pedometer.Pedometer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty
//    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pedometer_id")
    private Pedometer pedometer;

    public Member() {
    }

    public Member(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
