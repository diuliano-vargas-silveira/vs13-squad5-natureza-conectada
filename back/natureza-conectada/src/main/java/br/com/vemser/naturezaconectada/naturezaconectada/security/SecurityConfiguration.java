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
                        .antMatchers("/auth").permitAll()
                        .antMatchers(HttpMethod.POST,"/cliente").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/cliente/{id}").hasRole("CLIENTE")
                        .antMatchers(HttpMethod.PUT,"/cliente/status/{id}").hasAnyRole("CLIENTE", "ADMIN")
                        .antMatchers(HttpMethod.GET,"/cliente").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/cliente/{id}").hasRole("ADMIN")
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