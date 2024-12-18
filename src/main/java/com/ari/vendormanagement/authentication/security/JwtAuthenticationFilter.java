package com.ari.vendormanagement.authentication.security;

import com.ari.vendormanagement.authentication.model.UserPrinciple;
import com.ari.vendormanagement.authentication.service.JwtService;
import com.ari.vendormanagement.service.RateLimiterService;
import com.ari.vendormanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;

  @Setter(onMethod_ = {@Autowired}, onParam_ = {@Lazy})
  private JwtService jwtService;


  @Setter(onMethod_ = {@Autowired}, onParam_ = {@Lazy})
  private UserService userService;

  @Setter(onMethod_ = {@Autowired}, onParam_ = {@Lazy})
  private  RateLimiterService rateLimiterService;


  private void returnError(HttpServletResponse response, String message, int statusValue, String msg) throws IOException {
    response.setStatus(statusValue);
//    HttpStatus.FORBIDDEN.value()
    String content = "";
    String jsonStr = "";
    if (!message.equals(null)) {
//      "Unauthorized! "
      content = msg + message;
      jsonStr = objectMapper.writeValueAsString(content);
    }
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonStr);
    response.getWriter().flush();
    response.getWriter().close();
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String username;
    final String userAgent = request.getHeader("User-Agent");
    final String path = request.getRequestURI().substring(request.getContextPath().length());
    if (userAgent == null) {
      this.returnError(response, "Missing User Agent", HttpStatus.FORBIDDEN.value(), "Unauthorized ! ");
      return;
    }

    final String ipAddress = request.getRemoteAddr();
    if (ipAddress == null) {
      this.returnError(response, "Missing IP Address", HttpStatus.FORBIDDEN.value(), "Unauthorized ! ");
      return;
    }

    if (path.matches(SecurityConstant.PERMITTED_URI)) {
      UserDetails userDetails = UserPrinciple.builder().ipAddress(ipAddress).userAgent(userAgent).build();

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } else {
      String jwt = getJwt(request);
      if (jwt != null) {

        username = jwtService.extractUserName(jwt);


        if (StringUtils.isNotEmpty(username)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
          }
        //!Rate Limiter
        if(StringUtils.isNotBlank(username)) {
          Bucket bucket = rateLimiterService.resolveBucket(username);
          if(bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
          } else {
            this.returnError(response, "Too many requests", HttpStatus.TOO_MANY_REQUESTS.value(), "Unauthorized !");
          }
        }
        } else {
          this.returnError(response, "Invalid token!", HttpStatus.FORBIDDEN.value(), "Unauthorized !");
        }
    }
  }

  private String getJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.replace("Bearer ", "");
    }
    return null;
  }

  private void sendErrorReponse(HttpServletResponse response, int value) {
    HttpServletResponse resp = (HttpServletResponse)response;
    resp.setStatus(value);


    resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
  }
}