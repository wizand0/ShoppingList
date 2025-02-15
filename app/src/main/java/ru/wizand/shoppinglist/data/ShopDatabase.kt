package ru.wizand.shoppinglist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShopItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun shopItemDao(): ShopItemDao
}
