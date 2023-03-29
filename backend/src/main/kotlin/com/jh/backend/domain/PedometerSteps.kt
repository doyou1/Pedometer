package com.jh.backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "pedometer_steps")

data class PedometerSteps(
    @Id
    @SequenceGenerator(
        name = "pedometer_steps__id_seq",
        sequenceName = "pedometer_steps__id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pedometer_steps__id_seq")
    val _id: Long = 0,
    val userid: String = "",
    val timestamp: Long = 0,
    val yyyymmdd: String = "",
    val initsteps: Int = 0,
    val steps: String = ""
)
