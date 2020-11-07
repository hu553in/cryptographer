# cryptographer

## Description

This project is an encryption/decryption app that can process the source using following ciphers:
* Caesar
* Vigenere
* Affine
* Gamma
* Feistel network

**Note:** All source characters will be converted to the upper case before further processing.
Each source character which is not English letter will be skipped.
**These two statements are true for everything excluding Feistel network cipher.**

Affine cipher's `a` variable is hardcoded in the current version of app.\
It's value is:
* `3` for encryption
* `9` for decryption

Gamma cipher's `a`, `b`, `m` variables are hardcoded in the current version of app.\
Their values are:
* `a = 3`
* `b = 2`
* `m = 40692`

Feistel network's keys and round key calculation algorithm
are hardcoded in the current version of app.\
Their values are:
* keys: `5`, `2`, `7`, `6`, `1`, `8`, `3`, `9`, `4`, `6`, `2`, `3`, `8`, `5`, `1`, `4`
* round key calculation algorithm: `key * 7 / 2`

Also, this app can try to break Vigenere cipher using the frequency cryptanalysis.

**Note:** the probability of the successful breaking is directly proportional
to the similarity of the source to the "normal" English text
("normal" means "written using a common English language").

## Tech stack

* Kotlin
* Gradle

## How to use

1. Install `OpenJDK` (≥ 8), `GNU Make`
2. Run `make`
3. Run `java -jar ./build/cryptographer.jar` with `--help` or some another CLI args

### CLI args

#### Required

* `--encrypt`, `--decrypt`, `--break` - the name of an action to do
* `--caesar`, `--vigenere`, `--affine`, `--gamma`, `--feistelNetwork` - the name of the cipher to use
* `--in IN`, `--input IN` - an input file path
* `--out OUT`, `--output OUT` - an output file path

#### Optionally required for different ciphers

* `--shift SHIFT` - the shift required for Caesar cipher encryption/decryption
* `--key KEY` - the key required for Vigenere cipher encryption/decryption
(must consist of English letters only)
* `--b B` - `b` variable required for Affine cipher encryption/decryption

#### Optional

* `--startKey START_KEY` - the start key for gamma cipher encryption/decryption (defaults to `1`)
* `-h`, `--help` - show this help message and exit

### Example

```
java -jar ./build/cryptographer.jar --encrypt --gamma --startKey 25 --in src.txt --out dest.txt
```
