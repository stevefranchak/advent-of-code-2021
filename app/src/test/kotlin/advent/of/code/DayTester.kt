package advent.of.code

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.io.FileFilter
import java.io.FileNotFoundException
import java.util.stream.Stream

/**
 * This class exercises all input test files under resources.
 */
class DayTester {
    @ParameterizedTest
    @MethodSource("testFiles")
    fun testDayInput(testFile: File) {
        println(testFile)
    }

    companion object {
        @JvmStatic
        fun testFiles(): Stream<File> {
            val testFiles = File("src/test/resources/test_input").listFiles(
                FileFilter { it.extension.lowercase() == ".yaml" }
            )?.sorted()
            return if (testFiles.isNullOrEmpty()) {
                throw FileNotFoundException("Could not locate test input files!")
            } else {
                testFiles.stream()
            }
        }
    }
}
