package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

/*        --주의--
        엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안됨
        JPA의 모든 데이터 변경은 트랜잭션 안에서 실행*/

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /*
            --SELECT--
            Member findMember = em.find(Member.class, 1L); // EntityManager를 마치 자바 컬렉션이라 생각하면 된다. 원하는 데이터를 저장하고 읽는다.
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            --콘솔 출력 결과(H2 데이터베이스의 현재 상태에 따라 데이터가 다름)--
            (참고로 콘솔에 출력되는 것은 persistence.xml의 <property name="hibernate.show_sql" value="true" /> 덕분)
            Hibernate:
                select
                    member0_.id as id1_0_0_,
                    member0_.name as name2_0_0_
                from
                    Member member0_
                where
                    member0_.id=?
            findMember.id = 1
            findMember.name = HelloA
            */

            /*--UPDATE--
            EntityManager를 통해 데이터를 가져오면 JPA가 이를 관리하는데,
            트랜잭션을 커밋하는 시점에서 데이터의 변경을 모두 체크하여
            데이터가 변경됐다면 UPDATE 쿼리를 만들어서 날린다.

            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");

            --콘솔 출력 결과--
            Hibernate:
                select
                    member0_.id as id1_0_0_,
                    member0_.name as name2_0_0_
                from
                    Member member0_
                where
                    member0_.id=?
            Hibernate:
            *//* update
                hellojpa.Member *//* update
                    Member
                set
                    name=?
                where
                    id=?*/

/*            --JPQL--
            JPQL을 한마디로 정의하면 객체 지향 SQL
            쿼리문이 테이블 대상이 아니라 객체 대상이다. SQL은 데이터베이스 테이블을 대상이다.
            SQL을 추상화해서 특정 데이터베이스에 의존하지 않는다.

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            --콘솔 출력 결과--
            Hibernate:
                *//* select
                    m
                from
                    Member as m *//* select
                        member0_.id as id1_0_,
                        member0_.name as name2_0_
                    from
                        Member member0_
            member.name = HelloJPA*/

            tx.commit(); // 트랜잭션 커밋을 해야 변경사항이 반영이 된다.
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 사용이 끝난 EntityManager는 반드시 종료해야 한다.
        }

        emf.close(); // 애플리케이션을 종료할 때 EntityManagerFactory도 종료해야 한다.
    }

}
