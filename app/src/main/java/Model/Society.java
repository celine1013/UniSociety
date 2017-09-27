package Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Fan
 */
public class Society implements Parcelable{
    public static final String SOCIETY = "Society";
    public static final String SOCIETY_NAME = "societyName";
    public static final String SOCIETY_DESCRIPTION = "societyDescription";
    public static final String SOCIETY_CATEGORY = "societyCategory";
    public static final String SOCIETY_EMAIL = "emailAddress";
    public static final String SOCIETY_CONTACT_PERSON = "contactPerson";
    public static final String SOCIETY_CONTACT_NUMBER = "contactNumber";
    public static final String SOCIETY_LOGO = "logo";
    public static final String SOCIETY_FACEBOOK = "facebook";
    public static final String SOCIETY_VERIFICATION_CODE = "verificationCode";
    public static final String SOCIETY_AVAILABLE = "available";
    public static final String SOCIETY_ID = "id";
    private String societyName;
    private String societyDescription;
    private int societyCategory;
    private String emailAddress;
    private String contactPerson;
    private String contactNumber;
    private String logo;
    private String facebook;
    private String verificationCode;
    private boolean available;
    private int id;

    public Society() {
    }


    public Society(int id, String societyName, String societyDescription, int societyCategory,
                   String emailAddress, String contactPerson, String contactNumber, String facebook,
                   String logo, boolean available, String verificationCode) {
        this.societyName = societyName;
        this.societyDescription = societyDescription;
        this.societyCategory = societyCategory;
        this.emailAddress = emailAddress;
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.facebook = facebook;
        this.logo = logo;
        this.available = available;
        this.verificationCode = verificationCode;
        this.id = id;
    }

    protected Society(Parcel in) {
        societyName = in.readString();
        societyDescription = in.readString();
        societyCategory = in.readInt();
        emailAddress = in.readString();
        contactPerson = in.readString();
        contactNumber = in.readString();
        logo = in.readString();
        facebook = in.readString();
        verificationCode = in.readString();
        available = in.readByte() != 0;
        id = in.readInt();
    }

    public static final Creator<Society> CREATOR = new Creator<Society>() {
        @Override
        public Society createFromParcel(Parcel in) {
            return new Society(in);
        }

        @Override
        public Society[] newArray(int size) {
            return new Society[size];
        }
    };

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getSocietyDescription() {
        return societyDescription;
    }

    public void setSocietyDescription(String societyDescription) {
        this.societyDescription = societyDescription;
    }

    public int getSocietyCategory() {
        return societyCategory;
    }

    public void setSocietyCategory(int societyCategory) {
        this.societyCategory = societyCategory;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLogo() {
        return logo;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getAvailable() { return available; }

    public String getVerifationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
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
        parcel.writeString(societyName);
        parcel.writeString(societyDescription);
        parcel.writeInt(societyCategory);
        parcel.writeString(emailAddress);
        parcel.writeString(contactPerson);
        parcel.writeString(contactNumber);
        parcel.writeString(logo);
        parcel.writeString(facebook);
        parcel.writeString(verificationCode);
        parcel.writeByte((byte) (available ? 1 : 0));
        parcel.writeInt(id);
    }
}

