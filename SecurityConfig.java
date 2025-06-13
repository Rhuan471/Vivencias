package com.exemplo.eventos.config;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Permite todas as requisições (APENAS PARA DESENVOLVIMENTO)
                // Em produção, você definiria regras mais específicas, por exemplo:
                // .requestMatchers("/perfis/**").authenticated()
                // .requestMatchers("/eventos/**").hasRole("USER")
            );
        return http.build();
    }
}