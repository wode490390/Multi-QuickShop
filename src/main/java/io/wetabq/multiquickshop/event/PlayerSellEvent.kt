package io.wetabq.multiquickshop.event

import cn.nukkit.Player
import cn.nukkit.event.Cancellable
import cn.nukkit.event.HandlerList
import cn.nukkit.event.player.PlayerEvent
import io.wetabq.multiquickshop.data.ShopData

/**
 * QuickShop
 *
 * @author WetABQ Copyright (c) 2018.09
 * @version 1.0
 */
class PlayerSellEvent(player : Player,val shopData: ShopData, val count: Int) : PlayerEvent(), Cancellable {

    init {
        super.player = player
    }

    companion object {

        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlers(): HandlerList {
            return handlers
        }
    }
}
