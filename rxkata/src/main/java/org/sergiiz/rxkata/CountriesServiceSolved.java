package org.sergiiz.rxkata;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

class CountriesServiceSolved implements CountriesService {

    @Override
    public Single<String> countryNameInCapitals(Country country) {

        return Single.just(country.getName().toUpperCase(Locale.US));
    }

    public Single<Integer> countCountries(List<Country> countries) {
        return Single.just(countries.size());
    }

    public Observable<Long> listPopulationOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(new Function<Country, Long>() {
                    @Override
                    public Long apply(Country country) throws Exception {
                        return country.getPopulation();
                    }
                }); // put your solution here;
    }

    @Override
    public Observable<String> listNameOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(new Function<Country, String>() {
                    @Override
                    public String apply(Country country) throws Exception {
                        return country.getName();
                    }
                }); // put your solution here
    }

    @Override
    public Observable<Country> listOnly3rdAnd4thCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .skip(2)
                .take(2); // put your solution here
    }

    @Override
    public Single<Boolean> isAllCountriesPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries)
                .all(new Predicate<Country>() {
                    @Override
                    public boolean test(Country country) throws Exception {
                        return country.getPopulation() > 1000000;
                    }
                }); // put your solution here
    }


    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries)
                .filter(new Predicate<Country>() {
                    @Override
                    public boolean test(Country country) throws Exception {
                        return country.getPopulation() > 1000000;
                    }
                }); // put your solution here
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillionWithTimeoutFallbackToEmpty(final FutureTask<List<Country>> countriesFromNetwork) {
        return Observable.fromFuture(countriesFromNetwork, Schedulers.io())
                .flatMap(new Function<List<Country>, Observable<Country>>() {
                    @Override
                    public Observable<Country> apply(List<Country> countries) throws Exception {
                        return Observable.fromIterable(countries);
                    }
                })
                .filter(new Predicate<Country>() {
                    @Override
                    public boolean test(Country country) throws Exception {
                        return country.getPopulation() > 1000000;
                    }
                })
                .timeout(1, TimeUnit.SECONDS, Observable.empty()); // put your solution here
    }

    @Override
    public Observable<String> getCurrencyUsdIfNotFound(String countryName, List<Country> countries) {
        return Observable.fromIterable(countries)
                .filter(new Predicate<Country>() {
                    @Override
                    public boolean test(Country country) throws Exception {
                        return country.getName().equals(countryName);
                    }
                })
                .map(new Function<Country, String>() {
                    @Override
                    public String apply(Country country) throws Exception {
                        return country.getCurrency();
                    }
                })
                .defaultIfEmpty("USD"); // put your solution here
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(new Function<Country, Long>() {
                    @Override
                    public Long apply(Country country) throws Exception {
                        return country.getPopulation();
                    }
                })
                .reduce(new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long a, Long b) throws Exception {
                        return a + b;
                    }
                })
                .toObservable(); // put your solution here
    }

    @Override
    public Single<Map<String, Long>> mapCountriesToNamePopulation(List<Country> countries) {
        return Observable.fromIterable(countries)
                .toMap(new Function<Country, String>() {
                    @Override
                    public String apply(Country country) throws Exception {
                        return country.getName();
                    }
                }, new Function<Country, Long>() {
                    @Override
                    public Long apply(Country country) throws Exception {
                        return country.getPopulation();
                    }
                }); // put your solution heresl
    }
}
