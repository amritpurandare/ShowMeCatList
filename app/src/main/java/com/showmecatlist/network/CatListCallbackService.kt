package com.showmecatlist.network

import com.showmecatlist.dataclasses.Pet

// Service to communicate between view and repository class
interface CatListCallbackService {

    // This method will hold the final result of the back-end call
    fun sendCatList(petList: MutableList<Pet>?)
}