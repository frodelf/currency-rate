package lv.analytick.test.currencyrate.service.impl;

import lv.analytick.test.currencyrate.dto.CryptoCurrencyRateDto;
import lv.analytick.test.currencyrate.dto.FiatCurrencyRateDto;
import lv.analytick.test.currencyrate.dto.ResponseDto;
import lv.analytick.test.currencyrate.entity.Currency;
import lv.analytick.test.currencyrate.entity.CurrencyType;
import lv.analytick.test.currencyrate.repository.CurrencyRepository;
import lv.analytick.test.currencyrate.service.ApiService;
import lv.analytick.test.currencyrate.service.CurrencyRatesService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CurrencyRatesServiceImpl implements CurrencyRatesService {
    private final CurrencyRepository currencyRepository;
    private final ApiService apiService;

    public CurrencyRatesServiceImpl(CurrencyRepository currencyRepository, ApiService apiService) {
        this.currencyRepository = currencyRepository;
        this.apiService = apiService;
    }

    @Override
    public Mono<ResponseDto> getCurrencyRates() {
        ResponseDto responseDto = new ResponseDto();

        return Mono.zip(addFiatToResponse(responseDto), addCryptoToResponse(responseDto))
                .thenReturn(responseDto);
    }

    @Override
    public Mono<ResponseDto> addCryptoToResponse(ResponseDto responseDto) {
        return apiService.getCryptoData()
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(getAllCryptoByType()
                        .map(currency -> new CryptoCurrencyRateDto(currency.getCurrencyCode(), currency.getRate())))
                .collectList()
                .doOnNext(responseDto::setCrypto)
                .flatMap(crypto -> uploadCrypto(crypto).thenReturn(responseDto));
    }

    @Override
    public Mono<ResponseDto> addFiatToResponse(ResponseDto responseDto) {
        return apiService.getFiatData()
                .flatMapMany(Flux::fromIterable)
                .switchIfEmpty(getAllFiatByType()
                        .map(currency -> new FiatCurrencyRateDto(currency.getCurrencyCode(), currency.getRate())))
                .collectList()
                .doOnNext(responseDto::setFiat)
                .flatMap(fiat -> uploadFiat(fiat).thenReturn(responseDto));
    }

    private Mono<Void> uploadFiat(List<FiatCurrencyRateDto> fiat) {
        return Flux.fromIterable(fiat)
                .flatMap(dto -> updateRateByCurrencyCode(dto.getCurrency(), dto.getRate(), CurrencyType.FIAT))
                .then();
    }

    private Mono<Void> uploadCrypto(List<CryptoCurrencyRateDto> crypto) {
        return Flux.fromIterable(crypto)
                .flatMap(dto -> updateRateByCurrencyCode(dto.getName(), dto.getValue(), CurrencyType.CRYPTO))
                .then();
    }

    @Override
    public Flux<Currency> getAllFiatByType() {
        return currencyRepository.findAllByType(CurrencyType.FIAT);
    }

    @Override
    public Flux<Currency> getAllCryptoByType() {
        return currencyRepository.findAllByType(CurrencyType.CRYPTO);
    }

    @Override
    public Mono<Void> updateRateByCurrencyCode(String currencyCode, double rate, CurrencyType type) {
        return currencyRepository.updateRateByCurrencyCode(currencyCode, rate, type);
    }
}