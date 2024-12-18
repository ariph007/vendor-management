package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.persistence.entity.User;
import com.ari.vendormanagement.service.RateLimiterService;
import com.ari.vendormanagement.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import java.time.Duration;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiterService {

  private final UserService userService;

  private final ProxyManager<String> proxyManager;

  public Bucket resolveBucket(String key) {
    Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(key);

    return proxyManager.builder().build(key, configSupplier);
  }

  private Supplier<BucketConfiguration> getConfigSupplierForUser(String userId) {
    User user = userService.getByUsername(userId);
    int roleLimit = user.getRole().getLimit();
    Refill refill = Refill.intervally(roleLimit, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(roleLimit, refill);
    return () -> (BucketConfiguration.builder()
        .addLimit(limit)
        .build());
  }

}
