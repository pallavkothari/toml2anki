build:
	./gradlew build install

buildSkipTests:
	./gradlew build install -x test

run:
	./build/install/toml2anki/bin/toml2anki

.PHONY: build
