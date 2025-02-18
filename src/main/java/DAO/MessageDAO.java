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

    

}
