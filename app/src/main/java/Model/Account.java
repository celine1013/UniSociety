package Model;


import java.io.Serializable;

/**
 *
 * @author Fan
 */
public class Account  implements Serializable {
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
}
