import java.util.Date;

/**
 * Created by 1 on 25.07.15.
 */
class Advertise {
    public enum Type {
        FLAT,
        ROOM
    }
    String city;
    Type type;
    int price;
    String desc;
    long time; // milliseconds
    String url;

    public Advertise(String city, Type type, int price, String desc, Date dateTime, String url) {
        this.city = city;
        this.type = type;
        this.price = price;
        this.desc = desc;
        this.time = dateTime.getTime();
        this.url = url;
    }
}
