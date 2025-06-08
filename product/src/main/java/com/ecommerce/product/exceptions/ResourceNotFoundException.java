package com.ecommerce.product.exceptions;

import com.ecommerce.product.controllers.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;
    LocalDateTime errorAt;

    final static DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public ResourceNotFoundException() {
    }

    private static Logger productlogger = LoggerFactory.getLogger(ProductController.class);

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {


        super(String.format("%s not found with %s: %s ", resourceName, field, fieldName));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateTime = ldt.format(formatter);
        String isoFormattedDateTime = ldt.format(ISO_FORMATTER);
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
        productlogger.error("%s not found with %s: %s at %s", resourceName, field, fieldName, formattedDateTime);
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
