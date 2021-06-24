package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamTwoWay {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "teamTwoWay") // 연관관계의 주인 MemberRelationTwoWay 객체의 teamTwoWay 필에 의해 매핑됐다.
    private List<MemberRelationTwoWay> members = new ArrayList<>(); // 'MemberRelationTwoWay - TeamTwoWay' 객체 간 양방향 매핑이 가능

    public List<MemberRelationTwoWay> getMembers() {
        return members;
    }

    public void setMembers(List<MemberRelationTwoWay> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
