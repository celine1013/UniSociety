package Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.security.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fan
 */
public class Post implements Parcelable{
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
    private Map<String, Boolean> stars = new HashMap<>();

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

    protected Post(Parcel in) {
        postTitle = in.readString();
        postDescription = in.readString();
        eventCategory = in.readInt();
        beginTime = in.readString();
        endTime = in.readString();
        location = in.readString();
        popularity = in.readString();
        picture = in.readString();
        postDate = in.readString();
        available = in.readByte() != 0;
        id = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("postTitle", postTitle);
        result.put("postDescription", postDescription);
        result.put("eventCategory", eventCategory);
        result.put("location", location);
        result.put("popularity", popularity);
        result.put("beginTime", beginTime);
        result.put("endTime", endTime);
        result.put("postDate", postDate);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(postTitle);
        parcel.writeString(postDescription);
        parcel.writeInt(eventCategory);
        parcel.writeString(beginTime);
        parcel.writeString(endTime);
        parcel.writeString(location);
        parcel.writeString(popularity);
        parcel.writeString(picture);
        parcel.writeString(postDate);
        parcel.writeByte((byte) (available ? 1 : 0));
        parcel.writeInt(id);
    }
}