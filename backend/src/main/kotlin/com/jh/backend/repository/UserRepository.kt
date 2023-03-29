package com.jh.backend.repository

import com.jh.backend.domain.PedometerUser
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<PedometerUser, Long> {
    fun findById(id: String): PedometerUser?
    fun findByIdAndPwd(id: String, pwd: String): PedometerUser?
}