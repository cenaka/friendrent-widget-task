/**
 * Created by 1 on 23.07.15.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static spark.Spark.*;

public class AdvertiseWebService {

    public static final Random RANDOM = new Random();
    public static final String[] SUBWAY_NAMES = new String[]{"Академическая", "Владимирская", "Автово", "Спортивная", "Чкаловская"};
    public static final String[] STREET_NAMES = new String[]{"пр-т Науки", "ул. Гидротехников", "ул. Свободы", "Владимирский пр-т", "ул. Достоевского"};
    public static final int MAX_VALUE = 9999999;

    public static void main(String[] args) {
        staticFileLocation("/static");
        get("/advertises", (request, response) -> {
            Map<String, Object> ads = new HashMap<>();
            ArrayList<Advertise> advertises = new ArrayList<>();
            String city = request.queryMap("city").value();
            Advertise.Type type;
            Integer price;
            Map<String, Integer> citiesList = getNumberOfAdsMap();

            int number = getIntParameter(request, "number", 10);
            int from = getIntParameter(request, "from", 0);

            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR, -from);

            int numberOfAdvertises = 1000;

            if (request.queryMap("city").hasValue()) {
                String cityValue = request.queryMap("city").value();
                if (citiesList.containsKey(cityValue))
                    numberOfAdvertises = citiesList.get(cityValue);
            }
            if (numberOfAdvertises - from < number) {
                number = numberOfAdvertises - from;
            }
            for (int i = 0; i < number; i++) {
                type = getType(request);

                price = getPrice(request);
                if (price == null) {
                    break;
                }

                String desc = getDescription();
                String url = getUrl();
                cal.add(Calendar.HOUR, -1);
                Advertise ad = new Advertise(city, type, price, desc, cal.getTime(), url);
                advertises.add(ad);
            }
            ads.put("advertises", advertises);
            ads.put("advertisesLeft", numberOfAdvertises - from - number);
            return ads;
        }, new Gson()::toJson);
    }

    private static Integer getPrice(Request request) {
        int price;
        int priceFrom = getIntParameter(request, "priceFrom", 1);
        int priceTo = getIntParameter(request, "priceTo", MAX_VALUE);
        if (priceFrom > priceTo) {
            return null;
        }
        if (priceTo >= MAX_VALUE) {
            priceTo = Math.max(50000, priceFrom + 20000);
        }
        price = priceFrom + RANDOM.nextInt((priceTo - priceFrom + 1));
        return price;
    }

    private static String getUrl() {
        return "http://friendrent.ru/offer/" + (450000 + RANDOM.nextInt(1000));
    }

    private static Advertise.Type getType(Request request) {
        Advertise.Type type;
        if (request.queryMap("type").hasValue()) {
            type = Advertise.Type.valueOf(request.queryMap("type").value().toUpperCase());
        } else {
            type = Advertise.Type.values()[RANDOM.nextInt(2)];
        }
        return type;
    }

    private static String getDescription() {
        return "м. " + SUBWAY_NAMES[RANDOM.nextInt(SUBWAY_NAMES.length)] + ", " +
                            STREET_NAMES[RANDOM.nextInt(STREET_NAMES.length)] + " " + (RANDOM.nextInt(100) + 1);
    }

    private static int getIntParameter(Request request, String key, int defaultValue) {
        int number = defaultValue;
        if (request.queryMap(key).hasValue()) {
            number = request.queryMap(key).integerValue();
        }
        return number;
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

