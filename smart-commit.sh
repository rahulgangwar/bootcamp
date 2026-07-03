#!/bin/bash

# Git Smart Commit Script
# Analyzes staged changes and generates a commit message
# Usage: ./smart-commit.sh [project-path]

set -e

PROJECT_PATH="${1:-.}"

# Navigate to project
cd "$PROJECT_PATH"

# Check if git repo
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    echo "❌ Not a Git repository!"
    exit 1
fi

# Check for staged changes
if ! git diff --cached --quiet; then
    echo "✅ Found staged changes"
else
    echo "❌ No staged changes found. Stage your files first with: git add <files>"
    exit 1
fi

# Get analysis
echo ""
echo "📊 Staged files:"
git diff --cached --name-only | sed 's/^/  - /'

echo ""
echo "📈 Change summary:"
git diff --cached --stat | head -20

echo ""
echo "🔍 Diff preview (first 50 lines):"
git diff --cached | head -50
if [ $(git diff --cached | wc -l) -gt 50 ]; then
    echo "... (truncated)"
fi

echo ""
echo "================================"
echo "✨ Ready for Claude analysis"
echo "================================"
echo ""
echo "Next steps:"
echo "1. Copy the above information"
echo "2. Paste to Claude"
echo "3. Ask: 'Generate and commit these staged changes'"
echo ""

