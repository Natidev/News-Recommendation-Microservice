package com.ds.news_fetcher_service.config;

import com.ds.news_fetcher_service.models.provider.ProviderConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "news")
@Data
public class NewsProviderProperties {
    private List<ProviderConfig> providers = new ArrayList<>();
}
