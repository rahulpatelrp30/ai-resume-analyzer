# AI Resume Analyzer - Quick Start Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  AI Resume Analyzer - Java Backend" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Set Java Home
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot"

# Check if backend is already running
$existingProcess = Get-NetTCPConnection -LocalPort 8001 -ErrorAction SilentlyContinue
if ($existingProcess) {
    Write-Host "✅ Backend already running on port 8001" -ForegroundColor Green
} else {
    Write-Host "🚀 Starting Java backend server..." -ForegroundColor Yellow
    
    # Start backend in new window
    Start-Process powershell -ArgumentList "-NoExit", "-Command", `
        "`$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot'; cd 'c:\Users\Rahul\Desktop\AI Projects\ai-resume-analyzer\backend-java'; .\gradlew.bat bootRun"
    
    Write-Host "⏳ Waiting for server to start..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10
    
    # Verify server started
    $serverCheck = Get-NetTCPConnection -LocalPort 8001 -ErrorAction SilentlyContinue
    if ($serverCheck) {
        Write-Host "✅ Backend server started successfully!" -ForegroundColor Green
    } else {
        Write-Host "❌ Server failed to start. Check the backend window for errors." -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "🌐 Opening Resume Analyzer in browser..." -ForegroundColor Yellow
Start-Sleep -Seconds 1

# Open in default browser
Start-Process "c:\Users\Rahul\Desktop\AI Projects\ai-resume-analyzer\app-improved.html"

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "✅ Resume Analyzer is ready!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Backend API: http://localhost:8001" -ForegroundColor Cyan
Write-Host "Health Check: http://localhost:8001/health" -ForegroundColor Cyan
Write-Host ""
Write-Host "📝 Usage:" -ForegroundColor Yellow
Write-Host "  1. Upload a PDF resume" -ForegroundColor White
Write-Host "  2. Paste a job description" -ForegroundColor White
Write-Host "  3. Click 'Analyze Resume'" -ForegroundColor White
Write-Host ""
Write-Host "💡 Tip: Set OPENAI_API_KEY for full AI features" -ForegroundColor Yellow
Write-Host ""
Write-Host "Press Ctrl+C in the backend window to stop the server" -ForegroundColor Gray
