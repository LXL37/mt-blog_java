package com.meet.config;

import com.meet.filter.JwtAuthenticationTokenFilter;
import com.meet.handler.security.AccessDeniedHandlerImpl;
import com.meet.handler.security.AuthenticationEntryPointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 注册接口 允许匿名访问
                .antMatchers("/login").anonymous()
                .antMatchers("/user/register").anonymous()
                //需要认证才能访问的借口
                /* .antMatchers("/logout").authenticated()
                 .antMatchers("/user/userInfo").authenticated()*/
                .anyRequest().authenticated();
        // 除上面外的所有请求全部不需要认证即可访问
        /* .anyRequest().permitAll()*/
        //配置异常处理器
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

     /*    .authenticationEntryPoint(authenticationEntryPoint);*/
        //屏蔽掉默认的注销功能
        http.logout().disable();
        //jwt权限
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //允许跨域
        http.cors();
    }

}