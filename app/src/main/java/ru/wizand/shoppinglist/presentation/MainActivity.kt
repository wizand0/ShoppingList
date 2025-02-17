package ru.wizand.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import ru.wizand.shoppinglist.R
import ru.wizand.shoppinglist.data.ShopDatabase
import ru.wizand.shoppinglist.data.ShopListRepositoryImpl
import ru.wizand.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    lateinit var database: ShopDatabase
        private set

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize DB
        database = Room.databaseBuilder(
            applicationContext,
            ShopDatabase::class.java,
            "shop_db"
        ).build()
        ShopListRepositoryImpl.init(database.shopItemDao())

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //Subscribe shoplist
        viewModel.shopList.observe(this) {
            adapter.shopList = it
            Log.d("MainActivityTest", it.toString())
        }
//        viewModel.getShopList()

        // Запускаем корутину в lifecycleScope, чтобы вызывать suspend-функцию
//        lifecycleScope.launch {
//            val list = viewModel.getShopList()
            // Здесь можно обновить UI или что-то ещё, используя полученные данные
//            viewModel.shopList.value = list
//        }
    }

    //Вместо RecyclerView используется обычный linearLayout - это очень затратно и грозит лагами
    //Больше не нужно, т.к. переходим на RecyclerView
//    private fun showList(list: List<ShopItem>) {
//        llShopList.removeAllViews()
//        for (shopItem in list) {
//            val layoutId = if (shopItem.enabled) {
//                R.layout.item_shop_enabled
//            } else {
//                R.layout.item_shop_disabled
//            }
//            val view = LayoutInflater.from(this).inflate(layoutId, llShopList, false)
//            val tvName = view.findViewById<TextView>(R.id.tv_name)
//            val tvCount = view.findViewById<TextView>(R.id.tv_count)
//            tvName.text = shopItem.name
//            tvCount.text = shopItem.count.toString()
//            view.setOnLongClickListener {
//                viewModel.changeEnableState(shopItem)
//                true
//            }
//            llShopList.addView(view)
//        }
//    }
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            adapter = ShopListAdapter()
            adapter = adapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
    }
}