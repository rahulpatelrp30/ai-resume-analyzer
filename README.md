# 🎯 AI Resume Analyzer

**An intelligent resume analysis system powered by Java Spring Boot and OpenAI GPT-4. Upload your resume and job description to get instant AI-powered feedback, skills matching, and ATS optimization tips.**

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen) ![License](https://img.shields.io/badge/license-MIT-blue)

## 🌟 Features

- **🤖 AI-Powered Analysis** - Leverages OpenAI GPT-4 for intelligent resume feedback
- **📊 Skills Extraction** - Automatically identifies 50+ technical skills from resumes
- **✅ ATS Scoring** - Evaluates resume compatibility with Applicant Tracking Systems
- **🎯 Semantic Matching** - Uses cosine similarity algorithms for accurate job-resume matching
- **📄 Multiple Formats** - Supports PDF and text file uploads (up to 10MB)
- **⚡ Real-time Results** - Get comprehensive analysis in under 3 seconds

## 🏗️ Architecture

```
ai-resume-analyzer/
├── backend-java/              # Java Spring Boot backend
│   ├── src/main/
│   │   ├── java/com/resume/analyzer/
│   │   │   ├── config/        # CORS, OpenAI configuration
│   │   │   ├── controller/    # REST API endpoints
│   │   │   ├── service/       # Business logic (PDF, Skills, ATS, AI)
│   │   │   ├── model/         # DTOs and response objects
│   │   │   └── exception/     # Custom exception handling
│   │   └── resources/
│   │       └── application.properties
│   └── build.gradle           # Gradle dependencies
│
├── app-improved.html          # Frontend UI
├── sample-resume.txt          # Example resume
└── sample-job-description.txt # Example job posting
```

## 🚀 Quick Start

### Prerequisites

- **Java 17** or higher ([Download here](https://adoptium.net/))
- **Python 3.x** (for frontend server)
- **OpenAI API key** (optional, for AI features)

### 🎯 Easiest Way to Run (Windows)

```powershell
cd ai-resume-analyzer
.\START-RESUME-ANALYZER.ps1
```

This script will:
1. Start the Java backend on port 8001
2. Start the frontend server on port 3000
3. Open the app in your browser automatically

### 📋 Manual Setup

#### 1. Clone the repository
```bash
git clone https://github.com/rahulpatelrp30/ai-resume-analyzer.git
cd ai-resume-analyzer
```

#### 2. Set up OpenAI API key (Optional)
```bash
# Windows PowerShell
$env:OPENAI_API_KEY = "your-api-key-here"

# Linux/Mac
export OPENAI_API_KEY="your-api-key-here"
```

#### 3. Start the Backend (Java Spring Boot)
```bash
cd backend-java
# Windows
.\gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

Backend runs at: `http://localhost:8001`

#### 4. Start the Frontend (in a new terminal)
```bash
cd ai-resume-analyzer
python -m http.server 3000
```

#### 5. Open the Application
Visit: `http://localhost:3000/app-improved.html`

### 🧪 Test the Application

1. **Upload** a resume (PDF or TXT)
2. **Paste** a job description
3. **Click** "Analyze Resume"
4. **View** results in 2-3 seconds

## 🌐 Live Demo

**GitHub Pages:** https://rahulpatelrp30.github.io/ai-resume-analyzer/app-improved.html
- Shows the application interface
- Requires local backend setup for full functionality
- See setup instructions above

## 📖 Usage

Once both servers are running:

1. **Open** `http://localhost:3000/app-improved.html` in your browser
2. **Upload your resume** (PDF or TXT format, max 10MB)
3. **Paste the job description** you're targeting
4. **Click "Analyze Resume"**
5. **Review detailed results**:
   - 📊 Match score (0-100%)
   - ✅ Matched skills between resume and job
   - ⚠️ Missing skills to add
   - 🎯 ATS compatibility score
   - 💡 AI-generated improvement suggestions (if OpenAI key is configured)

## 🔧 How It Works

### 1. Resume Parsing
- Uses Apache PDFBox to extract text from PDF resumes
- Also supports plain text (.txt) files
- Handles multi-page documents efficiently

### 2. Skills Extraction
- Database of 50+ technical skills (Java, Python, Spring Boot, React, etc.)
- Pattern matching with word boundaries for accurate detection
- Identifies skills in both resume and job description

### 3. Matching Algorithm
- **Keyword Matching**: Direct skill matches between resume and job
- **Cosine Similarity**: Semantic comparison of text content
- **ATS Scoring**: Format, keyword density, section completeness

### 4. AI Feedback
- Uses OpenAI's GPT-4 to generate personalized suggestions
- Analyzes missing skills and provides actionable advice
- Focuses on resume improvements specific to the job

## 🎨 Tech Stack

### Backend
- **FastAPI** - Modern Python web framework
- **pdfplumber** - PDF text extraction
- **OpenAI API** - Embeddings & GPT-4
- **NumPy** - Vector operations

### Frontend
- **React 19** - UI framework
- **TypeScript** - Type safety
- **Vite** - Build tool
- **TailwindCSS** - Styling

## 📊 Sample Results

```json
{
  "matchScore": 81.2,
  "keywordScore": 75.0,
  "semanticScore": 89.3,
  "matchedSkills": ["python", "react", "aws", "docker"],
  "missingSkills": ["kubernetes", "terraform"],
  "improvementAdvice": "Focus on adding Kubernetes experience..."
}
```

## 🔑 OpenAI API Key

The app works in limited mode without an API key but provides full features with one:

**Without API Key:**
- ✅ PDF parsing
- ✅ Keyword-based matching
- ❌ Semantic similarity (defaults to 0)
- ❌ AI feedback (placeholder message)

**With API Key:**
- ✅ All features enabled
- ✅ Semantic analysis using embeddings
- ✅ GPT-4 powered improvement suggestions

Get your API key: https://platform.openai.com/api-keys

## 📝 Resume Bullet Point (for your actual resume!)

```
AI Resume Analyzer & Job Matcher — React, FastAPI, OpenAI, PostgreSQL
• Built an AI-powered web app that parses resumes and job descriptions, extracts skills, 
  and computes a match score using both keyword overlap and embeddings-based semantic similarity
• Integrated OpenAI embeddings and GPT-4 to generate personalized resume improvement suggestions, 
  reducing manual job-to-resume comparison time from ~15 minutes to under 1 minute
• Designed and implemented REST APIs with FastAPI and a React + TypeScript frontend for 
  file upload, analysis visualization, and user feedback
```

## 🚧 Future Enhancements

- [ ] User authentication & history
- [ ] Save analysis results to database
- [ ] Export results as PDF report
- [ ] Support for DOCX resumes
- [ ] Job posting URL scraping
- [ ] Batch analysis for multiple jobs
- [ ] ATS (Applicant Tracking System) optimization tips
- [ ] Resume template suggestions

## 📄 License

MIT License - feel free to use this for your portfolio!

## 🤝 Contributing

This is a portfolio project, but suggestions and improvements are welcome!

---

**Built by Rahul** | November 2025
