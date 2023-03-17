package com.jh.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MainController {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @RequestMapping("/getUsers", method = [RequestMethod.GET])
    fun home(): String {
        return personRepository.findAll().toString()
    }

    @RequestMapping("/post", method = [RequestMethod.POST])
    fun post(@RequestBody list: List<Person>): List<Person> {
        println("request list: $list")
        return personRepository.findAll()
    }
}