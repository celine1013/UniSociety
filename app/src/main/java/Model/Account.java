package Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 *
 * @author Fan
 */
public class Account  implements Parcelable {
    public static final String ACCOUNT = "Account";
    public static final String ACCOUNT_ACCOUNT_NAME = "accountName";
    public static final String ACCOUNT_PASSWORD = "password";
    public static final String ACCOUNT_SECURITY_QUESTION = "securityQuestion";
    public static final String ACCOUNT_TYPE = "id";

    private String accountName;
    private String password;
    private String securityQuestion;
    private int id;

    public Account(){}

    public Account(int id,String accountName, String password,
                   String securityQuestion) {
        this.accountName = accountName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.id = id;
    }

    protected Account(Parcel in) {
        accountName = in.readString();
        password = in.readString();
        securityQuestion = in.readString();
        id = in.readInt();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accountName);
        parcel.writeString(password);
        parcel.writeString(securityQuestion);
        parcel.writeInt(id);
    }
}
