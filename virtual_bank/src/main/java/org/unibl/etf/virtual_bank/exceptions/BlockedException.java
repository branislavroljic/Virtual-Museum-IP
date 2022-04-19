package org.unibl.etf.virtual_bank.exceptions;

import org.springframework.http.HttpStatus;

public class BlockedException extends HttpException{

    public BlockedException(){
        super(HttpStatus.FORBIDDEN, null);
    }

    public BlockedException(Object data){
        super(HttpStatus.FORBIDDEN, data);
    }
}
