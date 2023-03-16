package com.jh.backend

import org.springframework.data.jpa.repository.JpaRepository


interface PersonRepository : JpaRepository<Person, Long> {

}