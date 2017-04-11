package com.yzz.encache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yzz.util.TimeUtil;

@Service
public class EhCacheTestServiceImpl implements EhCacheTestService {

    @Cacheable(value="cacheTest",key="#param")
    public String getTimestamp(String param) {
        
        return TimeUtil.getCurrentTimeString();
    }

}
