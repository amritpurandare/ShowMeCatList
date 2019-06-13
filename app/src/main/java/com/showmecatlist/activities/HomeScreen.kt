package com.showmecatlist.activities

import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.showmecatlist.R
import com.showmecatlist.adapters.CatAdapter
import com.showmecatlist.dataclasses.Pet
import com.showmecatlist.network.CatData
import com.showmecatlist.network.CatListCallbackService
import com.showmecatlist.utils.isNetworkAvailable

// Home screen of the application
class HomeScreen : AppCompatActivity(), CatListCallbackService {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerViewManager: RecyclerView.LayoutManager
    private lateinit var mCatAdapter: RecyclerView.Adapter<*>
    private lateinit var progress: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        progress = findViewById(R.id.progress)

        progress.show()

        if (isNetworkAvailable(this)) {
            CatData().fetchCatData(this)
        } else {
            progress.hide()
            Toast.makeText(this, getString(R.string.error_no_internet), Toast.LENGTH_LONG).show()
        }

    }

    override fun sendCatList(petList: MutableList<Pet>?) {
        if (petList != null && !petList.isEmpty()) {

            mRecyclerView = findViewById(R.id.recycler_view)
            mRecyclerViewManager = LinearLayoutManager(this@HomeScreen)
            mCatAdapter = CatAdapter(petList, this@HomeScreen)

            mRecyclerView.visibility = View.VISIBLE
            progress.hide()

            mRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = mRecyclerViewManager

                adapter = mCatAdapter
            }
        } else {
            progress.hide()
            Toast.makeText(this@HomeScreen, getString(R.string.error_no_cats), Toast.LENGTH_LONG).show()
        }
    }
}
