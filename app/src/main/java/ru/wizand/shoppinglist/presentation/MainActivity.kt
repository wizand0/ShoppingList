package ru.wizand.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import ru.wizand.shoppinglist.R
import ru.wizand.shoppinglist.data.ShopDatabase
import ru.wizand.shoppinglist.data.ShopListRepositoryImpl

class MainActivity : AppCompatActivity() {

    lateinit var database: ShopDatabase
        private set

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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
}