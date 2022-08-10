package com.gan.phonespecifications

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gan.phonespecifications.adapter.PhoneAdapter
import com.gan.phonespecifications.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    var searchView: SearchView? = null

    private val retrofitService = RetrofitService.getInstance()
    private val adapter = PhoneAdapter()

//    isTopByFans is used, because API returns wrong data for "title"
    var isTopByFans = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()
//        viewModel = ViewModelProvider(this, MyViewModelFactory())[MainViewModel::class.java]

        binding.recyclerview.adapter = adapter

        viewModel.phoneList.observe(this, Observer {
            adapter.setPhoneList(it)
            progressDialog.isVisible = false
        })

        viewModel.data.observe(this, Observer {
            name.text = if (isTopByFans) "Top By Fans" else it.title
        })

        viewModel.errorMessage.observe(this, Observer {
            progressDialog.isVisible = false
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

//        // Associate searchable configuration with the SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        if (menu != null) {
//            (menu.findItem(R.id.search).actionView as SearchView).apply {
//                setSearchableInfo(searchManager.getSearchableInfo(componentName))
//            }
//        }
        val searchItem: MenuItem = menu.findItem(R.id.search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.show_latest_devices -> {
                progressDialog(false)
                viewModel.getPhonesData(MainRepository(retrofitService).getLatest())
            }
            R.id.show_top_by_interest -> {
                progressDialog(false)
                viewModel.getPhonesData(MainRepository(retrofitService).getTopByInterest())
            }

            R.id.show_top_by_fans -> {
                progressDialog(true)
                viewModel.getPhonesData(MainRepository(retrofitService).getTopByFans())
            }


        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            progressDialog(false)
            viewModel.getPhonesData(MainRepository(retrofitService).getSearch(query ?: ""))
        } else {
            isTopByFans = false
            viewModel.getPhonesData(MainRepository(retrofitService).getLatest())
        }
    }


    private fun clearSearch() {
        searchView?.onActionViewCollapsed()
        searchView?.setQuery("", false)
        searchView?.clearFocus()
    }

    private fun progressDialog(isTopByFans: Boolean) {
        progressDialog.isVisible = true
        this.isTopByFans = isTopByFans
        clearSearch()
    }
}