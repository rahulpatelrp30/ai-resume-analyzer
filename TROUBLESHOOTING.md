# 🔧 Troubleshooting Guide

## ✅ YOUR APP IS NOW RUNNING!

Both servers should be running in separate PowerShell windows:
- **Backend**: http://localhost:8000 (FastAPI)
- **Frontend**: http://localhost:3000 (React)

---

## Common Issues & Solutions

### 1. "uvicorn is not recognized" Error

**Problem**: Backend won't start, shows error about uvicorn not found

**Solution**:
```powershell
cd backend
pip install fastapi uvicorn starlette python-multipart
```

Then start using:
```powershell
python -m uvicorn main:app --reload
```

---

### 2. Backend/Frontend Not Responding

**Check if servers are running**:
```powershell
Get-Process | Where-Object {$_.ProcessName -like "*python*" -or $_.ProcessName -like "*node*"}
```

**Kill and restart**:
```powershell
# Stop all
Stop-Process -Name python*,node -Force

# Restart
.\start-app.ps1
```

---

### 3. Port Already in Use

**Check what's using port 8000 or 3000**:
```powershell
netstat -ano | findstr ":8000"
netstat -ano | findstr ":3000"
```

**Kill the process** (replace PID with actual process ID):
```powershell
Stop-Process -Id PID -Force
```

---

### 4. Module Not Found Errors

**Backend missing packages**:
```powershell
cd backend
pip install -r requirements.txt
pip install fastapi uvicorn starlette python-multipart pdfplumber openai numpy python-dotenv
```

**Frontend missing packages**:
```powershell
cd frontend
npm install
```

---

### 5. CORS Errors in Browser

This means frontend can't talk to backend.

**Check**:
1. Backend is running on port 8000
2. Frontend is configured to call http://localhost:8000
3. Check `frontend/src/api.ts` has correct URL

---

### 6. PDF Upload Not Working

**Make sure**:
- File is actual PDF (not image renamed to .pdf)
- File size is reasonable (<10MB)
- PDF contains actual text (not scanned image)

**Test with**:
- A resume exported from Word/Google Docs as PDF
- A PDF you can copy text from

---

### 7. No AI Suggestions

**This is normal without OpenAI API key!**

The app works in two modes:

**Without API Key** (current):
- ✅ PDF parsing
- ✅ Keyword matching
- ✅ Match score (keyword-based only)
- ❌ Semantic similarity (shows 0%)
- ❌ AI suggestions (shows placeholder)

**With API Key**:
- ✅ Everything above PLUS
- ✅ Semantic similarity via embeddings
- ✅ GPT-4 powered suggestions

**To add API key**:
1. Get key from: https://platform.openai.com/api-keys
2. Edit `backend/.env`:
   ```
   OPENAI_API_KEY=sk-your-actual-key-here
   ```
3. Restart backend

---

## Quick Commands

### Check if Everything is Working
```powershell
# Test backend
Invoke-WebRequest -Uri "http://localhost:8000/health"

# Test frontend
Invoke-WebRequest -Uri "http://localhost:3000"
```

### Restart Everything
```powershell
# Stop all
Stop-Process -Name python*,node -Force

# Start again
.\start-app.ps1
```

### View API Documentation
Open in browser: http://localhost:8000/docs

---

## Still Having Issues?

1. **Check PowerShell windows** - Both should show running servers with logs
2. **Look for error messages** - Red text in either PowerShell window
3. **Check file paths** - Make sure you're in the right directory
4. **Restart computer** - Sometimes ports get stuck

---

## Test the App

1. **Prepare a resume PDF**:
   - Export from Word/Google Docs
   - Make sure it's text-based (not scanned)

2. **Get a job description**:
   - Copy from LinkedIn, Indeed, etc.
   - Or use the sample in `QUICKSTART.md`

3. **Upload & Analyze**:
   - Go to http://localhost:3000
   - Upload resume
   - Paste job description
   - Click "Analyze Resume"
   - Wait 5-10 seconds for results

---

## Success Indicators

You'll know it's working when you see:

✅ Two PowerShell windows open and running
✅ Backend shows: "Application startup complete"
✅ Frontend shows: "Local: http://localhost:3000"
✅ Browser opens automatically
✅ You see the purple gradient UI
✅ Upload and textarea are clickable
✅ After analysis, you see match score and skills

---

## Performance

- **First analysis**: 5-10 seconds (includes startup)
- **Subsequent analyses**: 2-3 seconds
- **With OpenAI key**: +1-2 seconds for AI generation

---

**Everything working? Great! Read `PROJECT-COMPLETE.md` for what to do next!**
