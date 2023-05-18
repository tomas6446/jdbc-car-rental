#!/bin/bash

# Create the target/classes directory if it does not exist
mkdir -p target/classes

# Compile Java source files with Lombok and dotenv JARs
classpath="libs/lombok.jar:libs/java-dotenv-5.2.2.jar:libs/kotlin-stdlib-1.4.0.jar:libs/postgresql-42.6.0.jar"
find . -name "*.java" -print0 | xargs -0 javac -d target/classes -cp "$classpath"

# Add the libs directory to the classpath for running the program
classpath="target/classes:$classpath"

# Run the main class
java -cp "$classpath" com.jdbc.carrental.CarRental
