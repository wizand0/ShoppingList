package ru.wizand.shoppinglist.data.mappers

import ru.wizand.shoppinglist.data.ShopItemEntity
import ru.wizand.shoppinglist.domain.ShopItem

fun ShopItemEntity.toDomainModel(): ShopItem = ShopItem(
    id = id,
    name = name,
    count = count,
    enabled = enabled
)

fun ShopItem.toEntity(): ShopItemEntity = ShopItemEntity(
    id = id,
    name = name,
    count = count,
    enabled = enabled
)
