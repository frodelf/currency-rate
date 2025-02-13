package lv.analytick.test.currencyrate.service.impl;

import lv.analytick.test.currencyrate.dto.CryptoCurrencyRateDto;
import lv.analytick.test.currencyrate.dto.FiatCurrencyRateDto;
import lv.analytick.test.currencyrate.dto.ResponseDto;
import lv.analytick.test.currencyrate.entity.CurrencyType;
import lv.analytick.test.currencyrate.repository.CurrencyRepository;
import lv.analytick.test.currencyrate.service.ApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyRatesServiceImplTest {
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private ApiService apiService;
    @InjectMocks
    private CurrencyRatesServiceImpl currencyRatesService;

    @Test
    void getCurrencyRates() {
        ResponseDto responseDto = new ResponseDto();

        when(apiService.getFiatData()).thenReturn(Mono.just(Collections.emptyList()));
        when(apiService.getCryptoData()).thenReturn(Mono.just(Collections.emptyList()));
        when(currencyRepository.findAllByType(CurrencyType.FIAT)).thenReturn(Flux.empty());
        when(currencyRepository.findAllByType(CurrencyType.CRYPTO)).thenReturn(Flux.empty());

        Mono<ResponseDto> result = currencyRatesService.getCurrencyRates();

        StepVerifier.create(result)
                .expectNext(responseDto)
                .verifyComplete();
    }

    @Test
    void addCryptoToResponse() {
        ResponseDto responseDto = new ResponseDto();
        FiatCurrencyRateDto fiatDto = new FiatCurrencyRateDto("USD", 1.2);
        List<FiatCurrencyRateDto> fiatList = List.of(fiatDto);

        when(apiService.getFiatData()).thenReturn(Mono.just(fiatList));
        when(currencyRepository.findAllByType(CurrencyType.FIAT)).thenReturn(Flux.empty());
        doReturn(Mono.empty()).when(currencyRepository).updateRateByCurrencyCode(any(), anyDouble(), any());

        Mono<ResponseDto> result = currencyRatesService.addFiatToResponse(responseDto);

        StepVerifier.create(result)
                .assertNext(dto -> dto.getFiat().equals(fiatList))
                .verifyComplete();
    }

    @Test
    void addFiatToResponse() {
        ResponseDto responseDto = new ResponseDto();
        CryptoCurrencyRateDto cryptoDto = new CryptoCurrencyRateDto("BTC", 45000);
        List<CryptoCurrencyRateDto> cryptoList = List.of(cryptoDto);

        when(apiService.getCryptoData()).thenReturn(Mono.just(cryptoList));
        when(currencyRepository.findAllByType(CurrencyType.CRYPTO)).thenReturn(Flux.empty());
        doReturn(Mono.empty()).when(currencyRepository).updateRateByCurrencyCode(any(), anyDouble(), any());

        Mono<ResponseDto> result = currencyRatesService.addCryptoToResponse(responseDto);

        StepVerifier.create(result)
                .assertNext(dto -> dto.getCrypto().equals(cryptoList))
                .verifyComplete();
    }
}