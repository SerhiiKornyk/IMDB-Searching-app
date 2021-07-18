package com.example.mvvmexample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmexample.Adapters.TVShowsRecyclerViewAdapter
import com.example.mvvmexample.models.TVEntityUI
import com.example.mvvmexample.Repo.ImdbRepo
import com.example.mvvmexample.ViewModels.ImdbFilmsListViewModel
import com.example.mvvmexample.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val TAG = "MAIN_ACTIVITY"

    private lateinit var binding: ActivityMainBinding
    private val tvShowAdapter: TVShowsRecyclerViewAdapter by lazy {
        TVShowsRecyclerViewAdapter(
            emptyList()
        )
    }

    @Inject
    lateinit var repo: ImdbRepo
    private val viewModel: ImdbFilmsListViewModel by lazy { ImdbFilmsListViewModel(repo) }
    private val activityScope: CoroutineScope =
        CoroutineScope(Dispatchers.IO + CoroutineName("mainActivity"))


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        MyApplication.instance?.getComponent()?.inject(this)
        configUI()

    }

    private fun configUI() {
        binding.showRv.adapter = tvShowAdapter
        binding.executePendingBindings()
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
            activityScope.launch {
                val data = getShow(query)
                withContext(Dispatchers.Main) {
                    tvShowAdapter.updateData(data)
                }
            }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    private suspend fun getShow(query: String): List<TVEntityUI> {
        val dataList = mutableListOf<TVEntityUI>()
        viewModel.getShows(query)
            ?.catch {
                dataList.add(
                    TVEntityUI.returnEmpty()
                )
            }
            ?.onEach {
                Log.d(TAG, "handleIntent: $it")
                dataList.add(it)
            }
            ?.launchIn(activityScope)
            ?.join()
        return dataList


    }


}