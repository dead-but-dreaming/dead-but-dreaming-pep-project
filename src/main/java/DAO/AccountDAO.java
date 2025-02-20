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
    public Account registerNewUser(Account newUser){
        // System.out.println("//////////////// registerNewUser DAO");
        Connection conn = ConnectionUtil.getConnection();

        String username = newUser.getUsername();
        String password = newUser.getPassword();

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
                newUser.setAccount_id(id);

                return newUser;
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

    public boolean checkUserExists(int id){
        Connection conn = ConnectionUtil.getConnection();
        
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet results = preparedStatement.executeQuery();

            // if there are no rows in the result set isBeforeFirst will return false
            // we can use this to verify the account exists in the database
            if (results.isBeforeFirst()){
                return true;
            } else {
                return false;
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }
}
