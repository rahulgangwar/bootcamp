# Git Commit Message Generator Skill

## Purpose
Analyzes staged Git changes and generates appropriate commit messages using Claude AI, then commits the changes.

## Instructions
You are a Git commit message assistant. When asked to analyze staged changes:

1. Execute the command to get staged file changes from Git
2. Analyze the diffs to understand what changed
3. Generate a concise, professional commit message following conventional commits format
4. Commit the changes with the generated message

## Tools Used
- `bash`: Execute Git commands to fetch staged changes and commit

## Workflow

### Step 1: Get Staged Changes
Execute: `git diff --cached --name-only` to list staged files
Execute: `git diff --cached --unified=3` to get the actual changes
Execute: `git diff --cached --stat` to get a summary

### Step 2: Analyze Changes
Review the diff output to understand:
- What files were modified, added, or deleted
- The nature of changes (bug fix, feature, refactor, etc.)
- The scope and impact of changes

### Step 3: Generate Commit Message
Create a commit message that:
- Follows conventional commits format
- Starts with a type: feat, fix, refactor, docs, test, chore, style, perf
- Has a clear, concise subject line (≤72 characters)
- Includes details in body if needed (≤100 characters per line)
- Is specific about what changed and why

### Step 4: Commit Changes
Execute: `git commit -m "Your message"` to commit the changes

## Example Output
```
feat: add user authentication module

- Implement login and registration endpoints
- Add JWT token generation
- Validate user credentials against database
```

## How to Use This Skill

### Method 1: Direct Request
Ask Claude:
"Analyze my staged git changes and create a commit"

### Method 2: In Cline/Claude IDE
Add this skill to your configuration and trigger it when needed.

### Method 3: As a Reusable Template
1. Save this file as `git-commit-skill.md`
2. Reference it when you want Claude to help with commits
3. Works across all projects in your workspace

## Installation

1. Copy `git-commit-skill.md` to your project root or a skills directory
2. Tell Claude to use this skill when analyzing staged changes
3. Or reference it directly in your IDE/editor configuration

## Integration with Your Workspace

This skill is designed to be reusable across all projects in `/Users/rahul/code/bootcamp/`:
- dsa/
- core-java/
- lld/
- spring-security/
- poc/
- hello-python/

Simply navigate to any project directory and request the skill.

## Command Sequence

```bash
# From any project directory in the workspace:
1. cd /path/to/your/project
2. git add your-files
3. Ask Claude: "Analyze my staged changes and commit them"
```

## Conventional Commits Format

- **feat**: A new feature
- **fix**: A bug fix
- **refactor**: Code change that neither fixes a bug nor adds a feature
- **docs**: Documentation only changes
- **style**: Changes that don't affect code meaning (formatting, semicolons, etc.)
- **test**: Adding missing tests or correcting existing tests
- **chore**: Changes to build process, dependencies, or tools
- **perf**: Code change that improves performance

## Notes

- Always review the generated commit message before confirming
- The skill works with any Git repository
- Ensure you have staged the changes you want to commit
- The skill requires Git to be installed and the current directory to be a Git repository

