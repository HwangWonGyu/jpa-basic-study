package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaOneWayRelationMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            MemberRelationOneWay memberRelationOneWay = new MemberRelationOneWay();
            memberRelationOneWay.setUsername("member1");
            memberRelationOneWay.setTeam(team); // Team 객체를 MemberRelation 객체가 참조
            em.persist(memberRelationOneWay);

            MemberRelationOneWay findMemberRelationOneWay = em.find(MemberRelationOneWay.class, memberRelationOneWay.getId());
            Team findTeam = findMemberRelationOneWay.getTeam(); // teamId 없이도 곧바로 Team 객체 획득 가능
            System.out.println("findTeam = " + findTeam.getName());

            Team newTeam = em.find(Team.class, 100L); // 100번 키가 DB에 있다고 가정을 하고 Team을 가져와보자.
            findMemberRelationOneWay.setTeam(newTeam); // 그리고나서 찾아둔 MemberRelation의 Team을 변경하면 foreign key가 변경이 된다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        /*
        --출력 결과--
        Hibernate:

            drop table if exists Member CASCADE
        Hibernate:

            drop table if exists MemberRelation CASCADE
        Hibernate:

            drop table if exists Team CASCADE
        Hibernate:

            drop sequence if exists hibernate_sequence
        Hibernate: create sequence hibernate_sequence start with 1 increment by 1
        Hibernate:

            create table Member (
                id bigint not null,
                age integer,
                createdDate timestamp,
                description clob,
                lastModifiedDate timestamp,
                roleType varchar(255),
                name varchar(255),
                primary key (id)
            )
        Hibernate:

            create table MemberRelation (
                MEMBER_ID bigint not null,
                USERNAME varchar(255),
                TEAM_ID bigint,
                primary key (MEMBER_ID)
            )
        Hibernate:

            create table Team (
                TEAM_ID bigint not null,
                name varchar(255),
                primary key (TEAM_ID)
            )

        Hibernate:

            alter table MemberRelation
                add constraint FK574sx72orjbgr84uavmsbtmgp
                foreign key (TEAM_ID)
                references Team
        ===================
        */

        emf.close();
    }

}
