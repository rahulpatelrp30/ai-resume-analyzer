package com.resume.analyzer.exception;

import com.resume.analyzer.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<ErrorResponse> handleFileValidation(FileValidationException e) {
        logger.error("File validation error: {}", e.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .error("File Validation Error")
            .message(e.getMessage())
            .status(400)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(PDFExtractionException.class)
    public ResponseEntity<ErrorResponse> handlePDFExtraction(PDFExtractionException e) {
        logger.error("PDF extraction error: {}", e.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .error("PDF Extraction Error")
            .message(e.getMessage())
            .status(500)
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSize(MaxUploadSizeExceededException e) {
        logger.error("File size exceeded: {}", e.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .error("File Too Large")
            .message("Maximum file size is 10MB")
            .status(413)
            .build();
        
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(error);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        logger.error("Invalid argument: {}", e.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .error("Invalid Request")
            .message(e.getMessage())
            .status(400)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e) {
        logger.error("Unexpected error", e);
        
        ErrorResponse error = ErrorResponse.builder()
            .error("Internal Server Error")
            .message("An unexpected error occurred: " + e.getMessage())
            .status(500)
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
