package github.iocentral_de_adocao_if.demo.config;

import github.iocentral_de_adocao_if.demo.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authorization.AuthorizationDecision;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminRepository adminRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // DESABILITA CSRF (necessário para fetch)
                .csrf(csrf -> csrf.disable())

                // AUTORIZA ROTAS
                .authorizeHttpRequests(auth -> auth

                        // login sempre liberado
                        .requestMatchers("/auth/login").permitAll()

                        // criar admin só se ainda não existir nenhum
                        .requestMatchers(HttpMethod.POST, "/admins")
                        .access((authentication, context) ->
                                new AuthorizationDecision(adminRepository.count() == 0)
                        )

                        // qualquer rota /admins/** exige login
                        .requestMatchers("/admins/**").authenticated()

                        // resto liberado
                        .anyRequest().permitAll()
                )

                // NÃO usar login padrão
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration conf
    ) throws Exception {
        return conf.getAuthenticationManager();
    }
}
