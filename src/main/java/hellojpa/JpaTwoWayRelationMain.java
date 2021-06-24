package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaTwoWayRelationMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            TeamTwoWay teamTwoWay = new TeamTwoWay();
            teamTwoWay.setName("TeamA");
            em.persist(teamTwoWay);

            MemberRelationTwoWay memberRelationTwoWay = new MemberRelationTwoWay();
            memberRelationTwoWay.setUsername("member1");
            memberRelationTwoWay.setTeam(teamTwoWay); // Team 객체를 MemberRelation 객체가 참조
            em.persist(memberRelationTwoWay);

            MemberRelationTwoWay findMember = em.find(MemberRelationTwoWay.class, memberRelationTwoWay.getId());
            List<MemberRelationTwoWay> members = findMember.getTeam().getMembers();

            for( MemberRelationTwoWay m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        /*
        --출력 결과--
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

            create table MemberRelationOneWay (
                MEMBER_ID bigint not null,
                USERNAME varchar(255),
                TEAM_ID bigint,
                primary key (MEMBER_ID)
            )
        Hibernate:

            create table MemberRelationTwoWay (
                MEMBER_ID bigint not null,
                USERNAME varchar(255),
                TEAM_ID bigint,
                primary key (MEMBER_ID)
            )
        Hibernate:

            create table TeamOneWay (
                TEAM_ID bigint not null,
                name varchar(255),
                primary key (TEAM_ID)
            )
        Hibernate:

            create table TeamTwoWay (
                TEAM_ID bigint not null,
                name varchar(255),
                primary key (TEAM_ID)
            )
        Hibernate:

            alter table MemberRelationOneWay
                add constraint FK88k3mww49h872r5r4yvyu08bt
                foreign key (TEAM_ID)
                references TeamOneWay
        Hibernate:

            alter table MemberRelationTwoWay
                add constraint FKjfn8704kyyigsrnc17r9dim93
                foreign key (TEAM_ID)
                references TeamTwoWay
        Hibernate:
            call next value for hibernate_sequence
        Hibernate:
            call next value for hibernate_sequence
        Hibernate:
            *//* insert hellojpa.TeamTwoWay
                 *//* insert
                into
                    TeamTwoWay
                    (name, TEAM_ID)
                values
                    (?, ?)
        Hibernate:
            *//* insert hellojpa.MemberRelationTwoWay
                 *//* insert
                into
                    MemberRelationTwoWay
                    (TEAM_ID, USERNAME, MEMBER_ID)
                values
                    (?, ?, ?)
        */

        emf.close();
    }

}
