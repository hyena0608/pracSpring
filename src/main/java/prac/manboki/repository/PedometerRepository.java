package prac.manboki.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import prac.manboki.domain.Pedometer;

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
