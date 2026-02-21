# AI-Assisted Development Log

## Tool Used
- **Primary Tool:** Claude (Anthropic) via Cursor IDE
- **Version:** Claude Opus 4.5

## Summary of AI-Assisted Work

### Increments Completed with AI Assistance

| Increment | AI Contribution |
|-----------|-----------------|
| A-CodingStandard | Refactored code to follow CS2103/T naming conventions (camelCase, PascalCase) |
| A-JavaDoc | Added comprehensive JavaDoc comments to all public classes and methods |
| Level-9 Find | Implemented the find command with keyword search |
| A-Jar | Configured Gradle for JAR packaging |
| A-CheckStyle | Set up Checkstyle configuration |
| A-JavaFx | Created JavaFX UI with MainWindow, DialogBox, and FXML |
| A-Assertions | Added Java assertions to document critical assumptions |
| A-FullCommitMessage | Wrote full commit messages following SE-EDU guidelines |
| A-CodeQuality | Comprehensive code quality improvements (SLAP, constants, etc.) |
| B-FixedDurationTasks | Added support for tasks with fixed duration |

### Detailed Contributions

#### Code Quality (A-CodeQuality)
AI helped identify and fix:
- **Magic numbers/strings** → Replaced with named constants
- **SLAP violations** → Extracted helper methods (`extractArguments`, `splitByDelimiter`, `validateDescription`, `validateNotEmpty`, `parseDateTime`)
- **Missing access modifiers** → Added `private` to fields
- **Code duplication** → Extracted common error messages into constants
- **Redundant code** → Removed unused methods and duplicate checks
- **Incomplete JavaDoc** → Added documentation to all public elements

#### JavaFX UI (A-JavaFx)
AI generated:
- `Main.java` - Application entry point
- `Launcher.java` - JavaFX launcher
- `MainWindow.java` - FXML controller
- `DialogBox.java` - Custom dialog component
- `MainWindow.fxml` - UI layout
- Gradle configuration for JavaFX dependencies

#### Assertions (A-Assertions)
AI added assertions for:
- Constructor preconditions (null checks)
- Constructor postconditions (fields initialized)
- Method preconditions (valid parameters)
- Method postconditions (expected state changes)

## What Worked Well

1. **Bulk refactoring** - AI efficiently renamed methods/variables across multiple files while maintaining consistency
2. **Boilerplate generation** - JavaFX setup, FXML files, and JavaDoc were generated quickly
3. **Pattern recognition** - AI identified code quality issues (SLAP violations, magic numbers) that might be missed manually
4. **Error diagnosis** - AI quickly identified issues like redundant fields shadowing parent class
5. **Commit messages** - AI helped structure commit messages following SE-EDU format

## What Didn't Work Well

1. **IDE path confusion** - macOS case-insensitive filesystem caused issues with `AgentSmith` vs `agentsmith` folders
2. **Stray .class files** - Compiled files in source folder caused IDE errors
3. **Context loss** - Some earlier fixes were accidentally reverted, requiring re-application

## Time Saved Estimate

| Task | Manual Time | With AI | Savings |
|------|-------------|---------|---------|
| Code quality refactoring | ~2-3 hours | ~30 mins | ~80% |
| JavaFX setup | ~1-2 hours | ~15 mins | ~85% |
| JavaDoc writing | ~1-2 hours | ~20 mins | ~80% |
| Debugging/fixing errors | ~1 hour | ~15 mins | ~75% |

**Overall estimated time saved: ~70-80%**

## Lessons Learned

1. AI is most effective for repetitive tasks (renaming, adding documentation)
2. Always verify AI changes compile before moving on
3. Clear, specific prompts yield better results
4. AI can miss context that requires domain knowledge
5. Code review of AI output is still essential
