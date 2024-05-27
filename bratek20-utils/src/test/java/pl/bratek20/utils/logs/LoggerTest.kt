package pl.bratek20.utils.logs

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.utils.logs.api.Logger
import pl.bratek20.utils.logs.context.LoggerImpl
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class LoggerTest {

    private lateinit var logger: Logger
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream

    @BeforeEach
    fun setUp() {
        logger = someContextBuilder()
            .withModule(LoggerImpl())
            .get(Logger::class.java)

        originalOut = System.out
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun shouldLog() {
        logger.info("message")
        logger.warn("message")
        logger.error("message")
        
        assertThat(normalizedOutput())
            .isEqualTo(
                "INFO: message\n" +
                "WARN: message\n" +
                "ERROR: message\n"
            )
    }

    private fun normalizedOutput(): String {
        return outputStream.toString().replace("\r\n", "\n")
    }
}