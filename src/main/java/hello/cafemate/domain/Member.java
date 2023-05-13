package hello.cafemate.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Member {

    private Long id;
    private String memberId;
    private String password;
    private String name;
    private String phoneNumber;
    private String eMail;
    private boolean isAdmin;

    public Member() {
    }

    public Member(Long id, String memberId, String password,
                  String name, String phoneNumber, String eMail, boolean isAdmin) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.isAdmin = isAdmin;
    }

    public Member(String memberId, String password, String name,
                  String phoneNumber, String eMail, boolean isAdmin) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.isAdmin = isAdmin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * BeanPropertySqlParameterSource 사용시 매핑 문제로 인해
     * 별도의 is- 네이밍의 getter 메서드 설정
     */

    public boolean isIsAdmin(){
        return isAdmin;
    }

}
