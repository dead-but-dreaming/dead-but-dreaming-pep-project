package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();

        List<Message> allMessages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM MESSAGE";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()){
                Message msg = new Message(
                    results.getInt("message_id"),
                    results.getInt("posted_by"),
                    results.getString("message_text"),
                    results.getLong("time_posted_epoch")
                );

                allMessages.add(msg);
            }

            return allMessages;

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }


}
