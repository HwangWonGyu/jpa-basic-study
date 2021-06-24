package hellojpa;

import javax.persistence.*;


// 객체의 양방향은 사실 서로 다른 단방향들을 합친것을 억지로 '양'방향이라고 부르는 것이다.


// * 연관관계의 주인

// 양방향 매핑 규칙에서 나오는 개념
// 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
// 연관관계의 주인만이 외래 키를 관리(등록, 수정)
// 주인이 아닌쪽은 읽기만 가능
// 주인은 mappedBy 속성 사용X
// 주인이 아니면 mappedBy 속성으로 주인을 지정!

// 누구를 주인으로?
// 외래 키가 있는 곳을 주인으로 정해라

@Entity
public class MemberRelationTwoWay {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne // MemberRelation 입장에서는 Many, Team 입장에서는 One. [N:1]
    @JoinColumn(name = "TEAM_ID") // foreign key랑 매핑해야해서 JoinColumn 사용
    private TeamTwoWay teamTwoWay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TeamTwoWay getTeam() {
        return teamTwoWay;
    }

    public void setTeam(TeamTwoWay teamTwoWay) {
        this.teamTwoWay = teamTwoWay;
    }
}
