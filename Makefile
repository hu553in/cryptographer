GRADLEW_EXEC ?= ./gradlew

all: build postBuild

.PHONY: build
build:
	${GRADLEW_EXEC} clean shadowJar

.PHONY: postBuild
postBuild:
	mv ./build/libs/cryptographer-all.jar ./cryptographer.jar
	rm -rf ./build/*
	mv ./cryptographer.jar ./build/
