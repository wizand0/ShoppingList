package ru.wizand.shoppinglist.data

import ru.wizand.shoppinglist.data.mappers.toDomainModel
import ru.wizand.shoppinglist.data.mappers.toEntity
import ru.wizand.shoppinglist.domain.ShopItem
import ru.wizand.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    // Здесь нужно как-то получить dao.
    // Например, инициализировать через late init или посредством инициализации в классе Application.
    private lateinit var dao: ShopItemDao

    fun init(dao: ShopItemDao) {
        this.dao = dao
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        dao.insert(shopItem.toEntity())
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        dao.delete(shopItem.toEntity())
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        dao.update(shopItem.toEntity())
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem? {
        return dao.getShopItem(shopItemId)?.toDomainModel()
    }

//    override suspend fun getShopItem(shopItemId: Int): ShopItem {
//        return dao.getShopItem(shopItemId)?.toDomainModel()
//            ?: throw IllegalArgumentException("ShopItem with id $shopItemId not found")
//    }


    override suspend fun getShopList(): List<ShopItem> = dao.getShopList().map { it.toDomainModel() }
}
