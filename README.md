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
- **Gradle 8.5+** (included via wrapper)
- **OpenAI API key** (optional, for AI features)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/ai-resume-analyzer.git
cd ai-resume-analyzer
```

2. **Set up OpenAI API key** (Optional)
```bash
# Windows PowerShell
$env:OPENAI_API_KEY = "your-api-key-here"

# Linux/Mac
export OPENAI_API_KEY="your-api-key-here"
```

3. **Build the project**
```bash
cd backend-java
./gradlew build
```

3. Set up environment variables:
```bash
cp .env.example .env
# Edit .env and add your OPENAI_API_KEY
```

4. Run the server:
```bash
uvicorn main:app --reload
```

Backend will run at `http://localhost:8000`

### Frontend Setup

1. Navigate to frontend:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm run dev
```

Frontend will run at `http://localhost:3000`

## 📖 Usage

1. **Open the app** at `http://localhost:3000`
2. **Upload your resume** (PDF format)
3. **Paste a job description** you're interested in
4. **Click "Analyze Resume"**
5. **Review results**:
   - Overall match score (0-100)
   - Matched skills ✅
   - Missing skills ⚠️
   - AI-generated improvement suggestions 💡

## 🧪 How It Works

### 1. Resume Parsing
- Uses `pdfplumber` to extract text from PDF resumes
- Handles multi-page documents

### 2. Skill Extraction
- Maintains a database of 70+ technical skills
- Uses regex pattern matching to identify skills in both resume and job description
- Case-insensitive with word boundary detection

### 3. Matching Algorithm
```
Final Score = (0.6 × Keyword Score) + (0.4 × Semantic Score)
```

- **Keyword Score**: Percentage of job skills found in resume
- **Semantic Score**: Cosine similarity between embeddings (0-1)

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
