package com.example.demo.jwt.security;


import com.example.demo.jwt.config.CustomAccessDeniedHandler;
import com.example.demo.jwt.config.RestAuthenticationEntryPoint;
import com.example.demo.jwt.filter.JwtAuthenticationFilter;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        List<User> users = (List<User>) userService.findAll();
        if (users.isEmpty()){
            User user =new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            userService.save(user);
        }
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // allows specifying HttpServlet that should not use CSRF protection even if they match the requireCSRFProtectionMatcher(RequestMatcher)
        http.csrf().ignoringAntMatchers("/**");

        // authenticationEntryPoint . used by ExceptionTranslationFilter to commence an authe schema
        http.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint());
        http.authorizeRequests()
                .antMatchers("/**","/login").permitAll()
//                .anyRequest().authenticated()
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/user").hasAnyAuthority("CUSTOMER")
                .and().csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}
