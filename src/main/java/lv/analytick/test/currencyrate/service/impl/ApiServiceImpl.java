package lv.analytick.test.currencyrate.service.impl;

import lv.analytick.test.currencyrate.dto.CryptoCurrencyRateDto;
import lv.analytick.test.currencyrate.dto.FiatCurrencyRateDto;
import lv.analytick.test.currencyrate.service.ApiService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    private final WebClient.Builder webClientBuilder;

    public ApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<List<FiatCurrencyRateDto>> getFiatData() {
        return webClientBuilder.build().get().uri("http://localhost:8080/fiat-currency-rates").retrieve().bodyToMono(new ParameterizedTypeReference<List<FiatCurrencyRateDto>>(){}).onErrorResume(e -> Mono.just(Collections.emptyList()));
    }

    @Override
    public Mono<List<CryptoCurrencyRateDto>> getCryptoData() {
        return webClientBuilder.build().get().uri("http://localhost:8080/crypto-currency-rates").retrieve().bodyToMono(new ParameterizedTypeReference<List<CryptoCurrencyRateDto>>(){}).onErrorResume(e -> Mono.just(Collections.emptyList()));
    }
}
