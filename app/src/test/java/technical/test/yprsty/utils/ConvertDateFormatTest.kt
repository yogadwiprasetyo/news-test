package technical.test.yprsty.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertDateFormatTest {

    @Test
    fun `convertDateFormat should correctly format date`() {
        val inputDate = "2023-12-21T15:30:00Z"
        val expectedOutput = "21 December 2023 15:30"
        val convertedDate = inputDate.convertDateFormat()
        assertEquals(expectedOutput, convertedDate)
    }

    @Test
    fun `convertDateFormat should handle invalid date format`() {
        val invalidDate = "Invalid Date"
        val convertedDate = invalidDate.convertDateFormat()
        assertEquals(invalidDate, convertedDate)
    }

}