package com.project.onefood.FavouriteList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.Databases.FavouriteDB.FavouriteItem
import com.project.onefood.Databases.FavouriteDB.*
import com.project.onefood.R


class FavouriteActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var adapter: FavouritesRecyvlerView
    lateinit var list: ArrayList<FavouriteItem>
    private lateinit var favouriteItemRepository : FavouriteItemRepository
    private lateinit var favouriteDatabaseDao : FavouriteDatabaseDao
    private lateinit var favouriteItemViewModelFactory: FavouriteItemViewModelFactory
    private lateinit var favouriteItemViewModel: FavouriteItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        favouriteDatabaseDao = FavouriteDatabase.getInstance(this).FavouriteDatabaseDao
        favouriteItemRepository = FavouriteItemRepository(favouriteDatabaseDao)
        favouriteItemViewModelFactory = FavouriteItemViewModelFactory(repository = favouriteItemRepository)
        favouriteItemViewModel = ViewModelProvider(this, favouriteItemViewModelFactory)
            .get(FavouriteItemViewModel::class.java)


        list = ArrayList()
        recyclerView = findViewById(R.id.favouritesRecycler)
        adapter = FavouritesRecyvlerView(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

    }


    override fun onResume() {
        super.onResume()

        val emptyText: TextView = findViewById(R.id.empty)
        favouriteItemViewModel.allFavouriteItemsLiveData.observe(this, Observer { it ->
            emptyText.isVisible = it.isEmpty()
            adapter.replace(it as ArrayList<FavouriteItem>)
            adapter.notifyDataSetChanged()
        })
    }
}