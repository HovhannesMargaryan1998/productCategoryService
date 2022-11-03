package com.example.productcategoryservice.config;

import com.example.productcategoryservice.model.Role;
import com.example.productcategoryservice.security.CurrentUserDetailServiceImpl;
import com.example.productcategoryservice.security.JWTAuthenticationTokenFilter;
import com.example.productcategoryservice.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final CurrentUserDetailServiceImpl userDetailService;


    private final PasswordEncoder passwordEncoder;


    private final JwtAuthenticationEntryPoint unauthorizedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/categories").permitAll()
                .antMatchers(HttpMethod.GET,"/categories/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/categories").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/categories/{id}").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/categories/{id}").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/products").permitAll()
                .antMatchers(HttpMethod.GET,"/products/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/products/{categoryId}").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.POST,"/users/auth").permitAll()


                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }


    @Bean
    public JWTAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationTokenFilter();
    }
}
