package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }    

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", ctx -> {});
        app.delete("/messages/{message_id}", ctx -> {});
        app.patch("/messages/{message_id}", ctx -> {});
        app.get("/accounts/{account_id}/messages", ctx -> {});

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    private void registerHandler(Context context){
        // System.out.println("///////////////// registerHandler Controller");
        Account user = context.bodyAsClass(Account.class);

        System.out.println(user);
        
        if (user.getUsername() == ""){
            // blank username
            // System.out.println("blank username");
            context.status(400);
        } else if (user.getPassword().length() < 4){
            // password too short
            // System.out.println("password too short");
            context.status(400);
        } else {
            // System.out.println("attempting to register new user");
            user = accountService.registerNewUser(user);
            if (user != null){
                context.json(user);
                context.status(200);

            } else {
                // context.json(user);
                context.status(400);
            }
        }
        // System.out.println("reached end of handler");
    }

    private void loginHandler(Context context){
        // System.out.println("///////////////// loginHandler Controller");

        Account user = context.bodyAsClass(Account.class);
        
        // System.out.println("attempting to authenticate user");
        user = accountService.authenticateUser(user);

        if (user != null){
            context.json(user);
            context.status(200);
        } else {
            context.status(401);
        }
    }

    private void postMessageHandler(Context context){
        // System.out.println("///////////////// postMessageHandler Controller");

        Message message = context.bodyAsClass(Message.class);
        
        String text = message.getMessage_text();

        if (!accountService.checkUserExists(message.getPosted_by())){
            // invalid user id
            context.status(400);
        } else if (text.length() > 255){
            // message too long
            context.status(400);
        } else if (text == ""){
            // message empty
            context.status(400);
        } else {
            // System.out.println("attempting to post message");
            message = messageService.postNewMessage(message);
    
            if (message != null){
                context.json(message);
                context.status(200);
            } else {
                context.status(400);
            }
        }

    }

    private void getMessageHandler(Context context){
        List<Message> allMessages = messageService.getAllMessages();

        if (allMessages != null){
            context.json(allMessages);
            context.status(200);
        } else {
            context.status(400);
        }

    }

}