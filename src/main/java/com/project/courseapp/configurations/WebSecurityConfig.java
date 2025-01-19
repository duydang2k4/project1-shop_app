package com.project.courseapp.configurations;

import com.project.courseapp.fillters.JwtTokenFillter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig {
    private final JwtTokenFillter jwtTokenFillter;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFillter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/roles", apiPrefix))
                            .permitAll()

                            .requestMatchers(PUT,
                                    String.format("%s/categories/**", apiPrefix)).hasRole( "ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/categories/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST,
                                    String.format("%s/categories/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(GET,
                                    String.format("%s/categories/**", apiPrefix)).permitAll()

                            .requestMatchers(PUT,
                                    String.format("%s/products/**", apiPrefix)).hasRole( "ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST,
                                    String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(GET,
                                    String.format("%s/products/**", apiPrefix)).permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/orders/**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                            .requestMatchers(POST,
                                    String.format("%s/orders/**", apiPrefix)).hasRole("USER")
                            .requestMatchers(PUT,
                                    String.format("%s/orders/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/orders/**", apiPrefix)).hasRole("ADMIN")

                            .requestMatchers(GET,
                                    String.format("%s/order_details/**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                            .requestMatchers(POST,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole("USER")
                            .requestMatchers(PUT,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole("ADMIN")
                            .anyRequest().authenticated();
        })
                .csrf(AbstractHttpConfigurer::disable);
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("*"));
                corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfiguration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfiguration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
