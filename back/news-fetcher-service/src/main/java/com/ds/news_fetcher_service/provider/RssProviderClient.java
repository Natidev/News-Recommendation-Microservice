package com.ds.news_fetcher_service.provider;

import com.ds.news_fetcher_service.models.payload.RawNewsPayload;
import com.ds.news_fetcher_service.models.provider.ProviderConfig;
import com.ds.news_fetcher_service.parsers.RssParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class RssProviderClient implements NewsProviderClient{
    private final WebClient webClient;
    @Override
    public Flux<RawNewsPayload> fetch(ProviderConfig provider) {
        return Flux.fromIterable(provider.getFeeds())
                .flatMap(feed->
                        webClient.get()
                                .uri(feed.getUrl())
                                .retrieve()
                                .bodyToMono(String.class)
                                .doOnSubscribe(s ->
                                        log.info("HTTP request to {}", feed.getUrl())
                                )
                                .doOnNext(xml ->
                                        log.debug("Received XML size={} chars, actual xml: {}", xml.length(), xml)
                                )
                                .flatMapMany(xml-> RssParser.parse(xml,provider, feed)
                                        .doOnNext(item ->
                                            log.info("Parsed item {}", item.getTitle())
                                        )
                                )
                        );
    }


}
