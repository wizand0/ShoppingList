package ru.wizand.shoppinglist.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val count: Int,
    val enabled:Boolean

) {
    companion object {
        val UNDEFINED_ID: Int = -1
    }
}
