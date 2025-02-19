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

}
