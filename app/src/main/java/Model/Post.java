package Model;

import java.security.Timestamp;
import java.util.Date;

/**
 *
 * @author Fan
 */
public class Post {
    private String postTitle;
    private String postDescription;
    private int eventCategory;
    private String beginTime;
    private String endTime;
    private String location;
    private String popularity;
    private String picture;
    private String postDate;
    private boolean available;
    private int id;

    public Post() {
    }

    public Post(int id, String postTitle, String postDescription, int eventCategory,
                String location, String popularity, String picture, String beginTime,
                String endTime, String postDate, boolean available) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.eventCategory = eventCategory;
        this.location = location;
        this.popularity = popularity;
        this.picture = picture;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.postDate = postDate;
        this.available = available;
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public int getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(int eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPicture() {
        return picture;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String date) {
        this.postDate = date;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getAvailable() { return available; }

    public int getId() {
        return id;
    }
}