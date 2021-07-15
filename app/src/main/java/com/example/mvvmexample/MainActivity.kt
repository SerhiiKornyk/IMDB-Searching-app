package com.example.mvvmexample

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmexample.Repo.ImdbRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repo: ImdbRepo


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        MyApplication.instance?.getComponent()?.inject(this)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

                 CoroutineScope(Dispatchers.IO).launch {
                    val flow = repo.getFilm()
                        .catch { it.printStackTrace() }
                        .collect { entity ->
                            Log.d("TAG", "onCreate: ${entity} ")

                        }


                }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent?) {
        intent ?: return
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
        }
    }

}