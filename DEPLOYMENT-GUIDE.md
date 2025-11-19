# 🚀 Deployment Guide - AI Resume Analyzer

## Quick Deploy to Render (FREE)

Your app is ready to deploy! Follow these steps:

### Step 1: Sign up for Render
1. Go to https://render.com
2. Click **"Get Started"**
3. Sign up with your **GitHub account** (rahulpatelrp30)

### Step 2: Deploy Your App
1. Once logged in, click **"New +"** → **"Web Service"**
2. Click **"Connect Repository"** 
3. Find and select: **`rahulpatelrp30/ai-resume-analyzer`**
4. Render will detect the `render.yaml` file automatically

### Step 3: Configure (if needed)
If Render doesn't auto-detect, use these settings:
- **Name:** ai-resume-analyzer
- **Runtime:** Java
- **Build Command:** `cd backend-java && ./gradlew build -x test`
- **Start Command:** `cd backend-java && java -jar build/libs/*.jar`
- **Plan:** Free

### Step 4: Add Environment Variables (Optional)
If you have an OpenAI API key:
1. Go to **Environment** tab
2. Add: `OPENAI_API_KEY` = your-key-here

### Step 5: Deploy!
1. Click **"Create Web Service"**
2. Wait 5-10 minutes for first deployment
3. Your app will be live at: `https://ai-resume-analyzer-xxxx.onrender.com`

## 🎯 What You'll Get

- ✅ Live URL to share with recruiters
- ✅ Auto-deployment on every git push
- ✅ Free SSL certificate (HTTPS)
- ✅ Health monitoring
- ⚠️ Free tier sleeps after 15 min of inactivity (first request takes ~30 seconds to wake up)

## 🔗 After Deployment

1. **Test your live app:** Visit the URL Render provides
2. **Update your resume:** Add the live link
3. **Add to GitHub README:** Update README.md with the deployment link
4. **Share with recruiters:** Include in job applications

## 💡 Alternative: Deploy to Railway

If you prefer Railway:
1. Go to https://railway.app
2. Sign in with GitHub
3. Click **"New Project"** → **"Deploy from GitHub repo"**
4. Select `rahulpatelrp30/ai-resume-analyzer`
5. Railway will auto-detect and deploy

Railway gives you $5 free credit per month.

## 📊 What Recruiters See

Instead of just GitHub code, they'll see:
- ✅ **Live demo:** Working application they can test
- ✅ **Professional:** Shows you can deploy to production
- ✅ **Complete:** From code to deployment
- ✅ **Portfolio-ready:** Add the live link to your resume

---

**Your GitHub:** https://github.com/rahulpatelrp30/ai-resume-analyzer  
**Your Live App:** (Will be generated after deployment)
