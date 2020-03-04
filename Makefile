GRADLEW_EXEC ?= ./gradlew

all: build postBuild

.PHONY: build
build:
	${GRADLEW_EXEC} clean jar

.PHONY: postBuild
postBuild:
	mv ./build/libs/cryptographer.jar ./
	rm -rf ./build/*
	mv ./cryptographer.jar ./build/
