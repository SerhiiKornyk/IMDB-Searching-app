package com.example.mvvmexample

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmexample.Repo.ImdbRepo
import com.example.mvvmexample.ViewModels.ImdbFilmsListViewModel
import com.example.mvvmexample.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val TAG = "MAIN_ACTIVITY"

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repo: ImdbRepo
    private val viewModel: ImdbFilmsListViewModel by lazy { ImdbFilmsListViewModel(repo) }
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Default + CoroutineName("ui"))


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        MyApplication.instance?.getComponent()?.inject(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query?.isNotEmpty() == true)
            uiScope.launch { getShow(query) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    private suspend fun getShow(query: String) {
        val result = viewModel.getShows(query)
            .onEach { Log.d(TAG, "handleIntent: $it") }
            .launchIn(uiScope)


    }


}