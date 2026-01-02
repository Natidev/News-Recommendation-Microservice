package com.ds.news_fetcher_service.provider;

import com.ds.news_fetcher_service.models.payload.RawNewsPayload;
import com.ds.news_fetcher_service.models.provider.ProviderConfig;
import reactor.core.publisher.Flux;

public interface NewsProviderClient {
    Flux<RawNewsPayload> fetch(ProviderConfig provider);
}
