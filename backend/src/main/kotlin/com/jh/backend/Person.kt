package com.jh.backend

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0, val name: String = "", val age: Int = 0
)