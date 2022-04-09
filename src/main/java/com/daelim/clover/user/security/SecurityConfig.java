package com.daelim.clover.user.security;

import com.daelim.clover.user.service.UserService;
import com.daelim.clover.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Log4j2
@Configuration
@EnableWebSecurity      //spring security 를 적용한다는 Annotation
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userService;

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");

    }
    //규칙 설정
    //@param http
    //@throws Exception
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        log.info("시큐리티입장");
        http
                .authorizeRequests()
                    .antMatchers("/login","/sign","/access_denied","resources/**").permitAll()//로그인 권한은 누구나 ,resources 파일도 모든권한
                    //USER, ADMIN 접근 허용
//                    .antMatchers("/userAccess").hasRole("USER")
//                    .antMatchers("/userAccess").hasRole("ADMIN")
//                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/user_access")//로그인 성공 후 리다이렉트 주소
                    .failureUrl("/access_denied")  //인증에 실패했을 떄 보여주는 화면
                    .and()
                .logout()
                .logoutSuccessUrl("/sign")// 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true)//세션날리기
                        .and()
                .csrf().disable(); //로그인창
        log.info("시큐리티끝끝");
    }
    //로그인 인증 처리 메소드
    //@param auth
    //@throws Exception
   @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
       log.info("로그인인증");
            auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());//해당 서비스(userService)에서는  UserDetailsService를  implements해서
       //LoadUserByUsername() 구현해야함 (서비스참고)

    }
}
