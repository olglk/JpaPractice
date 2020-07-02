package com.example.demo.Service;

import com.example.demo.model.House;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Repository
public class HouseService {

    @PersistenceContext
    EntityManager entityManager;

    //HouseService(EntityManager entityManager) {
    //this.entityManager = entityManager;
    //}

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void deleteById(int id) {
        //entityManager.getTransaction().begin();
        House house1 = entityManager.find(House.class, id);
        entityManager.lock(house1, LockModeType.OPTIMISTIC);
        entityManager.remove(house1);
        //entityManager.getTransaction().commit();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void detach(House house) {
        entityManager.detach(house);
    }

    public List<House> findAllSQL() {
        return entityManager.createNativeQuery("SELECT * FROM House", House.class).getResultList();
    }

    public List<House> findAllJPQL() {
        return entityManager.createQuery("FROM House", House.class).getResultList();
    }

    public List<House> getCriteriaHouse() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<House> criteriaQuery = criteriaBuilder.createQuery(House.class);
        Root<House> houseRoot = criteriaQuery.from(House.class);
        criteriaQuery.select(houseRoot);
//        Map<String,Object> properties = new HashMap();
//        properties.put("javax.persistence.lock.timeout", 3000);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<String> getCriteriaHouseNames() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<House> houseRoot = criteriaQuery.from(House.class);
        criteriaQuery.select(houseRoot.get("name"));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Integer getCriteriaHouseSumAges() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<House> houseRoot = criteriaQuery.from(House.class);
        criteriaQuery.select(criteriaBuilder.sum(houseRoot.get("age")));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}


