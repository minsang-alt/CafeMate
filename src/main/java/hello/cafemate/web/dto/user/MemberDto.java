package hello.cafemate.web.dto.user;

import lombok.Getter;

@Getter
public class MemberDto {
    private String memberId;

    private String password;
    private String name;
    private String phoneNumber;
    private String eMail;
    private boolean isAdmin;

    public MemberDto(String memberId, String password, String name, String phoneNumber, String eMail, boolean isAdmin) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.isAdmin = isAdmin;
    }
}
