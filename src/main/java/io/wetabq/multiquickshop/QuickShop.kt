package io.wetabq.multiquickshop

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.entity.Entity
import cn.nukkit.entity.data.EntityMetadata
import cn.nukkit.item.Item
import cn.nukkit.level.Position
import cn.nukkit.network.protocol.AddItemEntityPacket
import cn.nukkit.network.protocol.RemoveEntityPacket
import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.TextFormat
import io.wetabq.multiquickshop.command.QuickShopCommand
import io.wetabq.multiquickshop.config.ItemNameConfig
import io.wetabq.multiquickshop.config.LanguageConfig
import io.wetabq.multiquickshop.config.MasterConfig
import io.wetabq.multiquickshop.config.ShopConfig
import io.wetabq.multiquickshop.listener.CreateShopListener
import io.wetabq.multiquickshop.listener.InteractionShopListener
import io.wetabq.multiquickshop.listener.PlayerListener
import java.util.regex.Pattern

/**
 * QuickShop
 *
 * @author WetABQ Copyright (c) 2018.09
 * @version 1.0
 */
class QuickShop : PluginBase(){

    lateinit var shopConfig: ShopConfig
    lateinit var masterConfig: MasterConfig
    lateinit var languageConfig: LanguageConfig
    lateinit var itemNameConfig: ItemNameConfig

    override fun onLoad() {
        instance = this
        logger.notice("MultiQuickShop Loading... - Made by WetABQ")
    }

    override fun onEnable() {
        loadConfig()
        TITLE = TextFormat.colorize(masterConfig.title)
        server.pluginManager.registerEvents(CreateShopListener(),this)
        server.pluginManager.registerEvents(InteractionShopListener(),this)
        server.pluginManager.registerEvents(PlayerListener(),this)
        Server.getInstance().commandMap.register("", QuickShopCommand())
        logger.notice("MultiQuickShop Enabled! Vesion:$VERSION")
        logger.notice("Author:WetABQ Github:https://github.com/WetABQ")
}

    override fun onDisable() {
        saveAllConfig()
        logger.warning("MultiQuickShop Disabled! Goodbye~")
    }

    private fun loadConfig() {
        shopConfig = ShopConfig()
        masterConfig = MasterConfig()
        languageConfig = LanguageConfig()
        itemNameConfig = ItemNameConfig()
    }

    private fun saveAllConfig() {
        shopConfig.save()
        masterConfig.save()
        languageConfig.save()
        itemNameConfig.save()
    }

    companion object {

        lateinit var instance : QuickShop
        var TITLE = ""
        const val VERSION = "v1.0.0"

        fun isInteger(str: String): Boolean {
            val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
            return pattern.matcher(str).matches()
        }

        fun addItemEntity(location: Position,item: Item,eid : Long) {
            addItemEntity(Server.getInstance().onlinePlayers.values.toTypedArray(),location,item,eid)
        }

        fun addItemEntity(player: Player,location: Position,item: Item,eid : Long) {
            addItemEntity(arrayOf(player),location,item,eid)
        }

        fun addItemEntity(players: Array<Player>,location: Position,item: Item,eid : Long) {
            val addItemEntityPacket = AddItemEntityPacket()
            addItemEntityPacket.entityUniqueId = eid
            addItemEntityPacket.entityRuntimeId = addItemEntityPacket.entityUniqueId
            addItemEntityPacket.item = item
            addItemEntityPacket.x = location.x.toInt().toFloat() + 0.5F
            addItemEntityPacket.y = location.y.toFloat() + 1
            addItemEntityPacket.z = location.z.toInt().toFloat() + 0.5F
            addItemEntityPacket.speedX = 0f
            addItemEntityPacket.speedY = 0f
            addItemEntityPacket.speedZ = 0f
            val flags = 1L shl Entity.DATA_FLAG_IMMOBILE
            addItemEntityPacket.metadata = EntityMetadata()
                    .putLong(Entity.DATA_FLAGS, flags)
                    .putLong(Entity.DATA_LEAD_HOLDER_EID, -1)
                    .putFloat(Entity.DATA_SCALE, 4f)
            Server.broadcastPacket(players, addItemEntityPacket)
        }

        fun removeItemEntity(eid : Long) {
            removeItemEntity(Server.getInstance().onlinePlayers.values.toTypedArray(),eid)
        }

        fun removeItemEntity(player: Player,eid : Long) {
            removeItemEntity(arrayOf(player),eid)
        }

        fun removeItemEntity(players: Array<Player>,eid : Long) {
            val removeItemEntityPacket = RemoveEntityPacket()
            removeItemEntityPacket.eid = eid
            Server.broadcastPacket(players,removeItemEntityPacket)
        }
    }
}
