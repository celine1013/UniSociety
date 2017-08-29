package Model;

/**
 *
 * @author Fan
 */
public abstract class Society {
    private String societyName;
    private String societyDescription;
    private String societyCategory;
    private String emailAddress;
    private String contactPerson;
    private String contactNumber;
    private String logo;
    private String facebook;
    private String verificationCode;
    private boolean available;
    private int id;

    public Society(int id, String societyName, String societyDescription, String societyCategory,
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

    public String getSocietyCategory() {
        return societyCategory;
    }

    public void setSocietyCategory(String societyCategory) {
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
}

