// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.architecture.structs.fixtures

import org.assertj.core.api.Assertions.assertThat

import com.github.bratek20.architecture.structs.api.*

fun assertStructPath(given: StructPath, expected: String) {
    val diff = diffStructPath(given, expected)
    assertThat(diff).withFailMessage(diff).isEqualTo("")
}
