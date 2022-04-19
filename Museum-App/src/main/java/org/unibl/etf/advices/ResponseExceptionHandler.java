package org.unibl.etf.advices;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.unibl.etf.exceptions.HttpException;

import java.io.IOException;
import java.nio.charset.Charset;

public class ResponseExceptionHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new HttpException(response.getStatusCode(), StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    }
}
