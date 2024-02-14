package br.com.vemser.naturezaconectada.naturezaconectada.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/auth", "/").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/cliente").permitAll()
                        .antMatchers(HttpMethod.PUT,"/cliente/{id}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/cliente/status/{id}").hasAnyRole("CLIENTE", "ADMIN")
                        .antMatchers(HttpMethod.GET,"/cliente").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/cliente/{id}").hasAnyRole("ADMIN","CLIENTE")
                        .antMatchers(HttpMethod.GET,"/cliente/ativos").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/contato/{idCliente}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/contato/{idContato}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/contato/remover/{idContato}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.GET,"/contato").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/contato/{idCliente}").hasAnyRole("CLIENTE", "ADMIN")
                        .antMatchers(HttpMethod.POST,"/endereco/{idCliente}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/endereco/{idEndereco}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/endereco/remover/{idEndereco}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.GET,"/endereco").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/endereco/{idEndereco}").hasAnyRole("CLIENTE", "ADMIN")
                        .antMatchers(HttpMethod.PUT,"/endereco/ativar/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/endereco/ativos").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/entrega/{idEndereco}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.GET,"/entrega").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/entrega/{idEntrega}").hasAnyRole("CLIENTE", "ADMIN")
                        .antMatchers(HttpMethod.PUT,"/entrega/{idEntrega}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT, "/entrega/status/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/especialista").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/especialista/{id}").hasRole("ESPECIALISTA")
                        .antMatchers(HttpMethod.GET,"/especialista/admin/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/especialista").permitAll()
                        .antMatchers(HttpMethod.PUT,"/especialista/status/{id}").hasAnyRole("ESPECIALISTA", "ADMIN")
                        .antMatchers(HttpMethod.PUT,"/especialista/{idEspecialista}").hasRole("ESPECIALISTA")
                        .antMatchers(HttpMethod.GET,"/mudas").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/mudas/ativas").permitAll()
                        .antMatchers(HttpMethod.GET,"/mudas/{idMuda}").permitAll()
                        .antMatchers(HttpMethod.GET,"/mudas/eco").permitAll()
                        .antMatchers(HttpMethod.POST,"/mudas").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT,"/mudas/{idMuda}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT,"/mudas/status/{idMuda}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/mudas/doacoes").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/relatorio").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.GET,"/relatorio/espec/{idEspecialista}").hasAnyRole("ESPECIALISTA","ADMIN")
                        .antMatchers(HttpMethod.GET,"/relatorio/cliente/{id}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.GET,"/relatorio/aberto").hasAnyRole("ESPECIALISTA","ADMIN")
                        .antMatchers(HttpMethod.PUT,"/relatorio/avaliacao/{idRelatorio}").hasRole("ESPECIALISTA")
                        .antMatchers(HttpMethod.PUT,"/relatorio/{idRelatorio}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.GET,"/relatorio").hasRole("ADMIN")
                        .antMatchers("/usuario/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}