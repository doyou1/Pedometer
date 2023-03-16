package com.jh.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class BackendApplication {
//    @Autowired
//    private lateinit var jdbcTemplate: JdbcTemplate

//    override fun run(vararg args: String?) {
//    }

}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

