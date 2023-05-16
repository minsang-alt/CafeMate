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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/user/**").hasAnyRole("ADMIN", "MANAGER") // "/user/**" 패턴에 대한 접근 제어
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") // 로그인 페이지
                .loginProcessingUrl("/auth/signin") // 로그인 처리 URL
                .defaultSuccessUrl("/"); // 로그인 성공 시 이동할 기본 URL

        return httpSecurity.build();
    }
}
