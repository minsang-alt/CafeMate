package hello.cafemate.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {
@NotBlank
@Size(min=5,max=30)
private String userId;
@NotBlank
@Size(min=4,max=10)
private String password;
@Email
private String email;
@NotBlank
@Size(max=10)
private String fullName;

private String nickName;

@NotBlank
@Size(max=13)
private String userContact;





}
