package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTwoWayRelationMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            TeamOneWay teamOneWay = new TeamOneWay();
            teamOneWay.setName("TeamA");
            em.persist(teamOneWay);

            MemberRelationOneWay memberRelationOneWay = new MemberRelationOneWay();
            memberRelationOneWay.setUsername("member1");
            memberRelationOneWay.setTeam(teamOneWay); // Team 객체를 MemberRelation 객체가 참조
            em.persist(memberRelationOneWay);

            MemberRelationOneWay findMemberRelationOneWay = em.find(MemberRelationOneWay.class, memberRelationOneWay.getId());
            TeamOneWay findTeam = findMemberRelationOneWay.getTeam(); // teamId 없이도 곧바로 Team 객체 획득 가능
            System.out.println("findTeam = " + findTeam.getName());

            TeamOneWay newTeam = em.find(TeamOneWay.class, 100L); // 100번 키가 DB에 있다고 가정을 하고 Team을 가져와보자.
            findMemberRelationOneWay.setTeam(newTeam); // 그리고나서 찾아둔 MemberRelation의 Team을 변경하면 foreign key가 변경이 된다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
