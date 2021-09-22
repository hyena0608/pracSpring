package prac.manboki.domain.pedometer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prac.manboki.domain.member.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedometerService {

    private final PedometerRepository pedometerRepository;

    @Transactional
    public Long join(Pedometer pedometer) {

        pedometerRepository.save(pedometer);
        return pedometer.getId();
    }

    public Optional<Pedometer> findByEmail(String email) {
        return pedometerRepository.findAll().stream()
                .filter(p -> p.getMember().getEmail().equals(email))
                .findFirst();
    }

}
