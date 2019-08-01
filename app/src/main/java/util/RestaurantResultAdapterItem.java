package util;

public class RestaurantResultAdapterItem {

    String restaurantName;
    Double rating;
    String restaurantId;
    String price;
    String address;
    String distance;

    public RestaurantResultAdapterItem(String restaurantName, Double rating, String restaurantId, String price, String address, String distance) {
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.restaurantId = restaurantId;
        this.price = price;
        this.address = address;
        this.distance = distance;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
