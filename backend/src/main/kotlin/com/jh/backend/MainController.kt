package com.jh.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
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
        println("item: $item")
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
    fun getExistData(@RequestBody id: String): List<PedometerSteps> {
        val result = stepsRepository.findByUserid(id.replace("\"", ""))
        println(result.toString())
        return result
    }

    @RequestMapping("/addItem/{id}", method = [RequestMethod.POST])
    fun addItem(@RequestBody item: Pedometer, @PathVariable id: String): Boolean {
        val new = PedometerSteps(
            -1, id, item.timestamp, item.yyyymmdd, item.initSteps, item.steps
        )
        stepsRepository.save(new)
        return true
    }

    @RequestMapping("/updateItem/{id}", method = [RequestMethod.POST])
    fun updateItem(@RequestBody item: Pedometer, @PathVariable id: String): Boolean {
        val old = stepsRepository.findByUseridAndTimestamp(id, item.timestamp)
        return if (old != null) {
            val new = PedometerSteps(
                old._id, id, old.timestamp, old.yyyymmdd, item.initSteps, item.steps
            )
            stepsRepository.save(new)
            true
        } else {
            false
        }
    }

}