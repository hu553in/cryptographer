package com.github.hu553in.cryptographer.rng

import com.github.hu553in.cryptographer.LINEAR_CONGRUENT_RNG_A
import com.github.hu553in.cryptographer.LINEAR_CONGRUENT_RNG_B
import com.github.hu553in.cryptographer.LINEAR_CONGRUENT_RNG_M

class LinearCongruentRng(private var key: Int) {
    fun next(): Int {
        val new = (key * LINEAR_CONGRUENT_RNG_A + LINEAR_CONGRUENT_RNG_B) % LINEAR_CONGRUENT_RNG_M
        key = new
        return new
    }
}
