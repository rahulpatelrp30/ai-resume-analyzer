# Resume Analyzer - Java Spring Boot Backend

AI-powered resume analysis system built with Java 17 and Spring Boot 3.2.0. Analyzes resumes against job descriptions using OpenAI GPT-4 with few-shot learning.

## Features

- 📄 **PDF Resume Parsing** - Extract text from PDF resumes using Apache PDFBox
- 🔍 **Skills Extraction** - Identify 50+ technical skills from resumes and job descriptions
- 📊 **ATS Scoring** - Calculate Applicant Tracking System compatibility scores
- 🤖 **LLM Analysis** - GPT-4 powered insights with few-shot learning
- 🎯 **Match Scoring** - Keyword and semantic similarity analysis
- 🔗 **REST API** - Easy integration with any frontend

## Prerequisites

- **Java 17+** (required)
- **OpenAI API Key** (required for AI features)

## Setup

1. **Set OpenAI API Key**

   Windows (PowerShell):
   ```powershell
   $env:OPENAI_API_KEY = "sk-your-key-here"
   ```

   Or create a `.env` file:
   ```
   OPENAI_API_KEY=sk-your-key-here
   ```

2. **Build the Project**

   ```powershell
   .\gradlew.bat build
   ```

3. **Run the Application**

   ```powershell
   .\gradlew.bat bootRun
   ```

   The API will start on `http://localhost:8001`

## API Endpoints

### Health Check
```
GET /health
```

Response:
```json
{
  "status": "healthy",
  "service": "Resume Analyzer API (Java Spring Boot)",
  "openaiConfigured": true
}
```

### Analyze Resume
```
POST /analyze
Content-Type: multipart/form-data
```

Parameters:
- `resume` (file): PDF file (max 10MB)
- `job_description` (text): Job description text

Response:
```json
{
  "matchScore": 75.5,
  "keywordScore": 68.0,
  "semanticScore": 83.0,
  "matchedSkills": ["java", "spring boot", "docker"],
  "missingSkills": ["kubernetes", "aws"],
  "totalResumeSkills": 15,
  "totalJobSkills": 12,
  "atsScore": {
    "overall_score": 78.5,
    "format_score": 85.0,
    "keyword_density": 68.0,
    "section_completeness": 90.0
  },
  "llmAnalysis": {
    "overallAssessment": "Strong candidate...",
    "matchScoreReasoning": "...",
    "strengths": ["..."],
    "criticalImprovements": ["..."],
    "atsOptimization": {...},
    "skillDevelopmentPriority": ["..."],
    "immediateActions": ["..."]
  },
  "resumeFilename": "resume.pdf",
  "improvementAdvice": "...",
  "scoreReasoning": "..."
}
```

## Architecture

```
backend-java/
├── src/main/java/com/resume/analyzer/
│   ├── ResumeAnalyzerApplication.java   # Spring Boot main class
│   ├── config/
│   │   ├── OpenAIConfig.java            # OpenAI service configuration
│   │   └── WebConfig.java               # CORS configuration
│   ├── controller/
│   │   └── AnalyzeController.java       # REST API endpoints
│   ├── service/
│   │   ├── ATSService.java              # ATS scoring logic
│   │   ├── LLMAnalyzerService.java      # GPT-4 few-shot learning
│   │   ├── OpenAIService.java           # OpenAI API wrapper
│   │   ├── PDFService.java              # PDF text extraction
│   │   └── SkillsService.java           # Skills matching
│   ├── model/
│   │   ├── AnalysisResponse.java        # Main API response DTO
│   │   ├── LLMAnalysis.java             # LLM analysis DTO
│   │   ├── HealthResponse.java          # Health check DTO
│   │   └── ErrorResponse.java           # Error response DTO
│   └── exception/
│       ├── FileValidationException.java
│       ├── PDFExtractionException.java
│       └── GlobalExceptionHandler.java  # Centralized error handling
└── src/main/resources/
    └── application.properties            # Spring Boot configuration
```

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8001

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# OpenAI
openai.model=gpt-4o-mini
openai.embedding.model=text-embedding-3-small
openai.max.tokens=1000
openai.temperature=0.7

# CORS
cors.allowed.origins=http://localhost:3000,http://localhost:5173,file://
```

## Development

### Build
```powershell
.\gradlew.bat build
```

### Run Tests
```powershell
.\gradlew.bat test
```

### Clean Build
```powershell
.\gradlew.bat clean build
```

### Check Dependencies
```powershell
.\gradlew.bat dependencies
```

## Technology Stack

- **Spring Boot 3.2.0** - Web framework
- **Java 17** - Programming language
- **Gradle 8.5** - Build tool
- **Apache PDFBox 3.0.1** - PDF parsing
- **OpenAI Java Client 0.18.2** - GPT-4 integration
- **Lombok** - Reduce boilerplate code
- **SLF4J + Logback** - Logging

## Troubleshooting

### OpenAI API Key Not Found
Ensure `OPENAI_API_KEY` environment variable is set. The app will run but AI features will be disabled.

### Build Fails
- Ensure Java 17+ is installed: `java -version`
- Try clean build: `.\gradlew.bat clean build`

### Port Already in Use
Change port in `application.properties`:
```properties
server.port=8002
```

### PDF Extraction Fails
- Ensure PDF is not encrypted
- Check PDF is valid format
- Maximum file size is 10MB

## Frontend Integration

The backend is compatible with the existing `app-improved.html` frontend. Just ensure:
- Backend is running on port 8001
- CORS is configured to allow your frontend origin
- Frontend sends `resume` (not `resume_file`) parameter

## License

MIT License - See LICENSE file for details

## Author

Built with ☕ and Spring Boot
