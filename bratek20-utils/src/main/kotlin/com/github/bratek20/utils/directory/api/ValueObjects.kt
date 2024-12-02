// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.utils.directory.api

data class FileName(
    val value: String
) {
    override fun toString(): String {
        return value.toString()
    }
}

data class DirectoryName(
    val value: String
) {
    override fun toString(): String {
        return value.toString()
    }
}

data class File(
    private val name: String,
    private val content: String,
) {
    fun getName(): FileName {
        return FileName(this.name)
    }

    fun getContent(): FileContent {
        return fileContentCreate(this.content)
    }

    companion object {
        fun create(
            name: FileName,
            content: FileContent,
        ): File {
            return File(
                name = name.value,
                content = fileContentGetValue(content),
            )
        }
    }
}

data class Directory(
    private val name: String,
    private val files: List<File> = emptyList(),
    private val directories: List<Directory> = emptyList(),
) {
    fun getName(): DirectoryName {
        return DirectoryName(this.name)
    }

    fun getFiles(): List<File> {
        return this.files
    }

    fun getDirectories(): List<Directory> {
        return this.directories
    }

    companion object {
        fun create(
            name: DirectoryName,
            files: List<File> = emptyList(),
            directories: List<Directory> = emptyList(),
        ): Directory {
            return Directory(
                name = name.value,
                files = files,
                directories = directories,
            )
        }
    }
}

data class CompareResult(
    private val same: Boolean,
    private val differences: List<String>,
) {
    fun getSame(): Boolean {
        return this.same
    }

    fun getDifferences(): List<String> {
        return this.differences
    }

    companion object {
        fun create(
            same: Boolean,
            differences: List<String>,
        ): CompareResult {
            return CompareResult(
                same = same,
                differences = differences,
            )
        }
    }
}