package Model;

import java.security.Timestamp;
import java.util.Date;

/**
 *
 * @author Fan
 */
public abstract class Post {
    private String postTitle;
    private String postDescription;
    private String eventCategory;
    private Timestamp beginTime;
    private Timestamp endTime;
    private String location;
    private String popularity;
    private String picture;
    private Timestamp postDate;
    private boolean available;
    private int id;

    public Post(int id, String postTitle, String postDescription, String eventCategory,
                String location, String popularity, String picture, Timestamp beginTime,
                Timestamp endTime, Timestamp postDate, boolean available) {
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

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
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

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getAvailable() { return available; }

    public int getId() {
        return id;
    }
}