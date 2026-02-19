package bank.authentication_service.exception.client;

public class ClientNotCreatedException extends ClientException {

    public ClientNotCreatedException(String message) {
        super(message, "CLIENT_NOT_CREATED");
    }
}
