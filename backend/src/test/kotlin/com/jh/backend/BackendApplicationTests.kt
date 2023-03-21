package com.jh.backend

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.Calendar

@SpringBootTest
class BackendApplicationTests {
    @Test
    fun contextLoads() {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis();
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        println("c.timeInMillis: ${c.timeInMillis}")
    }
}
