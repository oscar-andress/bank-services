package bank.client_person.handler.client;

public class ClientNotFoundException extends ClientException{
    
    public ClientNotFoundException(String message) {
        super(message, "CLIENT_ALREADY_EXISTS");
    }
}
