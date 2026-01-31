package bank.client_person.handler.client;

public abstract class ClientException extends RuntimeException{

    private final String errorCode;

    protected ClientException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }
}
