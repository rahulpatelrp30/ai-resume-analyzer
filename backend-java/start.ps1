$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot"
Write-Host "Starting AI Resume Analyzer (Java Backend)..." -ForegroundColor Green
Set-Location "c:\Users\Rahul\Desktop\AI Projects\ai-resume-analyzer\backend-java"
.\gradlew.bat bootRun
