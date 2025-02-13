package lv.analytick.test.currencyrate.service;

import lv.analytick.test.currencyrate.dto.CryptoCurrencyRateDto;
import lv.analytick.test.currencyrate.dto.FiatCurrencyRateDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApiService {
    Mono<List<FiatCurrencyRateDto>> getFiatData();
    Mono<List<CryptoCurrencyRateDto>> getCryptoData();
}