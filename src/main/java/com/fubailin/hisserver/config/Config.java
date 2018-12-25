package com.fubailin.hisserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "application")
@PropertySource("classpath:application.properties")
public class Config {
    private String jwtSecretKey = "NEgq1BTPn0sQSB/bWCVh7ep0aABScoj3uv1A7ofpJMZlEwRNHHqErPYaxLGHYm7Nlio6r/eeWAG9ZzvMTapIhQ==";
    private String jwtHeader = "Authorization";
    private String jwtPrefix = "Bearer";
    private long jwtExpiration = 30 * 60 * 1000;
}
