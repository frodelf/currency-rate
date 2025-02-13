package lv.analytick.test.currencyrate.repository;

import lv.analytick.test.currencyrate.entity.Currency;
import lv.analytick.test.currencyrate.entity.CurrencyType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyRepository extends R2dbcRepository<Currency, Long> {
    Flux<Currency> findAllByType(CurrencyType type);
    @Query("""
    INSERT INTO currency (currency_code, rate, type) 
    VALUES (:currencyCode, :rate, :type) 
    ON CONFLICT (currency_code) 
    DO UPDATE SET rate = :rate, type = :type
""")
    Mono<Void> updateRateByCurrencyCode(@Param("currencyCode") String currencyCode, @Param("rate") double rate, @Param("type") CurrencyType type);
}