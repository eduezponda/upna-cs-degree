#!b/bin/bash
javac -d . ../src/bytecounter/*
jar cvf counter.jar bytecounter/*
javac -d . ../src/main/*
jar cvfe main.jar main.App main/*
