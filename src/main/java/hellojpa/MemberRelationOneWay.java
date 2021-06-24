package hellojpa;

import javax.persistence.*;

@Entity
public class MemberRelationOneWay {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne // MemberRelation 입장에서는 Many, Team 입장에서는 One. [N:1]
    @JoinColumn(name = "TEAM_ID") // foreign key랑 매핑해야해서 JoinColumn 사용
    private TeamOneWay teamOneWay;

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

    public TeamOneWay getTeam() {
        return teamOneWay;
    }

    public void setTeam(TeamOneWay teamOneWay) {
        this.teamOneWay = teamOneWay;
    }
}
