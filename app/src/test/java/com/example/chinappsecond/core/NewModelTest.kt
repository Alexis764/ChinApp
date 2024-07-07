package com.example.chinappsecond.core

import com.example.chinappsecond.motherobject.NewMotherObject.anyNews
import io.kotlintest.shouldBe
import org.junit.Test
import java.time.LocalDate

class NewModelTest {

    @Test
    fun `toDate Should Return Correct NewModelDate`() {
        //Given
        val newModel = anyNews.copy(time = "2023-02-15")

        //When
        val newModelDate = newModel.toDate()

        //Then
        newModelDate.time shouldBe LocalDate.of(2023, 2, 15)
    }

    @Test
    fun `toDate Should Handle Invalid Date Properly`() {
        //Given
        val newModel = anyNews.copy(time = "2023-15-1000")

        //When
        val newModelDate = newModel.toDate()

        //Then
        newModelDate.time shouldBe LocalDate.now()
    }

}