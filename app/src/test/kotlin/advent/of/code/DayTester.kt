package advent.of.code

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.io.FileFilter
import java.io.FileNotFoundException
import java.util.stream.Stream

/**
 * This class exercises all test inputs contained in YAML files located in the test module's resources. Execute via
 * gradle using `./gradlew test` from the repo's root directory.
 */
@ExtendWith(SoftAssertionsExtension::class)
class DayTester {
    @ParameterizedTest(name = "{0}")
    @MethodSource("testInput")
    fun testDayInput(testInput: DayTestInput, softly: SoftAssertions) {
        val day = testInput.day
        (1..2).forEach { star ->
            val expectedResult = testInput.results[star - 1]
            if (expectedResult == null) {
                println("[Day $day, Star $star] Skipping execution - no expected result defined")
                return@forEach
            }
            val result = executeDayByStar(day, star, testInput.input.trim().split('\n')).toString()
            println("[Day $day, Star $star] Actual: $result | Expected: $expectedResult")
            softly.assertThat(result).isEqualTo(expectedResult.toString())
        }
    }

    companion object {
        @JvmStatic
        fun testInput(): Stream<DayTestInput> {
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

    data class DayTestInput(val input: String, val results: List<String?>, val name: String, val day: Int) {
        /**
         * Used for formatting the parameterized test name.
         */
        override fun toString(): String {
            return name
        }
    }
}
