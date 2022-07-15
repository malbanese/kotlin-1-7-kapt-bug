### Overview
This is an Android project which has the following components
1. Basic Kotlin code generation gradle plugin
2. Basic Kotlin annotation processor
3. Basic Android app which shows a single line of text

### Code Generation Plugin
Generates an extremely basic class named SimpleDataSource. It's expected to generate for each Android variant.

### Annotation Processor
Looks for @Inject annotations and prints out extremely basic information about the field

### Android App
Has a single field, of the generated type SimpleDataSource, annotated with @Inject - and displays some text utilizing the generated class.

### What's the problem?
It would appear that during the kapt task, the generated classes are not being properly included on the classpath. When running the app, and looking at the code inside the IDE - there appear to be no issues with using and inspecting the class.

Here are the print messages coming from the annotation processor mentioned above:

#### Inside Kotlin 1.6.21
Note: (name) dataSource
  (kind) FIELD
  (type) com.example.kotlinbug.SimpleDataSource
  
#### Inside Kotlin 1.7
Note: (name) dataSource
  (kind) FIELD
  (type) error.NonExistentClass

This obviously causes issues with certain annnotation processors which rely on the class type!
