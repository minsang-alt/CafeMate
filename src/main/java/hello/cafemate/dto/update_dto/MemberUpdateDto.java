package hello.cafemate.dto.update_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {
    private String memberId;
    private String password;
    private String name;
    private String phoneNumber;
    private String eMail;
    private Boolean isAdmin=null;

    public MemberUpdateDto(){}
}
