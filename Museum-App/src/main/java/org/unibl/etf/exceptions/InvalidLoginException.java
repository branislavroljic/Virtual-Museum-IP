package org.unibl.etf.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends HttpException{

    public InvalidLoginException(){
        super(HttpStatus.UNAUTHORIZED, null);
    }

    public InvalidLoginException(Object data) {super(HttpStatus.UNAUTHORIZED, data);}
}
