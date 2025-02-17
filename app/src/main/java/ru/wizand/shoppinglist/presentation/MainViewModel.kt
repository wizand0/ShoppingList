package ru.wizand.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.wizand.shoppinglist.data.ShopListRepositoryImpl
import ru.wizand.shoppinglist.domain.DeleteShopItemUseCase
import ru.wizand.shoppinglist.domain.EditShopItemUseCase
import ru.wizand.shoppinglist.domain.GetShopListUseCase
import ru.wizand.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

//    val shopList = MutableLiveData<List<ShopItem>>()
    val shopList = getShopListUseCase.getShopList()

//    suspend fun getShopList(): List<ShopItem> {
//        return repository.getShopList()
//    }

    suspend fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)

    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        repository.editShopItem(newItem)
    }


}