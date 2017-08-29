package Model;


/**
 *
 * @author Fan
 */
public class Account {
    private String accountName;
    private String password;
    private String securityQuestion;
    private int id;

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

    public int getId() {
        return id;
    }
}
