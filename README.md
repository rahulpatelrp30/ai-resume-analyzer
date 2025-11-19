# 🎯 AI Resume Analyzer

**An intelligent resume analysis system powered by Java Spring Boot and AI. Upload your resume and job description to get instant feedback, skills matching, and ATS optimization tips.**

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen) ![License](https://img.shields.io/badge/license-MIT-blue)

## 🌐 Live Demo

**Try it now:** [https://rahulpatelrp30.github.io/ai-resume-analyzer/](https://rahulpatelrp30.github.io/ai-resume-analyzer/)

## 📸 Screenshots

### Match Score & Overview
![Match Score](https://raw.githubusercontent.com/rahulpatelrp30/ai-resume-analyzer/main/screenshots/match-score.png)
*Get an instant match score showing how well your resume aligns with the job requirements*

### AI-Powered Analysis
![AI Analysis](https://raw.githubusercontent.com/rahulpatelrp30/ai-resume-analyzer/main/screenshots/ai-analysis.png)
*Receive key strengths identified by AI analysis*

### ATS Compatibility Score
![ATS Score](https://raw.githubusercontent.com/rahulpatelrp30/ai-resume-analyzer/main/screenshots/ats-score.png)
*Check your resume's compatibility with Applicant Tracking Systems*

### Skills Analysis
![Skills Analysis](https://raw.githubusercontent.com/rahulpatelrp30/ai-resume-analyzer/main/screenshots/skills-analysis.png)
*See which skills match and which are missing from your resume*

## ✨ What This App Does

This tool helps you **improve your resume** by:

1. 📤 **Upload** your resume (PDF or text file)
2. 📋 **Paste** a job description you're applying to
3. 🤖 **Get AI analysis** showing:
   - **Match Score** - How well your resume fits the job (%)
   - **Skills Found** - Technical skills you have that match
   - **Skills Missing** - Important skills you should add
   - **ATS Score** - How well automated systems will read your resume
   - **AI Insights** - Key strengths and improvement suggestions

## 🌟 Key Features

- ✅ **Real-time Analysis** - Results in under 3 seconds
- 🎯 **Skills Matching** - Identifies 50+ technical skills automatically
- 📊 **ATS Scoring** - Checks format, keywords, and structure
- 🤖 **AI Insights** - Powered by advanced language models
- 📄 **Multiple Formats** - Supports PDF and text files (up to 10MB)
- 🌐 **Works Online** - No installation needed, just visit the link

## 📁 Project Structure

```
ai-resume-analyzer/
├── backend-java/              # Java Spring Boot API
│   ├── src/main/java/         # Java source code
│   │   ├── controller/        # REST endpoints (/analyze, /health)
│   │   ├── service/           # Business logic
│   │   └── model/             # Data models
│   └── build.gradle           # Dependencies
├── app-improved.html          # Main application UI
├── screenshots/               # Demo screenshots
└── START-RESUME-ANALYZER.ps1  # Quick start script
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

## 💻 Run Locally (Optional)

Want to run your own copy? It's easy!

### ⚡ Quick Start (Windows)
```powershell
.\START-RESUME-ANALYZER.ps1
```

### 📋 Manual Setup

**Step 1:** Install Java 17+ ([Download](https://adoptium.net/))

**Step 2:** Clone the project
```bash
git clone https://github.com/rahulpatelrp30/ai-resume-analyzer.git
cd ai-resume-analyzer
```

**Step 3:** Start the backend
```bash
cd backend-java
.\gradlew.bat bootRun       # Windows
./gradlew bootRun           # Mac/Linux
```

**Step 4:** Open in browser (new terminal)
```bash
python -m http.server 3000
```

**Step 5:** Visit `http://localhost:3000/app-improved.html`

## 🔧 How It Works

**Simple explanation:**

1. **You upload** your resume and paste a job description
2. **Backend reads** the resume using Apache PDFBox
3. **Skills engine** searches for 50+ technical skills (Java, Python, React, etc.)
4. **Matching algorithm** compares your skills vs. job requirements
5. **ATS checker** analyzes format, keywords, and sections
6. **AI model** (optional) provides personalized feedback
7. **Results page** shows your scores and suggestions

**Tech Stack:**
- **Backend:** Java 17, Spring Boot 3.2, Apache PDFBox
- **Frontend:** HTML/CSS/JavaScript
- **AI:** OpenAI GPT-4 (optional)
- **Deployment:** GitHub Pages + Render

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
