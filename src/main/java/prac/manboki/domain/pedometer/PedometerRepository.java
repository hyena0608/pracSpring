package prac.manboki.domain.pedometer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PedometerRepository {

    private final EntityManager em;

    public void save(Pedometer pedometer) {
        em.persist(pedometer);
    }

    public Pedometer findOne(Long id) {
        return em.find(Pedometer.class, id);
    }
}
