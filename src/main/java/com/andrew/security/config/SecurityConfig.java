package com.andrew.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;


@Configuration
@ComponentScan("com.andrew")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("username")
                        .password(passwordEncoder.encode("password"))
                        .roles("role"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/seniorCashier/**").hasAuthority("SENIOR_CASHIER")
                .antMatchers("/cashier/**").hasAuthority("CASHIER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/productObserver/**").hasAuthority("PRODUCT_OBSERVER")
                //.antMatchers("/").authenticated()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/signUp").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .defaultSuccessUrl("/roleController")
                .loginPage("/login")
                .permitAll();
        http.csrf().disable();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
