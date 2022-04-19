package org.unibl.etf.services.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.advices.ResponseExceptionHandler;
import org.unibl.etf.exceptions.HttpException;
import org.unibl.etf.models.requests.PurchaseRequest;
import org.unibl.etf.services.VirtualBankService;

import java.util.Collections;

@Service
public class VirtualBankServiceImpl implements VirtualBankService {

    private final RestTemplate restTemplate;

    public VirtualBankServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        restTemplate.setErrorHandler(new ResponseExceptionHandler());
    }

    @Override
    public void requestPurchase(PurchaseRequest purchaseRequest) {
        String url = "http://localhost:8080/api/bank/accounts/payment";

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PurchaseRequest> request = new HttpEntity<PurchaseRequest>(purchaseRequest, headers);

        ResponseEntity response = this.restTemplate.postForEntity(url, request, ResponseEntity.class);//restTemplate.exchange(url, HttpMethod.POST, request, ResponseEntity.class);

        if(response.getStatusCode() != HttpStatus.OK) {
            throw new HttpException(response.getStatusCode(), response.getBody());
        }
    }

}
