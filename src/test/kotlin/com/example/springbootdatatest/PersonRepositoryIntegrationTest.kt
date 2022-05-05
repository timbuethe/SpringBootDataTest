package com.example.springbootdatatest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Commit
import java.util.UUID

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
internal class PersonRepositoryIntegrationTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    @Commit
    fun updateNames() {

        // GIVEN
        // insert some records
        personRepository.save(Person(UUID.randomUUID(), "Homer", "Simpson"))
        personRepository.save(Person(UUID.randomUUID(), "Bart", "Simpson"))
        personRepository.save(Person(UUID.randomUUID(), "Marge", "Simpson"))

        // read back the ids and assert lastname before updating
        val readBeforeUpdate = personRepository.findAll()
        assertTrue(readBeforeUpdate.isNotEmpty())
        assertTrue(readBeforeUpdate.all { it.lastname == "Simpson" })
        val ids = readBeforeUpdate.map { it.id }

        // WHEN
        val count = personRepository.updateNames("John", "Doe", ids)
        personRepository.flush()

        // THEN
        assertEquals(3, count)
        val readAfterUpdate = personRepository.findAll()
        println(readAfterUpdate)
        assertEquals(3, readAfterUpdate.size)
        assertTrue(readAfterUpdate.all { it.firstname == "John" && it.lastname == "Doe" })
    }

}
