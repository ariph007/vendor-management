package com.ari.vendormanagement.service;

import io.github.bucket4j.Bucket;

public interface RateLimiterService {
  Bucket resolveBucket(String key);
}
