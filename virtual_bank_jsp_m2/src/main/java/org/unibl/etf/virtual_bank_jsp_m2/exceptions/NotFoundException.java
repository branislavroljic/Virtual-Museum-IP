package org.unibl.etf.virtual_bank_jsp_m2.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String data){
        super(data);
    }

    public NotFoundException(){}
}
