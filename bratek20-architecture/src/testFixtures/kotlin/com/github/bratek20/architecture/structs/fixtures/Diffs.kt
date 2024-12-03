// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.architecture.structs.fixtures

import com.github.bratek20.architecture.structs.api.*

fun diffStructPath(given: StructPath, expected: String, path: String = ""): String {
    if (given.value != expected) { return "${path}value ${given.value} != ${expected}" }
    return ""
}

fun diffStructValue(given: StructValue, expected: String, path: String = ""): String {
    if (given.value != expected) { return "${path}value ${given.value} != ${expected}" }
    return ""
}