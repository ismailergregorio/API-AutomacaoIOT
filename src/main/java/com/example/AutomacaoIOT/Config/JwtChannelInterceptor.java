package com.example.AutomacaoIOT.Config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryUser;
import com.example.AutomacaoIOT.Service.auth.TokenServices;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

 private final TokenServices tokenService;


 private final RepositoryUser repositoryUser;

 @Override
 public Message<?> preSend(Message<?> message, MessageChannel channel) {

  StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

  if (StompCommand.CONNECT.equals(accessor.getCommand())) {

   String authorization = accessor.getFirstNativeHeader("Authorization");

   if (authorization == null || !authorization.startsWith("Bearer ")) {
    throw new IllegalArgumentException("Token não informado");
   }

   String token = authorization.replace("Bearer ", "");

   String email = tokenService.validarToken(token);

   ModelUser usuario = repositoryUser.findByEmail(email);

   Authentication authentication = new UsernamePasswordAuthenticationToken(
     usuario,
     null,
     usuario.getAuthorities());

   accessor.setUser(authentication);
   SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  return message;
 }
}