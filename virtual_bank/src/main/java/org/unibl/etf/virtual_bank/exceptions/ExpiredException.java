package org.unibl.etf.virtual_bank.exceptions;

import org.springframework.http.HttpStatus;

public class ExpiredException extends HttpException{

    public ExpiredException(){
        super(HttpStatus.FORBIDDEN, null);
    }

    public ExpiredException(Object data){
        super(HttpStatus.FORBIDDEN, data);
    }
}
