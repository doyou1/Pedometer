package com.jh.backend

import jakarta.persistence.*

@Entity
@Table(name = "pedometer_user")
data class PedometerUser(
    @Id
    @SequenceGenerator(
        name = "pedometer_user__id_seq",
        sequenceName = "pedometer_user__id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pedometer_user__id_seq")
    val _id: Long = 0, val id: String = "", val pwd: String = ""
)