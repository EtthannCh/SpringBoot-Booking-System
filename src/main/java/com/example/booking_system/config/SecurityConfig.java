package com.example.booking_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     return http
    //             .csrf(AbstractHttpConfigurer::disable) // for Postman / REST APIs
    //             .authorizeHttpRequests(auth -> {
    //                 auth.requestMatchers("/api/auth/token").permitAll();
    //                 auth.anyRequest().authenticated();
    //             })
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())).build();
    // }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    //     UserDetails user = User.withUsername("user")
    //             .password(encoder.encode("mytemporarypass"))
    //             .roles("USER")
    //             .build();
    //     return new InMemoryUserDetailsManager(user);
    // }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        var authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
