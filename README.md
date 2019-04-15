# Multi-QuickShop
Multi-QuickShop for Nukkit

## How to use?

- **Install plugin**
  1. Download 'Multi-QuickShop' plugin.
  2. Put the plugin in the "plugins" folder.
  3. Restart your server.
- **Create Shop**
  1. Put a chest on ground
  2. Put a sign on the chest
  3. Use the items you want to sell and click on the chest
  4. Enter the price.

## Config:(No changes are recommended)
```
title: '&l&eQuick&6Shop &r&c» &a'

```

--------

## Command:
|    Command    |        Args      |          Describe              |                     Permission                     |
|:----------------:|:--------------|:------------------------------:|:--------------------------------------------------:|
|    mqs        |      [subcmd] [args]      |         Multi-QuickShop          |        All         |

--------

## API: 
```java
//Listening event(PlayerBuyEvent,PlayerSellEvent,PlayerCreateShopEvent,PlayerRemoveShopEvent)
@EventHandler
public void onPlayerBuyEvent(PlayerBuyEvent event) {
  event.player.sendMessage(event.getShopDate()+"\n"+event.getCount());
}
```

--------

### Images:
![SHOP.jpg](https://i.loli.net/2018/09/08/5b93e3efbc115.jpg)

--------

### Open source:

- [GitHub(QuickShop)](https://github.com/WetABQ/Nukkit-QuickShop)

### Author:

- [WetABQ](https://github.com/WetABQ)
