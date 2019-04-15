package io.wetabq.multiquickshop.shop

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.item.Item
import io.wetabq.multiquickshop.event.PlayerBuyEvent
import io.wetabq.multiquickshop.utils.Lang
import me.onebone.multieconomyapi.MultiEconomyAPI

/**
 * QuickShop
 *
 * @author WetABQ Copyright (c) 2018.09
 * @version 1.0
 */
class BuyShop(sign: String) : Shop(sign) {

    fun buyItem(player: Player, count: Int) {
        if (count <= 0) {
            player.sendMessage(Lang.getMessage("&cPlease enter the correct number"))
            return
        }
        val money = MultiEconomyAPI.getInstance().myMoney(player.name)
        val price = shopData.price * count
        if (money >= price) {
            if (this.getShopChest() != null) {
                val item = Item.get(shopData.itemId, shopData.itemMeta, count)
                if (shopData.unlimited || Shop.hasItem(this.getShopChest()!!.realInventory, item)) {
                    if (shopData.unlimited || Shop.getItemInInventoryCount(this.getShopChest()!!.realInventory, item) >= count) {
                        if (!player.inventory.isFull && player.inventory.canAddItem(item)) {

                            val event = PlayerBuyEvent(player, shopData,count)
                            Server.getInstance().pluginManager.callEvent(event)
                            if (event.isCancelled) return

                            MultiEconomyAPI.getInstance().reduceMoney(player.name, price.toDouble())
                            if (!shopData.unlimited) {
                                MultiEconomyAPI.getInstance().addMoney(shopData.owner, price.toDouble())
                                val owner = Server.getInstance().getPlayerExact(shopData.owner)
                                owner?.sendMessage(Lang.getMessage("&aPlayer &e{} &asuccessfully purchased &6{}*{} &aand got &e{}$", arrayOf(player.name, Lang.getItemName(item), count, price)))
                                this.getShopChest()?.realInventory?.removeItem(item)
                            }
                            player.inventory.addItem(item)
                            player.sendMessage(Lang.getMessage("&aSuccessfully purchased &6{}*{}", arrayOf(Lang.getItemName(item), "$count")))
                        } else {
                            player.sendMessage(Lang.getMessage("&cYour inventory is full and cannot be purchased"))
                        }
                    } else {
                        player.sendMessage(Lang.getMessage("&cThe shop is out of stock and cannot be sold"))
                    }
                } else {
                    player.sendMessage(Lang.getMessage("&cThe shop is out of stock and cannot be sold"))
                }
            }
        } else {
            player.sendMessage(Lang.getMessage("&cYour money is not enough to buy,you still need \${} to buy", arrayOf(shopData.price - money)))
        }
    }
}
