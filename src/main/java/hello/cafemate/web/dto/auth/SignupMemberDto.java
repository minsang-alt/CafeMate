package hello.cafemate.web.dto.auth;

import hello.cafemate.web.dto.user.MemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SignupMemberDto {
    @NotBlank
    @Size(min=5,max=30)
    private String member_id;
    private String password;
    private String name;
    private String phone_number;
    private String e_mail;
    //나중에 수정 필요
    private boolean isAdmin;

    public SignupMemberDto(String member_id,String password,String name,String phone_number, String e_mail, String isAdmin){
        this.member_id=member_id;
        this.password = password;
        this.name= name;
        this.phone_number = phone_number;
        this.e_mail = e_mail;
        this.isAdmin = Boolean.parseBoolean(isAdmin);
    }

    public boolean isIsAdmin(){
        return isAdmin;
    }

    public MemberDto toEntity(){
        return new MemberDto(
                this.member_id,
                this.password,
                this.name,
                this.phone_number,
                this.e_mail,
                this.isAdmin

        );
    }

}
