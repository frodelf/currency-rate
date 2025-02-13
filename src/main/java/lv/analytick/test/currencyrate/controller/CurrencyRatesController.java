package lv.analytick.test.currencyrate.controller;

import lv.analytick.test.currencyrate.dto.ResponseDto;
import lv.analytick.test.currencyrate.service.CurrencyRatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/currency-rates")
public class CurrencyRatesController {
    private final CurrencyRatesService currencyRatesService;

    public CurrencyRatesController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @GetMapping
    public ResponseEntity<Mono<ResponseDto>> getCurrencyRates() {
        return ResponseEntity.ok(currencyRatesService.getCurrencyRates());
    }
}