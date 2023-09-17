package uz.cluster.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import uz.cluster.dao.currency_dto.CurrencyModelDTO;
import uz.cluster.entity.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;

public class CurrencyExchangeRate {

    public static final String URL_SOURCE = "https://cbu.uz/oz/arkhiv-kursov-valyut/json/";  //Previous URL -> "https://nbu.uz/exchange-rates/json/"

    public static final String EXCHANGE_CURRENCY_TYPE_DOLLAR = "USD";

    @SneakyThrows
    public static String downloadDollarExchangeRate() {
        CurrencyModelDTO currency = getCurrentDollar();
        if (currency == null)
            return "0";
        return currency.getRate();
    }

    @SneakyThrows
    public static String downloadDollarExchangeRateByDate(String date) {
        CurrencyModelDTO currency = getCurrentDollarByDate(date);
        if (currency == null)
            return "0";
        return currency.getRate();
    }
    @SneakyThrows
    public static CurrencyModelDTO getCurrentDollarByDate(String date) {
        return getCurrency(CurrencyExchangeRate.EXCHANGE_CURRENCY_TYPE_DOLLAR +  "/" + date + "/");
    }

    public static void calculateDollarByDate(Document document) {
        double value = Double.parseDouble(CurrencyExchangeRate.downloadDollarExchangeRateByDate(String.valueOf(document.getDocumentDate())));
        document.setUsdCourse(value);
        document.setUsdAmount( Math.round(100 * 100* (document.getAmount() + document.getValueAddedTax()) / document.getUsdCourse() / 100));
    }

    public static double calculateDollar(double sumAmount,LocalDate date) {
        double value = Double.parseDouble(CurrencyExchangeRate.downloadDollarExchangeRateByDate(String.valueOf(date)));
        return Math.round(100 * 100* (sumAmount) / value / 100);
    }

    public static double calculateDollarByDateForForm(LocalDate date, double amount) {
        double value = Double.parseDouble(CurrencyExchangeRate.downloadDollarExchangeRateByDate(String.valueOf(date)));
        return Math.round(100 * 100* (amount) / value / 100);
    }

    @SneakyThrows
    public static CurrencyModelDTO getCurrentDollar() {
        return getCurrency(CurrencyExchangeRate.EXCHANGE_CURRENCY_TYPE_DOLLAR + "/");
    }

    @SneakyThrows
    private static CurrencyModelDTO getCurrency(String fullURL) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        URL website = new URL(URL_SOURCE + fullURL);
        URLConnection urlConnection = website.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Type type = new TypeToken<ArrayList<CurrencyModelDTO>>() {
        }.getType();
        ArrayList<CurrencyModelDTO> currencies = gson.fromJson(reader, type);

        for (CurrencyModelDTO currency : currencies) {
            return currency;
        }
        return null;
    }

    public static void main(String[] args) throws Throwable {
        String usd = downloadDollarExchangeRate();
        System.out.println(usd);
    }
}
