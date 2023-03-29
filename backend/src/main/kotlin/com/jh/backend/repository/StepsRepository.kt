package com.jh.backend.repository

import com.jh.backend.domain.PedometerSteps
import org.springframework.data.jpa.repository.JpaRepository


interface StepsRepository : JpaRepository<PedometerSteps, Long> {
    fun findByUserid(id: String): List<PedometerSteps>

    fun findByUseridAndTimestamp(id: String, timestamp: Long): PedometerSteps?
}