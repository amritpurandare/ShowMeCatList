package com.showmecatlist.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.showmecatlist.BuildConfig
import com.showmecatlist.R
import com.showmecatlist.adapters.CatAdapter
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

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerViewManager: RecyclerView.LayoutManager
    private lateinit var mCatAdapter: RecyclerView.Adapter<*>

    private lateinit var progress: ContentLoadingProgressBar

    private val TAG: String = HomeScreen::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        progress = findViewById(R.id.progress)

        progress.show()

        // Check for network connectivity
        if (isNetworkAvailable(this)) {
            val create = GetCatDataService.create()
            val data = create.getData()

            // execute the request on separate Thread
            data.enqueue(object : Callback<List<Person>> {
                override fun onFailure(call: Call<List<Person>>, t: Throwable) {

                    progress.hide()

                    Toast.makeText(this@HomeScreen, getString(R.string.error_message), Toast.LENGTH_LONG).show()

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, t.localizedMessage)
                    }
                }

                override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                    val body = response.body()

                    if (body == null || body.isEmpty()) {
                        Toast.makeText(this@HomeScreen, getString(R.string.error_no_persons), Toast.LENGTH_LONG).show()
                        progress.hide()
                        return
                    }

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, body.size.toString())
                    }

                    // Filter the males
                    val maleList = body.filter { it.gender == MALE }


                    // Filter the females
                    val feMaleList = body.filter { it.gender == FEMALE }

                    // From Male list, filter the pets having type == Cat
                    var maleCatList = mutableListOf<Pet>()
                    for (person in maleList) {
                        val cat = person.pets?.filter { it.type == TYPE_CAT }
                        if (cat != null) maleCatList.addAll(cat)
                    }

                    // From feMale list, filter the pets having type == Cat
                    var feMaleCatList = mutableListOf<Pet>()
                    for (person in feMaleList) {
                        val cat = person.pets?.filter { it.type == TYPE_CAT }
                        if (cat != null) feMaleCatList.addAll(cat)
                    }

                    // sort the male cat list
                    maleCatList = maleCatList.sortedBy { it.name.toLowerCase() } as MutableList<Pet>

                    // sort the female cat list
                    feMaleCatList = feMaleCatList.sortedBy { it.name.toLowerCase() } as MutableList<Pet>

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, maleCatList.size.toString())
                        Log.d(TAG, feMaleCatList.size.toString())
                    }


                    val petList = mutableListOf<Pet>()
                    if (maleCatList.size > 0) {

                        // Add the MALE Section
                        val male = Pet(name = MALE, type = MALE)
                        petList.add(male)
                        petList.addAll(maleCatList)
                    }

                    if (feMaleCatList.size > 0) {

                        // Add the FEMALE Section
                        val female = Pet(name = FEMALE, type = FEMALE)
                        petList.add(female)
                        petList.addAll(feMaleCatList)
                    }

                    progress.hide()
                    if (!petList.isEmpty()) {

                        mRecyclerView = findViewById(R.id.recycler_view)
                        mRecyclerViewManager = LinearLayoutManager(this@HomeScreen)
                        mCatAdapter = CatAdapter(petList,this@HomeScreen)

                        mRecyclerView.visibility = View.VISIBLE

                        mRecyclerView.apply {
                            setHasFixedSize(true)
                            layoutManager = mRecyclerViewManager

                            adapter = mCatAdapter
                        }
                    } else {
                        Toast.makeText(this@HomeScreen, getString(R.string.error_no_cats), Toast.LENGTH_LONG).show()
                    }
                }
            })
        } else {
            progress.hide()
            Toast.makeText(this, getString(R.string.error_no_internet), Toast.LENGTH_LONG).show()
        }
    }
}
