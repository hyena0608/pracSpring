package prac.manboki.domain.pedometer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Optional;

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

    public List<Pedometer> findAll() {
        return em.createQuery("select p from Pedometer p", Pedometer.class)
                .getResultList();
    }

    public Pedometer findByEmail(String email) {
        return em.createQuery("select p from Pedometer p join fetch p.member m" +
                " where m.email = :email", Pedometer.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public int findTotalStepsById(Long id) {
        return em.createQuery("select p.totalSteps from Pedometer p where p.id = :id", Pedometer.class)
                .getSingleResult()
                .getTotalSteps();
    }
}
