package com.ercanbeyen.servicecommon.client.config;

import com.ercanbeyen.servicecommon.client.exception.BadRequestException;
import com.ercanbeyen.servicecommon.client.exception.ErrorResponse;
import com.ercanbeyen.servicecommon.client.exception.InternalServerErrorException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {
    private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        Response.Body responseBody = response.body();
        String message;

        try (response) {
            String json = IOUtils.toString(responseBody.asInputStream(), Charsets.UTF_8);
            ErrorResponse errorResponse = new ObjectMapper().readValue(json, ErrorResponse.class);
            message = errorResponse.message();
            log.info("Exception is successfully processed");
        } catch (JsonProcessingException exception) {
            log.error("ObjectMapper's readValue method throw an exception");
            message = exception.getMessage();
        } catch (IOException exception) {
            log.error("Response.Body's asInputStream method throw an exception");
            message = exception.getMessage();
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(message);
            case 404 -> new ResourceNotFoundException(message);
            case 500 -> new InternalServerErrorException(message);
            default -> new Exception(message);
        };
    }
}
