# npm-package-analyzer
NPM package analyzer

[![Build Status](https://travis-ci.org/ysden123/npm-package-analyzer.svg?branch=master)](https://travis-ci.org/ysden123/npm-package-analyzer)

## Description
The NPM package analyzer scans all JavaScript (".js") files, 
extracts used module names (from require([module])) and
compares them with dependencies from package.json.

Analyzer outputs number of unused modules and unused module
names. 

## Build
Run command:
```ahell
sbt assembly
```
JAR location is target\scala-2.11\npm-package-analyzer-assembly-[version].jar

## Running

```shell
java -jar npm-package-analyzer-assembly-0.0.1.jar [path to directory with project]
```

