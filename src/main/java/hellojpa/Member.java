package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
// import javax.persistence.Table;
// import javax.persistence.Column;

@Entity
// @Table(name = "USER")
// USER 라는 테이블과 맵핑
public class Member {

    @Id
    private Long id;

    // @Column(name = "username")
    // USER 테이블의 username 컬럼과 맵핑
    private String name;

    // JPA 내부적으로 리플렉션을 사용하기 때문에 기본 생성자 추가
    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
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
