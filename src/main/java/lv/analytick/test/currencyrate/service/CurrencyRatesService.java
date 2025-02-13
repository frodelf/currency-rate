package lv.analytick.test.currencyrate.service;

import lv.analytick.test.currencyrate.dto.ResponseDto;
import lv.analytick.test.currencyrate.entity.Currency;
import lv.analytick.test.currencyrate.entity.CurrencyType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyRatesService {
    Mono<ResponseDto> getCurrencyRates();
    Mono<ResponseDto> addCryptoToResponse(ResponseDto responseDto);
    Mono<ResponseDto> addFiatToResponse(ResponseDto responseDto);
    Flux<Currency> getAllFiatByType();
    Flux<Currency> getAllCryptoByType();
    Mono<Void> updateRateByCurrencyCode(String currencyCode, double rate, CurrencyType type);
}