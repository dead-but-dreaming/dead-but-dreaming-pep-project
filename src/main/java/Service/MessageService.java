package Service;

import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;

    // constructor
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    // constructor with existing DAO
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    
    public Message postNewMessage(Message message){
        return messageDAO.postNewMessage(message);
    }

}
