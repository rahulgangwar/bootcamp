# Git Smart Commit Skill - Setup Guide

## Quick Start

This reusable Claude skill analyzes your staged Git changes and creates intelligent commit messages across all your projects.

### Files Created

1. **GIT_COMMIT_SKILL.md** - The main skill definition for Claude
2. **git-commit-skill.md** - Alternative format reference
3. **smart-commit.sh** - Helper script to prepare changes for analysis

## Usage

### Method 1: Direct Claude Request (Recommended)

```bash
# Stage your changes
git add your-files

# Ask Claude to use the skill
cd /path/to/your/project
```

Then ask Claude:
```
"Analyze my staged changes and create a smart commit"
```

Or simply:
```
"Generate and commit my staged changes"
```

### Method 2: Using Helper Script

```bash
# Make script executable
chmod +x /Users/rahul/code/bootcamp/smart-commit.sh

# Navigate to any project in bootcamp
cd /Users/rahul/code/bootcamp/dsa

# Stage your changes
git add src/main/java/_01_sorting/MergeSort.java

# Run the helper
/Users/rahul/code/bootcamp/smart-commit.sh

# Copy the output and share with Claude
```

### Method 3: Manual Process

```bash
# Stage changes
git add .

# In any project, ask Claude with context
# "I have staged changes in $(git diff --cached --stat)"
```

## Supported Projects

This skill works across all projects in `/Users/rahul/code/bootcamp/`:

- ✅ **dsa/** - Data structures and algorithms (Java)
- ✅ **core-java/** - Core Java concepts
- ✅ **lld/** - Low-level design patterns (Java)
- ✅ **spring-security/** - Spring Security implementation
- ✅ **poc/** - Proof of concepts
- ✅ **hello-python/** - Python learning projects

## Example Workflow

### DSA Project Example

```bash
# Navigate to DSA project
cd /Users/rahul/code/bootcamp/dsa

# Make your changes
# ... edit files ...

# Stage changes
git add src/main/java/_01_sorting/MergeSort.java

# Ask Claude
# "Generate and commit my staged changes"

# Claude will:
# 1. Analyze the MergeSort changes
# 2. Understand it's a fix to sorting algorithm
# 3. Generate: "fix(dsa): improve merge sort pivot selection"
# 4. Commit the changes
```

### Core Java Project Example

```bash
cd /Users/rahul/code/bootcamp/core-java

# Stage documentation changes
git add docs/multithreading/

# Ask Claude
# "Analyze my staged changes and commit"

# Claude will:
# 1. See documentation updates
# 2. Generate: "docs(core-java): update executor service documentation"
# 3. Commit changes
```

## Commit Message Conventions

The skill automatically uses conventional commits format:

| Type | Usage | Example |
|------|-------|---------|
| **feat** | New feature | `feat(dsa): add binary search implementation` |
| **fix** | Bug fix | `fix(api): resolve null pointer exception` |
| **refactor** | Code restructure | `refactor(core-java): simplify thread pool logic` |
| **docs** | Documentation | `docs(readme): add setup instructions` |
| **test** | Test additions | `test(lld): add unit tests for observer pattern` |
| **chore** | Maintenance | `chore: update dependencies` |
| **style** | Formatting | `style: apply code formatting` |
| **perf** | Performance | `perf(graph): optimize dijkstra algorithm` |

## Installation for Claude IDE/Cline

1. **Copy the skill file:**
   ```bash
   cp /Users/rahul/code/bootcamp/GIT_COMMIT_SKILL.md ~/.claude/skills/
   ```

2. **Reference in your IDE settings**

3. **Use in conversation:**
   - Start with: `@git-commit-skill`
   - Or mention: "Using the git commit skill"

## Troubleshooting

### No staged changes found
```bash
# Stage your changes first
git add <files-or-path>

# Verify staging
git status
```

### Not in a Git repository
```bash
# Navigate to a git repository
cd /Users/rahul/code/bootcamp/<project>

# Verify
git status
```

### Commit message seems wrong
- Review Claude's analysis
- Ask Claude to regenerate with more context
- Can always amend: `git commit --amend -m "new message"`

## Tips & Best Practices

1. **Stage related changes together** - Keep commits focused
2. **Use clear variable/function names** - Helps Claude understand changes
3. **Add comments to complex changes** - Provides context
4. **Review before confirming** - Always check the generated message
5. **One feature per commit** - Don't mix different types of changes

## Integration Examples

### With Git Hooks (Optional)

Create `.git/hooks/prepare-commit-msg` to suggest messages:

```bash
#!/bin/bash
# This would integrate with the skill automatically
```

### With CI/CD

The commits created by this skill follow conventional commits, which integrates well with:
- Semantic versioning
- Automated changelog generation
- Commit linting

## Advanced Usage

### Custom Scope for Specific File Types

If Claude needs hints about scope:
```
"Analyze changes to src/main/java/graph/ and use 'graph' as the scope"
```

### Multi-File Commits with Context

```
"I've staged changes to authentication and database modules. 
Generate a commit that reflects both changes."
```

### Viewing Generated Commits

```bash
# See the last 5 commits
git log --oneline -5

# See commit details
git show HEAD
```

## Support & Modifications

To customize the skill:

1. Edit **GIT_COMMIT_SKILL.md** to adjust conventions
2. Update helper script as needed
3. Share modified version across projects

## Next Steps

1. Make sure Git is installed: `git --version`
2. Navigate to any project: `cd /Users/rahul/code/bootcamp/dsa`
3. Stage your changes: `git add .`
4. Ask Claude: "Generate and commit my staged changes"

Enjoy smarter commits! 🚀

