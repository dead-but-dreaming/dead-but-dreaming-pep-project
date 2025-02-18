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

}
