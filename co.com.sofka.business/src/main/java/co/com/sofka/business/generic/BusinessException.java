package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.Command;

public class BusinessException extends RuntimeException {

    private final Command command;

    public BusinessException(String message, Throwable throwable, Command command){
        super(message, throwable);
        this.command = command;
    }

    public BusinessException(String message, Throwable throwable){
        this(message, throwable, null);
    }

    public BusinessException(String message){
        this(message, null, null);
    }

    public Command getCommand() {
        return command;
    }


}
