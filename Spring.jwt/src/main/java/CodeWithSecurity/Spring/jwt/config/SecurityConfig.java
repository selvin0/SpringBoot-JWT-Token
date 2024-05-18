package CodeWithSecurity.Spring.jwt.config;
import CodeWithSecurity.Spring.jwt.Service.UserDetailsImpl;
import CodeWithSecurity.Spring.jwt.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
        private final UserDetailsImpl userDetailsImpl;
        private final  JwtAuthenticationFilter jwtAuthenticationFilter;
        public SecurityConfig(UserDetailsImpl userDetailsImpl, JwtAuthenticationFilter jwtAuthenticationFilter) {
            this.userDetailsImpl = userDetailsImpl;
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
             return http.csrf(AbstractHttpConfigurer::disable)
                     .authorizeHttpRequests(req->req.requestMatchers("/login/**","/register/**")
                             .permitAll()
                             .requestMatchers("/admin/**").hasAuthority("ADMIN")
                             .anyRequest().authenticated())
                     .userDetailsService(userDetailsImpl)
                     .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                     .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                     .build();// which userDetails spring needs to use

        }

        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }
}
