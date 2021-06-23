package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaEntityMappingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("===================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        // 출력 결과
        // Hibernate:
        //
        //    create table Member (
        //       id bigint not null,
        //        age integer,
        //        createdDate timestamp,
        //        description clob,
        //        lastModifiedDate timestamp,
        //        roleType varchar(255),
        //        name varchar(255),
        //        primary key (id)
        //    )

        emf.close();
    }

}
