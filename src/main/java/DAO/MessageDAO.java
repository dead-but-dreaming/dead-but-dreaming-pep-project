package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

public class MessageDAO {

    // ### Message
    // ```
    // message_id integer primary key auto_increment,
    // posted_by integer,
    // message_text varchar(255),
    // time_posted_epoch long,
    // foreign key (posted_by) references Account(account_id)
    // ```

    public Message postNewMessage(Message newMessage){
        // System.out.println("//////////////// postNewMessage DAO");

        Connection conn = ConnectionUtil.getConnection();

        String body = newMessage.getMessage_text();
        long time = newMessage.getTime_posted_epoch();
        int user = newMessage.getPosted_by();

        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, user);
            preparedStatement.setString(2, body);
            preparedStatement.setLong(3, time);

            preparedStatement.executeUpdate();
            
            ResultSet results = preparedStatement.getGeneratedKeys();
            // System.out.println(results);

            if(results.next()){
                // get ID of new row, return message with new ID
                int id = (int) results.getInt(1);
                newMessage.setMessage_id(id);
                
                return newMessage;
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

}
