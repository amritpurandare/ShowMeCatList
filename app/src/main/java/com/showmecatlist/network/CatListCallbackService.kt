package com.showmecatlist.network

import com.showmecatlist.dataclasses.Pet

interface CatListCallbackService {
    fun sendCatList(petList: MutableList<Pet>?)
}