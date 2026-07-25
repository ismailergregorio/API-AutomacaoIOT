package com.example.AutomacaoIOT.Security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryUser;
import com.example.AutomacaoIOT.Service.auth.TokenServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

        private final TokenServices tokenServices;
        private final RepositoryUser repositoryUsuarios;

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {

                String token = recuperarToken(request);

                try {

                        if (token != null) {

                                String email = tokenServices.validarToken(token);

                                ModelUser usuario = repositoryUsuarios.findByEmail(email);

                                if (usuario == null) {
                                        retornarErro401(response, "Usuário não encontrado.");
                                        return;
                                }

                                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                                usuario,
                                                null,
                                                usuario.getAuthorities());

                                SecurityContextHolder.getContext().setAuthentication(auth);
                        }

                        filterChain.doFilter(request, response);

                } catch (JWTVerificationException e) {

                        retornarErro401(response, "Token inválido ou expirado.");

                } catch (Exception e) {

                        retornarErro401(response, "Falha na autenticação.");

                }
        }

        private void retornarErro401(HttpServletResponse response, String mensagem)
                        throws IOException {

                SecurityContextHolder.clearContext();

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write("""
                                {
                                    "status":401,
                                    "erro":"Unauthorized",
                                    "mensagem":"%s"
                                }
                                """.formatted(mensagem));

                response.getWriter().flush();
        }

        private String recuperarToken(HttpServletRequest request) {

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        return null;
                }

                return authHeader.substring(7);
        }
}