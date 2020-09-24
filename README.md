# cryptographer

## Description

Encryption/decryption app which can process the source using following ciphers:
* Caesar
* Vigenere
* Affine

All source characters will be converted to the upper case before further processing.\
Each source character which isn't English letter will be skipped.

Affine cipher's `a` variables are hardcoded in the current version of app.\
Their values are:
* `a = 3` for encryption
* `a = 9` for decryption

## Tech stack

* Kotlin
* Gradle

## How to use

1. Install `OpenJDK` (≥ 8), `GNU Make`
2. Run `make`
3. Run `java -jar ./build/cryptographer.jar` with `--help` or some another CLI args

### CLI args

#### Required

* `--encrypt`, `--decrypt` - the name of an action to do
* `--caesar`, `--vigenere`, `--affine` - the name of the cipher to use
* `--in IN`, `--input IN` - an input file path
* `--out OUT`, `--output OUT` - an output file path

#### Optionally required for different ciphers

* `--shift SHIFT` - the shift required for Caesar cipher
* `--key KEY` - the key required for Vigenere cipher
* `--b B` - `b` variable required for Affine cipher

#### Optional

* `-h`, `--help` - show this help message and exit
