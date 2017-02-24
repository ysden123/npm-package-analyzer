# npm-package-analyzer
NPM package analyzer

## Description
The NPM package analyzer scans all JavaScript (".js") files, 
extracts used module names (from require([module])) and
compares them with dependencies from package.json.

Analyzer outputs number of unused modules and unused module
names. 


## Test Coverage
```
sbt clean coverage test
```

## Build
Run command:
```
sbt assembly
```
JAR location is target\scala-2.12\npm-package-analyzer-assembly-[version].jar

## Running

```
java -jar npm-package-analyzer-assembly-0.1.0.jar
```

