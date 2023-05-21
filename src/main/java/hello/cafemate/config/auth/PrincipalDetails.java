package hello.cafemate.config.auth;

import hello.cafemate.domain.Customer;
import hello.cafemate.domain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Customer customer;
    private final Member member;

    public PrincipalDetails(Customer customer,Member member){
        this.customer = customer;
        this.member = member;
    }
    //권한을 가져오는 메서드(user의 role)
    //유저마다 권한이 1개가 아닐수도 있기 때문에 컬렉션 타입으로 받아야 한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities =  new ArrayList<>();
        if(customer != null){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        if(member!=null){
            if(member.isIsAdmin()){
                System.out.println("어드민 접근");
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }else{
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            }
        }
        return authorities;
    }

    //user의 정보를 가져오는 Getter
    @Override //user의 password를 가져오는 메서드
    public String getPassword() {
        if(customer!=null)return customer.getPassword();
        else return member.getPassword();
    }

    @Override //user의 username을 가져오는 메서드
    public String getUsername() {
        if(customer!=null)return customer.getCustomerId();
        else return member.getMemberId();
    }



    //return이 true일때, 정상적으로 로그인 로직이 실행됨
    @Override //계정이 만료되지 않았는가?
    public boolean isAccountNonExpired() {
        return true; //응, 만료되지 않았어.
    }

    @Override //계정이 잠기지 않았는가?
    public boolean isAccountNonLocked() {
        return true; //응 계정이 잠기지 않았어
    }

    @Override //비밀번호 변경한지 오래되지 않았는가?
    public boolean isCredentialsNonExpired() {
        return true; //응, 변경한지 오래되지 않았어
    }

    @Override //계정이 활성화 되어있는가?
    public boolean isEnabled() {
        return true; //응 , 계정 활성화가 되어있어
    }
}
