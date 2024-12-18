package com.ari.vendormanagement.authentication;

import com.ari.vendormanagement.authentication.model.UserPrinciple;
import com.ari.vendormanagement.persistence.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {

  public static User getLoginUser() {
    return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUser();
  }

  public static UserPrinciple getUserPrinciple() {
    return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
  }

  public static String getUserAgent() {
    return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUserAgent();
  }

  public static String getIpAddress() {
    return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getIpAddress();
  }

}