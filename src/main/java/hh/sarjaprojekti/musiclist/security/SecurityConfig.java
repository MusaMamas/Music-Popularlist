package hh.sarjaprojekti.musiclist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import hh.sarjaprojekti.musiclist.web.UserDetailService;

@Configuration
public class SecurityConfig {

    private final UserDetailService userDetailService;

    public SecurityConfig(@Lazy UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/postgres/**")).permitAll() 
                        .requestMatchers(mvcMatcherBuilder.pattern("/css/**"),
                                mvcMatcherBuilder.pattern("/js/**"),
                                mvcMatcherBuilder.pattern("/images/**"),
                                mvcMatcherBuilder.pattern("/register"))
                        .permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasAuthority("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/**")).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/postgres/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")))
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()))
                .formLogin(form -> form
                        .defaultSuccessUrl("/tracklist", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}