package com.example.springbootdatatest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Repository
interface PersonRepository : JpaRepository<Person, UUID> {

    @Query("UPDATE Person SET firstname = :firstname, lastname = :lastname WHERE id IN :ids")
    @Modifying
    fun updateNames(firstname: String, lastname: String, ids: List<UUID>) : Int
}

@Entity
data class Person(
    @Id val id:UUID,
    val firstname: String,
    val lastname: String)


