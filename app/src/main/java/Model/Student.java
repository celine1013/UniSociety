package Model;

import android.os.Parcelable;

/**
 *
 * @author Fan
 */
public abstract class Student implements Parcelable{
    private String studentName;
    private String emailAddress;
    private String facebook;
    private String label;
    private boolean available;
    private int id;

    public Student(int id, String studentName, String emailAddress, String facebook,
                   String label, boolean available) {
        this.studentName = studentName;
        this.emailAddress = emailAddress;
        this.facebook = facebook;
        this.label = label;
        this.available = available;
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getAvailable() { return available; }

    public int getId() {
        return id;
    }
}

