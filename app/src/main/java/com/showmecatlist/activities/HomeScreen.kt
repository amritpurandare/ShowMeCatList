package com.showmecatlist.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.showmecatlist.BuildConfig
import com.showmecatlist.R
import com.showmecatlist.dataclasses.Person
import com.showmecatlist.dataclasses.Pet
import com.showmecatlist.network.GetCatDataService
import com.showmecatlist.utils.FEMALE
import com.showmecatlist.utils.MALE
import com.showmecatlist.utils.TYPE_CAT
import com.showmecatlist.utils.isNetworkAvailable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Home screen of the application
class HomeScreen : AppCompatActivity() {

    private val TAG: String = HomeScreen::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Check for network connectivity
        if (isNetworkAvailable(this)) {
            val create = GetCatDataService.create()
            val data = create.getData()

            // execute the request on separate Thread
            data.enqueue(object : Callback<List<Person>> {
                override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, t.localizedMessage)
                    }
                }

                override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                    val body = response.body()

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, body?.size.toString())
                    }

                    // Filter the males
                    val maleList = body?.filter { it.gender == MALE }


                    // Filter the females
                    val feMaleList = body?.filter { it.gender == FEMALE }

                    // From Male list, filter the pets having type == Cat
                    var maleCatList = mutableListOf<Pet>()
                    if (maleList != null) {
                        for (person in maleList) {
                            val cat = person.pets?.filter { it.type == TYPE_CAT }
                            if (cat != null) maleCatList.addAll(cat)
                        }
                    }

                    // From feMale list, filter the pets having type == Cat
                    var feMaleCatList = mutableListOf<Pet>()
                    if (feMaleList != null) {
                        for (person in feMaleList) {
                            val cat = person.pets?.filter { it.type == TYPE_CAT }
                            if (cat != null) feMaleCatList.addAll(cat)
                        }
                    }

                    // sort the male cat list
                    maleCatList = maleCatList.sortedBy { it.name.toLowerCase() } as MutableList<Pet>

                    // sort the female cat list
                    feMaleCatList = feMaleCatList.sortedBy { it.name.toLowerCase() } as MutableList<Pet>

                    if(BuildConfig.DEBUG)
                    {
                        Log.d(TAG, maleCatList.size.toString())
                        Log.d(TAG, feMaleCatList.size.toString())
                    }
                }
            })
        } else {
            Toast.makeText(this, getString(R.string.no_internet_message), Toast.LENGTH_LONG).show()
        }
    }
}
