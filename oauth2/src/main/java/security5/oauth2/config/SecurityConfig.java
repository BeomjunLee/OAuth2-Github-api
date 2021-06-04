package security5.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import security5.oauth2.security.CustomOAuth2MemberService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable() //csrf 비활성화
                .authorizeRequests()
                .anyRequest().authenticated() //어떤 요청이든 권한이 필요, 지금은 허가
            .and()
                .oauth2Login()
                .loginPage("/login").permitAll() //login 은 아무나 허가
                .defaultSuccessUrl("/") //로그인 성공후 url이동
                .userInfoEndpoint() //OAuth2 로그인 성공 후 사용자 정보를 가져올 때 설정
                .userService(customOAuth2MemberService);
    }

}
