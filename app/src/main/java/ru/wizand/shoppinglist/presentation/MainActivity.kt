package ru.wizand.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import ru.wizand.shoppinglist.R
import ru.wizand.shoppinglist.data.ShopDatabase
import ru.wizand.shoppinglist.data.ShopListRepositoryImpl

class MainActivity : AppCompatActivity() {

    lateinit var database: ShopDatabase
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            database = Room.databaseBuilder(
                applicationContext,
                ShopDatabase::class.java,
                "shop_db"
            ).build()

        ShopListRepositoryImpl.init(database.shopItemDao())


    }
}