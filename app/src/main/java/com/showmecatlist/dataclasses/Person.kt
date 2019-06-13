package com.showmecatlist.dataclasses

// Person data class. This class will also hold the list of pets.
// structure is created based on the JSON response
data class Person(val name: String, val gender: String, val age: Int, val pets: List<Pet>?)