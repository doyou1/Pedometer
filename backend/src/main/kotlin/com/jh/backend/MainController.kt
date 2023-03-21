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

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var stepsRepository: StepsRepository

    @RequestMapping("/getUsers", method = [RequestMethod.GET])
    fun home(): String {
        return personRepository.findAll().toString()
    }

    @RequestMapping("/post", method = [RequestMethod.POST])
    fun post(@RequestBody list: List<Person>): List<Person> {
        println("request list: $list")
        return personRepository.findAll()
    }

    @RequestMapping("/isDuplicateId", method = [RequestMethod.POST])
    fun isDuplicateId(@RequestBody value: String): Boolean {
        return userRepository.findById(value) != null
    }

    @RequestMapping("/isAbleLogin", method = [RequestMethod.POST])
    fun isAbleLogin(@RequestBody item: LoginUser): Boolean {
        return if (item.isNew) {
            try {
                userRepository.save(PedometerUser(1, item.id, item.pwd))
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            userRepository.findByIdAndPwd(item.id, item.pwd) != null
        }
    }

    @RequestMapping("/getExistData", method = [RequestMethod.POST])
    fun getExistData(@RequestBody value: String): List<PedometerSteps> {
        println("value: $value")

        val result = stepsRepository.findByUserid(value)
        println(result.toString())

        return result
    }

}