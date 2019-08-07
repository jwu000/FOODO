package util;

public class ReviewAdapterItem {

    int rating;
    String username;
    String reviewText;
    String timeCreated;


    public ReviewAdapterItem(int rating, String username, String reviewText, String timeCreated) {
        this.rating = rating;
        this.username = username;
        this.reviewText = reviewText;
        this.timeCreated = timeCreated;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
}
