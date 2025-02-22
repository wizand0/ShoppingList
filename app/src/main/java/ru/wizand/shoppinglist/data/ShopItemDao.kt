package ru.wizand.shoppinglist.data

import androidx.room.*

@Dao
interface ShopItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shopItem: ShopItemEntity)

    @Delete
    fun delete(shopItem: ShopItemEntity)

    @Update
    fun update(shopItem: ShopItemEntity)

    @Query("SELECT * FROM shop_items WHERE id = :shopItemId")
    fun getShopItem(shopItemId: Int): ShopItemEntity?

    @Query("SELECT * FROM shop_items")
    fun getShopList(): List<ShopItemEntity>
}
