package advent.of.code

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.io.FileFilter
import java.io.FileNotFoundException
import java.util.stream.Stream

/**
 * This class exercises all test inputs contained in YAML files located in the test module's resources.
 */
class DayTester {
    @ParameterizedTest
    @MethodSource("testFiles")
    fun testDayInput(testInput: DayTestInput) {
        println(testInput)
    }

    companion object {
        @JvmStatic
        fun testFiles(): Stream<DayTestInput> {
            val testFiles = File("src/test/resources/test_input").listFiles(
                FileFilter { it.extension.lowercase() == "yaml" }
            )?.sorted()
            return if (testFiles.isNullOrEmpty()) {
                throw FileNotFoundException("Could not locate test input files!")
            } else {
                val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
                testFiles.stream().map { mapper.readValue(it, DayTestInput::class.java) }
            }
        }
    }

    data class DayTestInput(val input: String, val results: List<String>, val name: String)
}
