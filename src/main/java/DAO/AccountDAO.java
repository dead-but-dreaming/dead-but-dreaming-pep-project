package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    // ### Account
    // ```
    // account_id integer primary key auto_increment,
    // username varchar(255) unique,
    // password varchar(255)
    // ```

    // register
    public Account registerNewUser(Account account){
        // System.out.println("//////////////// registerNewUser DAO");
        Connection conn = ConnectionUtil.getConnection();

        String username = account.getUsername();
        String password = account.getPassword();

        try {
            String sql = "INSERT INTO account(username, password) VALUES(?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
            
            ResultSet results = preparedStatement.getGeneratedKeys();

            if(results.next()){
                // get ID of new row, return account with new ID
                int id = (int) results.getInt(1);
                account.setAccount_id(id);

                return account;
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    // login
    public Account authenticateUser(Account loginUser){
        // System.out.println("//////////////// authenticateUser DAO");

        Connection conn = ConnectionUtil.getConnection();

        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet results = preparedStatement.executeQuery();

            while(results.next()){
                Account dbUser = new Account(
                    results.getInt("account_id"),
                    results.getString("username"),
                    results.getString("password"));

                if (password.equals(dbUser.getPassword())){
                    return dbUser;
                }
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
