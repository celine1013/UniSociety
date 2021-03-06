package Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Fan
 */
public class Eventlist implements Parcelable {
    public static final String EVENTLIST = "Eventlist";
    public static final String POST_KEY = "key";
    public static final String ACCOUNT_ACCOUNT_NAME = "accountName";
    public static final String QUERY = "query";

    private String accountName;
    private String key;
    private String query;

    public Eventlist() {
    }

    public Eventlist(String key, String accountName) {
        this.accountName = accountName;
        this.key = key;
        this.query = toString(key,accountName);
    }

    protected Eventlist(Parcel in) {
        accountName = in.readString();
        key = in.readString();
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String toString(String postKey, String userName){
        return postKey + "_" + userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accountName);
        parcel.writeString(key);
        parcel.writeString(query);
    }
}
