package org.launchcode.liftoff.shoefinder.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    // Using BCryptPasswordEncoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/", "/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll(
                        )
                );

        return http.build();
    }

    // Security filter  use .requestMatchers to control access
    // Login security is handled here
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                                .requestMatchers("/", "/home", "/register" ,"/register/save").permitAll()
//                                .requestMatchers("/**").hasRole("USER")
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll());
//
//        return http.build();
//    }





//                    .requestMatchers("/", "/home", "/register" ,"/register/save", "register", "register/save").permitAll()
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//            )
//            .formLogin(formLogin -> formLogin
//                    .loginPage("/login")
////                    .failureUrl("/login?error=true")
//                    .permitAll()
//            ).logout( logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
////         .rememberMe(Customizer.withDefaults());
//
//        return http.build();
//
//    }
//

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }





}
