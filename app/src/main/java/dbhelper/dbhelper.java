package dbhelper;

import Model.Account;
import Model.Society;
/**
 * Created by Fan
 */

public class dbhelper {
    //Register//

    //check if the account has already exist
//true: exist / false: not exist
    public boolean hasExisted(String accountName){
        boolean result = false;
        /*if (accountName.equals(Account.getAccountName())) {
            return true; }
            else { return false;
        }*/
        return false;
    }

    //check if the society match the verification code
//true: matched / false: mismatched
    public boolean verifySocIdentity(String societyName, String verificationCode){
        return true;
    }

    //register the new society user into the database
    public void registerNewSocUser(String id, Account account){

    }

    //pre-load society data into database
    public void registerSocEntity(Society society){
    }

    // log-in
//return current user/null if failed
    public Account logIn (String account, String password){
        Account currentAccount = new Account();
        return currentAccount;
    }


}
