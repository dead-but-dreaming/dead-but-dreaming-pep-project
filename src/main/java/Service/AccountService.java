package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    // constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // constructor with existing DAO
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // register new user
    public Account registerNewUser(Account account){
        // System.out.println("/////////////////////// registerNewUser Service");
        Account newAccount = accountDAO.registerNewUser(account);
        return newAccount;
    }

    public Account authenticateUser(Account account){
        Account loggedIn = accountDAO.authenticateUser(account);
        return loggedIn;
    }

}
