package ru.wizand.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.wizand.shoppinglist.domain.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val count: Int,
    val enabled: Boolean
)