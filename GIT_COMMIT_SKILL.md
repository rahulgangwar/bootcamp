# Git Smart Commit Skill

## Overview
This skill enables Claude to analyze staged Git changes and automatically generate appropriate commit messages following conventional commits format, then commit the changes.

## Core Responsibilities
When this skill is activated, Claude will:
1. **Detect staged files** - Query Git for all staged changes
2. **Analyze diffs** - Review what was changed in each file
3. **Generate message** - Create a meaningful commit message
4. **Execute commit** - Commit changes with the generated message

## Activation Command
```
"Analyze my staged changes and create a smart commit"
or
"Generate and commit my staged changes"
```

## Implementation Steps

### Phase 1: Analyze Staged Changes
Execute these Git commands in sequence:

```bash
# List all staged files
git diff --cached --name-only

# Get detailed diff of all staged changes
git diff --cached --unified=3

# Get summary statistics
git diff --cached --stat
```

**What to look for:**
- File types and languages (Java, Python, JavaScript, etc.)
- Type of changes: additions, modifications, deletions
- Number of lines added/removed
- Patterns in the changes (refactoring, new features, bug fixes)

### Phase 2: Determine Commit Type
Based on the analysis, classify the changes as one of:
- **feat**: New functionality or features
- **fix**: Bug fixes
- **refactor**: Code restructuring without changing behavior
- **docs**: Documentation updates
- **test**: Test file additions/modifications
- **chore**: Dependency updates, configuration changes
- **style**: Formatting, linting fixes (no logic change)
- **perf**: Performance improvements

### Phase 3: Generate Commit Message
Format: `<type>(<scope>): <subject>`

**Rules:**
- Subject line: Maximum 72 characters
- Start with lowercase (except proper nouns)
- Do not end with a period
- Use imperative mood ("add" not "adds", "fixed")
- Optional body after blank line for complex changes
- Body lines: Maximum 100 characters each

**Examples:**
```
feat(auth): add JWT token refresh mechanism
fix(api): resolve null pointer in user service
refactor(utils): simplify string parsing logic
docs(readme): update installation instructions
test(dsaa): add unit tests for sorting algorithms
```

### Phase 4: Execute Commit
```bash
git commit -m "<generated message>"
```

## Git Commands Reference

```bash
# Check current Git status
git status

# See what's staged
git diff --cached

# See what's staged with file stats
git diff --cached --stat

# See files only
git diff --cached --name-only

# List untracked files
git ls-files --others --exclude-standard

# Verify commit was created
git log --oneline -1
```

## Best Practices

1. **Read the diff carefully** - Don't just count lines, understand the intent
2. **Use specific scopes** - Reference specific modules or components
3. **Keep it concise** - Clear and short is better than verbose
4. **Be consistent** - Match the project's existing commit style
5. **Respect conventions** - Follow the conventional commits standard

## Failure Handling

**If no staged files:**
```
Error: No staged changes found
Action: Ask user to stage files first with: git add <files>
```

**If not in a Git repository:**
```
Error: Not a git repository
Action: Navigate to a git repository root
```

**If commit fails:**
```
Action: Check git status and retry
Include error message in response
```

## Multi-Project Usage

This skill works across your entire workspace:
- Navigate to any project directory
- Stage your changes
- Request the skill to analyze and commit
- Works with Java (dsa/, core-java/, lld/), Python, JavaScript, etc.

## Configuration for IDE Integration

If using in Claude IDE or Cline:

1. Save this file as `git-commit-skill.md`
2. Reference in your IDE settings
3. Trigger via command: `@git-commit-skill`

## Example Session

```
User: "I've staged changes to some DSA algorithms. Create a commit for me."

Claude: 
📊 Analyzing staged changes...

Files staged:
- dsa/src/main/java/_01_sorting/MergeSort.java
- dsa/src/main/java/dp/Fibonacci.java

Changes detected:
- Modified sorting algorithm implementation
- Fixed dynamic programming base cases
- Added performance optimizations

Generated commit message:
feat(dsa): optimize merge sort and fibonacci algorithms

- Improve merge sort performance with better pivot selection
- Fix base case handling in fibonacci DP solution
- Add time complexity documentation

✅ Committing...
[main 5f7a3b2] feat(dsa): optimize merge sort and fibonacci algorithms
 2 files changed, 45 insertions(+), 23 deletions(-)
```

## Limitations

- Requires Git to be installed
- Must be in a Git repository
- Requires at least one staged file
- Generates message based on diff analysis only
- Cannot make judgment calls about undocumented intent

## Tips for Best Results

1. Stage related changes together
2. Avoid mixing different types of changes in one commit
3. Add inline comments to code for complex changes
4. The skill works better with clear, focused commits

