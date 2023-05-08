package hello.cafemate.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//SecurityConfig의 loginProcessingUrl메소드가 "/auth/signin"으로 로그인 요청을 했을때 UserDetailService가 낚아채도록 하는데
//이 순간 IoC에 등록되있던 UserDetailsService를 덮어씌운다. PrincipalDetailsService가 IoC에 들어가고 loadUserByUsername이 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService {



    //이 유저네임이 있는 지 없는지만 확인하면되고 비밀번호는 시큐리티가 알아서 처리함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return null;
    }


}
