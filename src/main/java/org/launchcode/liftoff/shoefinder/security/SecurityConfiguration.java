package org.launchcode.liftoff.shoefinder.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// todo COMMENTED OUT JUST FOR BUILDING restore this for security to function

//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
// todo COMMENTED OUT JUST FOR BUILDING restore this for security to function
//@EnableWebSecurity
public class SecurityConfiguration {

 // todo COMMENTED OUT JUST FOR BUILDING restore this for security to function
    //    private CustomUserDetailsService userDetailsService;
//
//    @Autowired
//    public SecurityConfiguration(CustomUserDetailsService userDetailsService){
//        this.userDetailsService = userDetailsService;
//    }


    // Using BCryptPasswordEncoder
    // todo COMMENTED OUT JUST FOR BUILDING restore this for security to function
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/", "/register", "/css**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login").permitAll()
//                        .defaultSuccessUrl("/")
//                        .loginProcessingUrl("/login")
//                        .failureUrl("/login?error=true").permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
//                );
//
//        return http.build();
//    }
//    public void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }


}
