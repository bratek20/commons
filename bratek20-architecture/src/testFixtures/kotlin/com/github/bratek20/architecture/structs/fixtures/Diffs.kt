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

data class ExpectedStruct(
)
fun diffStruct(given: Struct, expectedInit: ExpectedStruct.() -> Unit, path: String = ""): String {
    val expected = ExpectedStruct().apply(expectedInit)
    val result: MutableList<String> = mutableListOf()


    return result.joinToString("\n")
}

data class ExpectedStructList(
)
fun diffStructList(given: StructList, expectedInit: ExpectedStructList.() -> Unit, path: String = ""): String {
    val expected = ExpectedStructList().apply(expectedInit)
    val result: MutableList<String> = mutableListOf()


    return result.joinToString("\n")
}