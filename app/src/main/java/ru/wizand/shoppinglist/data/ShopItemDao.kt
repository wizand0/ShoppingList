package ru.wizand.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shopItem: ShopItemEntity)

    @Delete
    suspend fun delete(shopItem: ShopItemEntity)

    @Update
    suspend fun update(shopItem: ShopItemEntity)

    @Query("SELECT * FROM shop_items WHERE id = :shopItemId")
    suspend fun getShopItem(shopItemId: Int): ShopItemEntity?

    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemEntity>>
}
