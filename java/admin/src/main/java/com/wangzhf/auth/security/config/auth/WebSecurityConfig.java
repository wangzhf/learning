package com.wangzhf.auth.security.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 设置Http验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用jwt，无需csrf
        http.csrf().disable()
            // 基于Token,无需session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()

            // 对请求进行认证
            .authorizeRequests()

            // 允许对网站静态资源无授权访问
            .antMatchers(
                HttpMethod.GET,
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.css",
                "/**/*.js"
            ).permitAll()

            // 放行/login的post请求
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            // 放行获取认证信息的请求
            .antMatchers("/auth/**").permitAll()

            // 权限检查
            // .antMatchers("/hello").hasAuthority("AUTH_WRITE")
            // 角色检查
            // .antMatchers("/world").hasRole("ADMIN")
            // 所有请求都需要认证
            .anyRequest().authenticated()

            .and()

            // 添加一个过滤器，所有访问/login的请求交给JWTLoginFilter来处理
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义的身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }
}
