package com.project.courseapp.fillters;

import com.project.courseapp.components.JwtTokenUtil;
import com.project.courseapp.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFillter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            final String token = authorizationHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if(jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request){
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),
                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST")
//                Pair.of(String.format("%s/products", apiPrefix), "POST")
        );
        for (Pair<String, String> token : bypassTokens) {
            if (request.getServletPath().contains(token.getLeft()) &&
                    request.getMethod().equals(token.getRight())) {
                return true;
            }
        }
        return false;
    }
}
