# cryptographer

## Table of contents

* [Description](#description)
* [Roadmap](#roadmap)
* [Limitations](#limitations)
    * [Affine cipher](#affine-cipher)
    * [Gamma cipher](#gamma-cipher)
    * [Feistel network cipher](#feistel-network-cipher)
* [Tech stack](#tech-stack)
* [How to use](#how-to-use)
    * [CLI args](#cli-args)
        * [Required](#required)
        * [Optionally required for different ciphers](#optionally-required-for-different-ciphers)
        * [Optional](#optional)
    * [Example](#example)

## Description

This project is an encryption/decryption app that can process the source using following ciphers:

* Caesar
* Vigenere
* Affine
* Gamma
* Feistel network

**Note:** All source characters will be converted to the upper case before further processing.\
Each source character which is not English letter will be skipped.\
**These two statements are true for everything excluding Feistel network cipher.**

Also, this app can try to break Vigenere cipher using the frequency cryptanalysis.

**Note:** the probability of the successful breaking is directly proportional to the similarity of the source to the
"normal" English text ("normal" means "written using a common English language").

## Roadmap

- [ ] add tests for all ciphers

## Limitations

### Affine cipher

An Affine cipher's `a` variable is hardcoded in the current version of app.\
It's value is:

* `3` for encryption
* `9` for decryption

### Gamma cipher

Gamma cipher's `a`, `b`, `m` variables are hardcoded in the current version of app.\
Their values are:

* `a = 3`
* `b = 2`
* `m = 40692`

### Feistel network cipher

Feistel network's keys and the round key calculation algorithm are hardcoded in the current version of app.\
Their values are:

* keys: `5`, `2`, `7`, `6`, `1`, `8`, `3`, `9`, `4`, `6`, `2`, `3`, `8`, `5`, `1`, `4`
* the round key calculation algorithm: `key * 7 / 2` (an integer division)

The program works with texts which are encoded using UTF-8, so Feistel network's block size equals 4.

**Note:** The program _**theoretically**_ can fail while processing a text which contains some UTF-8 characters.\
At the moment there's no workaround or fix for this issue.

## Tech stack

* Kotlin
* Gradle

## How to run

1. Install OpenJDK (≥ 17), GNU Make
2. Run `make`
3. Run `java -jar ./build/cryptographer.jar` with `--help` or some another CLI args

### CLI args

#### Required

* `--encrypt`, `--decrypt`, `--break` - the name of an action to do
* `--caesar`, `--vigenere`, `--affine`, `--gamma`, `--feistelNetwork` - the name of the cipher to use
* `--in IN`, `--input IN` - an input file path
* `--out OUT`, `--output OUT` - an output file path

#### Optionally required for different ciphers

* `--shift SHIFT` - the shift required for the Caesar cipher encryption/decryption
* `--key KEY` - the key required for the Vigenere cipher encryption/decryption
  (must consist of English letters only)
* `--b B` - `b` variable required for the Affine cipher encryption/decryption

#### Optional

* `--startKey START_KEY` - the start key for the gamma cipher encryption/decryption (defaults to `1`)
* `-h`, `--help` - show this help message and exit

### Example

```
java -jar ./build/cryptographer.jar --encrypt --gamma --startKey 25 --in src.txt --out dest.txt
```
