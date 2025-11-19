package com.resume.analyzer.service;

import com.resume.analyzer.exception.FileValidationException;
import com.resume.analyzer.exception.PDFExtractionException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PDFService {
    
    private static final Logger logger = LoggerFactory.getLogger(PDFService.class);
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String[] ALLOWED_TYPES = {"application/pdf", "text/plain", "application/octet-stream"};
    
    public void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileValidationException("File is empty");
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileValidationException(
                String.format("File size %.1fMB exceeds maximum allowed size of 10MB",
                    file.getSize() / (1024.0 * 1024.0))
            );
        }
        
        String contentType = file.getContentType();
        boolean isAllowed = false;
        for (String type : ALLOWED_TYPES) {
            if (type.equals(contentType)) {
                isAllowed = true;
                break;
            }
        }
        
        if (!isAllowed) {
            throw new FileValidationException(
                "Invalid file type: " + contentType + ". Only PDF and text files are allowed."
            );
        }
        
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new FileValidationException("File must have a name");
        }
        String filenameLower = filename.toLowerCase();
        if (!filenameLower.endsWith(".pdf") && !filenameLower.endsWith(".txt")) {
            throw new FileValidationException("File must have .pdf or .txt extension");
        }
    }
    
    public String extractText(MultipartFile file) {
        String filename = file.getOriginalFilename();
        logger.info("📄 Extracting text from file: {}", filename);
        
        try {
            byte[] bytes = file.getBytes();
            String text;
            
            // Handle text files directly
            if (filename != null && filename.toLowerCase().endsWith(".txt")) {
                text = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
                logger.info("✅ Read text file: {} characters", text.length());
            } else {
                // Handle PDF files
                try (PDDocument document = Loader.loadPDF(bytes)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    text = stripper.getText(document);
                }
                logger.info("✅ Extracted {} characters from PDF", text.length());
            }
            
            if (text == null || text.trim().isEmpty()) {
                throw new PDFExtractionException(
                    "Could not extract text from file. It may be image-based or empty."
                );
            }
            
            return text.trim();
        } catch (IOException e) {
            logger.error("❌ Failed to extract text from file", e);
            throw new PDFExtractionException("Failed to read file", e);
        }
    }
}
