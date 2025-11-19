package com.resume.analyzer.exception;

public class PDFExtractionException extends RuntimeException {
    public PDFExtractionException(String message) {
        super(message);
    }
    
    public PDFExtractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
