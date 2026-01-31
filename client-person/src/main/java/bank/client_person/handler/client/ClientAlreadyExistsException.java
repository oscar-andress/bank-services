package bank.client_person.handler.client;

public class ClientAlreadyExistsException extends ClientException{

    public ClientAlreadyExistsException(String message) {
        super(message, "CLIENT_ALREADY_EXISTS");

    }
    
}
