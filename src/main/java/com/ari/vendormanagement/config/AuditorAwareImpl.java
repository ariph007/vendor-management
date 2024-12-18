package com.ari.vendormanagement.config;

import com.ari.vendormanagement.authentication.SessionHelper;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;


public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      try {
        return Optional.of(InetAddress.getLocalHost().getHostAddress());
      } catch (UnknownHostException e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown host.");
      }
    } else {
      return Optional
          .of(SessionHelper.getLoginUser() == null ? SessionHelper.getIpAddress()
              : SessionHelper.getLoginUser().getUsername());
    }
  }
}
