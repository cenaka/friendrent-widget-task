/**
 * Created by 1 on 23.07.15.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static spark.Spark.*;

public class AdvertiseWebService {

    public static final Random RANDOM = new Random();
    public static final String[] SUBWAY_NAMES = new String[]{"Академическая", "Владимирская", "Автово", "Спортивная", "Чкаловская"};
    public static final String[] STREET_NAMES = new String[]{"пр-т Науки", "ул. Гидротехников", "ул. Свободы", "Владимирский пр-т", "ул. Достоевского"};

    public static void main(String[] args) {
        staticFileLocation("/static");
        get("/advertises", (request, response) -> {
            Map<String, Object> ads = new HashMap<>();
            ArrayList<Advertise> advertises = new ArrayList<>();
            String city = request.queryMap("city").value();
            Advertise.Type type;
            int price;
            Map<String, Integer> citiesList = getNumberOfAdsMap();

            int number = 10;
            if (request.queryMap("number").hasValue()) {
                number = request.queryMap("number").integerValue();
            }
            int from = 0;
            if (request.queryMap("from").hasValue()) {
                from = request.queryMap("from").integerValue();
            }
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR, -from);
            int numberOfAdvertises = 1000;
            if (request.queryMap("city").hasValue()) {
                String cityValue = request.queryMap("city").value();
                if(citiesList.containsKey(cityValue))
                    numberOfAdvertises = citiesList.get(cityValue);
            }
            if (numberOfAdvertises - from < number) {
                number = numberOfAdvertises - from;
            }
            for (int i = 0; i < number; i++) {

                if (request.queryMap("type").hasValue()){
                    type = Advertise.Type.valueOf(request.queryMap("type").value().toUpperCase());
                } else {
                    type = Advertise.Type.values()[RANDOM.nextInt(2)];
                }
                int priceFrom = 1, priceTo = 999999;
                if (request.queryMap("priceTo").hasValue()) {
                    priceTo = request.queryMap("priceTo").integerValue();
                }
                if (request.queryMap("priceFrom").hasValue()) {
                    priceFrom = request.queryMap("priceFrom").integerValue();
                }
                if (priceFrom > priceTo) {
                    break;
                }
                price = priceFrom + RANDOM.nextInt((priceTo - priceFrom + 1));
                String desc = "м. " + SUBWAY_NAMES[RANDOM.nextInt(SUBWAY_NAMES.length)] + ", " +
                        STREET_NAMES[RANDOM.nextInt(STREET_NAMES.length)] + " " + (RANDOM.nextInt(100) + 1);
                String url = "http://friendrent.ru/offer/" + (450000 + RANDOM.nextInt(1000));
                cal.add(Calendar.HOUR,  -1);
                Advertise ad = new Advertise(city, type, price, desc, cal.getTime(), url);
                advertises.add(ad);
            }
            ads.put("advertises", advertises);

            ads.put("advertisesLeft", numberOfAdvertises - from - number);
            return ads;
        }, new Gson()::toJson);
    }

    private static Map<String, Integer> getNumberOfAdsMap() {
        Map<String, Integer> citiesList = new HashMap<String, Integer>();
        citiesList.put("Москва", 25);
        citiesList.put("Уфа", 0);
        citiesList.put("Казань", 30);
        citiesList.put("Самара", 8);
        return citiesList;
    }
}

