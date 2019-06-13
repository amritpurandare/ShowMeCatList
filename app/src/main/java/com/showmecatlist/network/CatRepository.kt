package com.showmecatlist.network

import android.util.Log
import com.showmecatlist.BuildConfig
import com.showmecatlist.dataclasses.Person
import com.showmecatlist.dataclasses.Pet
import com.showmecatlist.utils.FEMALE
import com.showmecatlist.utils.MALE
import com.showmecatlist.utils.TYPE_CAT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
* This class will fetch the data from back-end and will revert it back to view.
* */
class CatRepository {

    private val TAG: String = CatRepository::class.java.name


    /*
    * This method fetches person data from back-end.
    *
    * IT will filter the fetched data based on gender and then based on pet type == CAT
    *
    * Then It will sort both male and female cat list in ascending order.
    *
    * Finally it  adds the MALE and FEMALE sections in it and returns the final list to the caller.
    * */
    fun fetchCatData(callBack: CatListCallbackService) {

        GetCatDataService.create().getData().enqueue(object : Callback<List<Person>> {
            override fun onFailure(call: Call<List<Person>>, t: Throwable) {

                if (BuildConfig.DEBUG) {
                    Log.d("Error fetching data ", t.localizedMessage)
                }

                callBack.sendCatList(null)
            }

            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                val body = response.body()

                if (body == null || body.isEmpty()) {
                    Log.d(TAG, "No records fetched")
                    callBack.sendCatList(null)
                    return
                }

                if (BuildConfig.DEBUG) {
                    Log.d("Person list size ", body.size.toString())
                }

                val maleCatList = getMaleCatList(body)

                val feMaleCatList = getFemaleCatList(body)

                if (BuildConfig.DEBUG) {
                    Log.d("Sorted male list size ", maleCatList.size.toString())
                    Log.d("Sorted Femal list size ", feMaleCatList.size.toString())
                }

                val petList: MutableList<Pet>? = getMaleFemalePetList(maleCatList, feMaleCatList)

                callBack.sendCatList(petList)

            }
        })

    }

    /*
    * This function returns sorted and filtered cat list having owner gender as MALE
    * */
    private fun getMaleCatList(body: List<Person>): MutableList<Pet> {

        // Filter the males
        val maleList = body.filter { it.gender == MALE }

        // From Male list, filter the pets having type == Cat
        var maleCatList = mutableListOf<Pet>()
        for (person in maleList) {
            val cat = person.pets?.filter { it.type == TYPE_CAT }
            if (cat != null) maleCatList.addAll(cat)
        }

        // sort the male cat list
        maleCatList = maleCatList.sortedBy { it.name.toLowerCase() } as MutableList<Pet>

        return maleCatList
    }


    /**
     ** * This function returns sorted and filtered cat list having owner gender as FEMALE
     */
    private fun getFemaleCatList(body: List<Person>): MutableList<Pet> {

        // Filter the females
        val feMaleList = body.filter { it.gender == FEMALE }


        // From feMale list, filter the pets having type == Cat
        var feMaleCatList = mutableListOf<Pet>()
        for (person in feMaleList) {
            val cat = person.pets?.filter { it.type == TYPE_CAT }
            if (cat != null) feMaleCatList.addAll(cat)
        }

        // sort the female cat list
        feMaleCatList = feMaleCatList.sortedBy { it.name.toLowerCase() } as MutableList<Pet>

        return feMaleCatList
    }

    private fun getMaleFemalePetList(
        maleCatList: MutableList<Pet>,
        feMaleCatList: MutableList<Pet>
    ): MutableList<Pet>? {

        val petList: MutableList<Pet>? = mutableListOf()
        if (maleCatList.size > 0) {

            // Add the MALE Section
            val male = Pet(name = MALE, type = MALE)
            petList?.add(male)
            petList?.addAll(maleCatList)
        }

        if (feMaleCatList.size > 0) {

            // Add the FEMALE Section
            val female = Pet(name = FEMALE, type = FEMALE)
            petList?.add(female)
            petList?.addAll(feMaleCatList)
        }

        return petList

    }
}