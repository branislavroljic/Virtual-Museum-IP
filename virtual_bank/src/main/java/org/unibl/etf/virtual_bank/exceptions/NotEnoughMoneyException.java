package org.unibl.etf.virtual_bank.exceptions;

import org.springframework.http.HttpStatus;

public class NotEnoughMoneyException extends  HttpException{

    public NotEnoughMoneyException(){
        super(HttpStatus.CONFLICT, null);
    }

    public NotEnoughMoneyException(Object data){
        super(HttpStatus.CONFLICT, data);
    }

}