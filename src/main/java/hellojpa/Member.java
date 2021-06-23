package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
// @Entity 속성 정리
// 1) name
// 기본값 : 클래스 이름 그대로 사용
// 같은 클래스 이름이 없으면 가급적 기본값을 사용

// @Table
// @Table 속성 정리
// 1) name
// (name = "USER")
// USER 라는 테이블과 매핑
// 2) catalog
// 데이터베이스 catalog 매핑
// * 카탈로그?
// 데이터베이스의 개체들에 대한 정의를 담고 있는 메타데이터들로 구성된 데이터베이스 내의 인스턴스이다.
// 기본 테이블, 뷰 테이블, 동의어(synonym)들, 값 범위들, 인덱스들, 사용자들, 사용자 그룹 등등과 같은 데이터베이스의 개체들이 저장된 것이다.
// 3) schema
// 데이터베이스 schema 매핑
// 4) uniqueConstraints
// DDL 생성 시에 유니크 제약 조건 생성
public class Member {

    @Id
    //@GeneratedValue
    //자동 생성

    //** GenerationType
    //* IDENTITY : 데이터베이스에 위임, MYSQL 방언의 경우 AUTO_INCREMENT에 해당
    //  * 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용
    //  * JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
    //  * AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
    //  * IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행 하고 DB에서 식별자를 조회

    //* SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용
    //  * 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트(예: 오라클 시퀀스)
    //  오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용
    //  * @SequenceGenerator 필요

    //* TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용
    //  * 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
    //  장점: 모든 데이터베이스에 적용 가능
    //  단점: 성능
    //  * @TableGenerator 필요
    //  * 이미 DB에서 제공되는게 있기 때문에 잘 사용하진 않음

    //* AUTO : 위 전략들 중 하나를 방언에 따라 자동 지정, 기본값

    // 권장하는 식별자 전략
    // 기본키 제약 조건 : null 아님, 유일, 변하면 안된다.
    // 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
    // 예를 들어 주민등록번호도 기본 키로 적절하지 않다. (법적으로 주민번호를 보관하면 불법으로 간주하는 사례가 생기고 나서부터...)
    // 권장 : Long형 + 대체키(예 : 시퀀스, UUID) + 키 생성전략 사용

    private Long id;

    @Column(name = "name")
    //name 컬럼과 맵핑
    private String username;

    private Integer age;

    // @Enumerated
    // 자바의 Enum 타입과 매핑
    // 주의 : ORDINAL 사용 X
    // -> 이렇게 되면 자바 Enum 변경시 그 순서를 DB 데이터와 비교하여 개발자가 신경써가면서 개발해야하고 잘못된 값이 들어갈수도 있 된다.
    // EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
    // EnumType.STRING: enum 이름을 데이터베이스에 저장
    // 기본값 : ORDINAL
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // @Temporal
    // 자바8 이전의 날짜/시간 타입을 매핑할때 사용
    // 자바8에서 지원하는 LocalDate, LocalDateTime을 사용할때는 생략 가능
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // @Lob
    // 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
    // CLOB : String, char[], java.sql.CLOB
    // BLOB : byte[], java.sql.BLOB
    @Lob
    private String description;

    // @Transient
    // DB와 관련없이 메모리에서만 사용하고 싶은 필드

    // JPA 내부적으로 리플렉션을 사용하기 때문에 기본 생성자 추가
    public Member() {
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}