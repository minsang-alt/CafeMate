package hello.cafemate.config.auth;

import hello.cafemate.domain.Customer;
import hello.cafemate.domain.Member;
import hello.cafemate.repository.CustomerRepository;
import hello.cafemate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//SecurityConfig의 loginProcessingUrl메소드가 "/auth/signin"으로 로그인 요청을 했을때 UserDetailService가 낚아채도록 하는데
//이 순간 IoC에 등록되있던 UserDetailsService를 덮어씌운다. PrincipalDetailsService가 IoC에 들어가고 loadUserByUsername이 실행된다.
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final MemberRepository memberRepository;

    //리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만든다
    //이 유저네임이 있는 지 없는지만 확인하면되고 비밀번호는 시큐리티가 알아서 처리함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(username);
        List<Member> members = memberRepository.findByMemberId(username);

        //System.out.println("여기는"+customerOptional.toString());
        //System.out.println("여긴 온다"+members);
        Member member;
        if (customerOptional.isEmpty() && members.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Customer customer = customerOptional.orElse(null);
        if (!members.isEmpty()) {
            member = members.get(0); // 첫 번째 Member 객체를 가져옵니다.
        } else {
            member = null; // 회원이 없는 경우 null을 할당합니다.
        }
       // System.out.println("여긴 온다");
        return new PrincipalDetails(customer, member);

    }


}
