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
    public static void main(String[] args) {
        staticFileLocation("/static");
        get("/advertises", (request, response) -> {
            Map<String, Object> ads = new HashMap<>();
            ArrayList<Advertise> advertises = new ArrayList<>();
            String city = request.queryMap("city").value();
            Advertise.Type type;
            int price;
            Random random = new Random();
            String [] subwayName = new String[]{"Академическая", "Владимирская", "Автово", "Спортивная", "Чкаловская"};
            String [] streetName = new String[]{"пр-т Науки", "ул. Гидротехников", "ул. Свободы", "Владимирский пр-т", "ул. Достоевского"};


            int number = 10;
            if (request.queryMap("number").hasValue()) {
                number = request.queryMap("number").integerValue();
            }
            for (int i = 0; i < number; i++) {
                if (request.queryMap("type").hasValue()){
                    type = Advertise.Type.valueOf(request.queryMap("type").value().toUpperCase());
                } else {
                    type = Advertise.Type.values()[random.nextInt(2)];
                }
                int from = 1, to = 50000;
                if (request.queryMap("priceTo").hasValue()) {
                    to = request.queryMap("priceTo").integerValue();
                }
                if (request.queryMap("priceFrom").hasValue()) {
                    from = request.queryMap("priceFrom").integerValue();
                }
                if (from > to) {
                    break;
                }
                price = from + random.nextInt((to - from + 1));
                String desc = "м. " + subwayName[random.nextInt(subwayName.length)] + ", " +
                        streetName[random.nextInt(streetName.length)] + " " + (random.nextInt(100) + 1);
                String url = "http://friendrent.ru/offer/" + (450000 + random.nextInt(1000));
                Advertise ad = new Advertise(city, type, price, desc, new Date(), url);
                advertises.add(ad);
            }
            ads.put("advertises", advertises);
            return ads;
        }, new Gson()::toJson);
    }
}

