package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

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

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id){
        return messageDAO.deleteMessageByID(id);
    }


    public List<Message> getMessagesByUserID(int id){
        return messageDAO.getMessagesByUserID(id);
    }

}
