# TODO - Make LibraryManagementSystem runnable

## Step 1: Confirm project structure
- Detect which folder contains the real/consistent Java sources (there are two competing copies under `src/abstract_classes/LibraryManagementSystem/...`).

## Step 2: Remove/ignore broken duplicate sources
- Identify compilation errors due to mismatched packages/classes between duplicates.

## Step 3: Establish correct root folder for compilation
- Ensure files under `src/` use `package ...;` consistently.
- Ensure `Main.java` is in the default package and imports match.

## Step 4: Fix any compilation issues
- Align `LibraryEntity`, `TransactionProcessor`, and `Book` across duplicates so `Main` can compile.

## Step 5: Create run instructions
- Provide exact `javac` / `java` commands to compile and run Main.
- If necessary, provide a minimal build script.

## Step 6: Test
- Run compilation and execution from terminal.

