package ru.wizand.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.wizand.shoppinglist.data.ShopListRepositoryImpl
import ru.wizand.shoppinglist.domain.AddShopItemUseCase
import ru.wizand.shoppinglist.domain.EditShopItemUseCase
import ru.wizand.shoppinglist.domain.GetShopItemUseCase
import ru.wizand.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    val shopList = repository.getShopList()

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()
    private val _shopItem = MutableLiveData<ShopItem?>()
    private val _shouldCloseScreen = MutableLiveData<Unit>()

    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    val shopItem: MutableLiveData<ShopItem?>
        get() = _shopItem

    val shouldCloseScreen: MutableLiveData<Unit>
        get() = _shouldCloseScreen


    suspend fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parsCount(inputCount)
        val fieldsValid = validateInput(name, count)
        viewModelScope.launch {
            if (fieldsValid) {
                val shopItem = ShopItem(
                    id = 0, // ID будет автоматически сгенерирован Room
                    name = name,
                    count = count,
                    enabled = true // По умолчанию элемент включен
                )
                // Добавляем элемент через репозиторий
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parsCount(inputCount)
        val fieldsValid = validateInput(name, count)
        viewModelScope.launch {
            if (fieldsValid) {
                _shopItem.value?.let {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }

            }
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    private fun parsName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parsCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

}