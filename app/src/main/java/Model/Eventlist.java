package Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Fan
 */
public class Eventlist implements Parcelable {
    public static final String EVENTLIST = "Eventlist";
    public static final String POST_KEY = "postKey";
    public static final String ACCOUNT_ACCOUNT_NAME = "accountName";
    public static final String QUERY = "query";

    private String accountName;
    private String postKey;
    private String query;

    public Eventlist() {
    }

    public Eventlist(String key, String accountName) {
        this.accountName = accountName;
        this.postKey = key;
        this.query = key+ "_" +accountName;
    }

    public Eventlist(String accountName, String postKey, String query) {
        this.accountName = accountName;
        this.postKey = postKey;
        this.query = query;
    }

    protected Eventlist(Parcel in) {
        accountName = in.readString();
        postKey = in.readString();
        query = in.readString();
    }


    public static final Creator<Eventlist> CREATOR = new Creator<Eventlist>() {
        @Override
        public Eventlist createFromParcel(Parcel in) {
            return new Eventlist(in);
        }

        @Override
        public Eventlist[] newArray(int size) {
            return new Eventlist[size];
        }
    };

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setKey(String key) {
        this.postKey = key;
    }

    public static String toString(String postKey, String userName){
        return postKey + "_" + userName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accountName);
        parcel.writeString(postKey);
        parcel.writeString(query);
    }
}
