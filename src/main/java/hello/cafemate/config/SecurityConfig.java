package hello.cafemate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //비밀번호 암호화 시키는 메서드 IoC 컨테이너에 등록
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/user/**")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin() //로그인(인증)이 필요한 요청이 들어오면
                .loginPage("/auth/signin")//로그인페이지 auth/signin으로 이동시키고(GET)
                .loginProcessingUrl("/auth/signin")//POST->스프링시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/"); //인증이 정상적으로 완료되면 /로 이동한다.

        return httpSecurity.build();


    }


}
