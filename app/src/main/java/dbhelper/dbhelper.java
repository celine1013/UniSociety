package dbhelper;

import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Post;
import Model.Society;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Fan
 */
// TODO: add pictures to the database

public class dbhelper {
    /*Register and Log-In*/
    
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
    public boolean verifySocIdentity(int societyID, String verificationCode) {
        boolean result = true;
        return result;
    }


    //register the new user into the database
    public void registerNewSocUser(int type, Account account) {

    }

    public void registerNormalUser(int id, Account account) {
    }

    //pre-load society data into database
    public void registerSocEntity(Society society){
    }

    //return current user/null if failed
    //might have issue
    // ==>if so, add a new function which would return a boolean result of login;
    //      then add "getAccountByID
    public Account logIn (String account, String password){
        Account currentAccount = new Account();
        return currentAccount;
    }


    public Society getSocietyByID(int societyID) {
        Society society = new Society();
        return society;
    }


    /*Post CRUD*/

    public void createPost(int societyID, Post newPost) {
    }

    //TODO: how to update a specific post???? use ID?
    public void updatePost() {
    }

    public List<Post> getAllPosts(int societyID) {
        List<Post> posts = new ArrayList<>();
        return posts;
    }

    // TODO:  Find the format of the date
    public List<Post> getPostsByDate() {
        List<Post> posts = new ArrayList<>();
        return posts;
    }

    public List<Post> getPostsByCategory(String category) {
        List<Post> posts = new ArrayList<>();
        return posts;
    }
}
