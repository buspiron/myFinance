package pl.coderslab.myFinance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pl.coderslab.myFinance.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    public WebSecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/register", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/welcome").authenticated()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/welcome", true)
                        .permitAll()
                        .failureUrl("/login?error=true"))
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .userDetailsService(customUserDetailService);

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return ((request, response, exception) -> {
            request.getSession().setAttribute("errorMessage", "Invalid username or password.");
            response.sendRedirect("/login");
        }
        );
    }

}
