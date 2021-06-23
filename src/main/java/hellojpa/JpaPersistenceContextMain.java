package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaPersistenceContextMain {

    public static void main(String[] args) {

//        --영속성 컨텍스트와 식별자 값--
//        영속성 컨텍스트는 엔티티를 식별자 값(@Id로 테이블의 기본 키와 매핑한 값)으로 구분한다.
//        따라서 영속 상태는 식별자 값이 반드시 있어야 한다. 식별자 값이 없으면 예외가 발생한다.

//        --영속성 컨텍스트의 이점--
//        - 1차 캐시
//        - 동일성(identity) 보장
//        - 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
//        - 변경 감지(Dirty checking)
//        - 지연 로딩(Lazy loading)

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /*
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            // em.detach(member); // 준영속
            // em.remove(member); // 삭제
            System.out.println("=== AFTER ===");
            */


            /*
            -- 영속성 컨텍스트와 연관된 출력 결과 --
            === BEFORE ===
            === AFTER ===
            Hibernate:
                *//* insert hellojpa.Member
                    *//* insert
                    into
                        Member
                        (name, id)
                    values
                        (?, ?)
            트랜잭션을 커밋하는 시점에 영속성 컨텍스트에 저장된 데이터가 쿼리로 날아가게 된다.
            */

            /*
            --트랜잭션을 지원하는 쓰기 지연--
            // 영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("====================");

            // 버퍼링
            persistence.xml의 <property name="hibernate.jdbc.batch_size" value="숫자"/> 를 활용하여
            사이즈만큼 쿼리를 모아서 데이터베이스에 한방에 커밋!

            --출력 결과--
            ====================
            Hibernate:
                *//* insert hellojpa.Member
                    *//* insert
                    into
                        Member
                        (name, id)
                    values
                        (?, ?)
            Hibernate:
                *//* insert hellojpa.Member
                    *//* insert
                    into
                        Member
                        (name, id)
                    values
                        (?, ?)
            */

            /*
            --변경 감지(더티 체킹, Dirty Checking) : 영속성 컨텍스트를 활용한 데이터베이스의 데이터 변경--
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ");

            // em.persist(member); // JPA는 이런 코드가 필요 없다.

            System.out.println("====================");

            --출력 결과--
            Hibernate:
                select
                    member0_.id as id1_0_0_,
                    member0_.name as name2_0_0_
                from
                    Member member0_
                where
                    member0_.id=?
            ====================
            Hibernate: // 값만 변경했는데 update 문이 출력되네???
                *//* update
                    hellojpa.Member *//* update
                        Member
                    set
                        name=?
                    where
                        id=?

            --출력 결과에 대한 설명--
            트랜잭션 커밋을 하게 되면 flush()가 호출되고 엔티티와 스냅샷(영속성 컨텍스트에 최초로 들어온 상태)을 비교한다.
            변경된게 감지됐다면 update SQL을 생성해서 쓰기 지연 SQL 저장소에 만들어두고 데이터베이스에 반영한 다음
            최종적으로 커밋을 한다.
            */

            /*
            // 플러시
            영속성 컨텍스트의 변경내용을 데이터베이스에 반영(커밋하기 위해 전달하는 역할)

            // 플러시가 발생하면?
            변경 감지가 일어남
            수정된 엔티티 쓰기 지연 SQL 저장소에 등록
            쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)

            // 영속성 컨텍스트를 플러시하는 방법
            em.flush() - 직접 호출
            트랜잭션 커밋 - 플러시 자동 호출
            JPQL 쿼리 실행 - 플러시 자동 호출

            // 플러시는!
            영속성 컨텍스트를 비우지 않음
            영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
            트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨
            */

            /*
            // 준영속 상태
            영속 -> 준영속
            영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
            영속성 컨텍스트가 제공하는 기능을 사용 못함

            // 준영속 상태로 만드는 방법
            em.detach(entity);
            특정 엔티티만 준영속 상태로 전환

            em.clear();
            영속성 컨텍스트를 완전히 초기화하여 모든 엔티티를 준영속 상태로 전환

            em.close();
            영속성 컨텍스트를 종료하고 해당 영속성 컨텍스트가 관리하던 영속 상태의 모든 엔티티를 준영속 상태로 전환

            참고로 개발자가 직접 준영속 상태로 만드는 일은 드물다.

            em.merge();
            준영속 상태의 엔티티를 다시 영속 상태로 전환
            병합은 준영속, 비영속을 신경 쓰지 않는다.
            식별자 값으로 엔티티를 조회할 수 있으면 불러서 병합하고 조회할 수 없으면 새로 생성해서 병합한다.
            따라서 병합은 save or update 기능을 수행한다.

            // 준영속 상태의 특징
            1) 거의 비영속 상태에 가깝다.
            1차 캐시, 쓰기 지연, 변경 감지, 지연 로딩을 포함한 영속성 컨텍스트가 제공하는 어떠한 기능도 동작하지 않는다.
            2) 식별자 값을 가지고 있다.
            이미 한 번 영속 상태였으므로 반드시 식별자 값을 가지고 있다.

            */

            em.close();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
