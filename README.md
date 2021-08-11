# Moondrop Dale - Pokitto

# Building
This project is built using [FemtoIDE](https://github.com/felipemanga/femtoIDE) for the [Pokitto](https://talk.pokitto.com/)

## Crop types and growth data

type-growth

0-0 : empty, no crop no water no till
0-1 : empty, but tilled for crop

T-2 : Seed is planted, any type
T-3 : Sappling is growing 

1-4_9 : turnip 
2-4_9 : radish (harvestable starting at 7)
3-4_9 : daisy
4-4_9 : Green bean
5-4_9 : Coffee
6-4_9 : Tea
7-4_9 : Tomato
8-4_9 : Blueberry
9-4_9 : Magic Fruit (hehe)


Rendering:

3 soil types.
Light soil - not tilled, 0-0, can't water nor plant.
Medium soil - Tilled! 0-1, able to be watered and planted.
Dark soil - Tilled or planted when watered.

Any crop (T) that is growth < 4 && > 1 (2 and 3) will render 
the same Seed then Sapling before specific crop sprites.



## TODO

- Finish the shop view. 
  - The shop will display each available seed option. 
  - The unavailable will show some sort of "locked" icon. 
    - Maybe when a locked item is hilighted, it will give a hint to unlock it.
  - Available seed options will show a price and current quantity when hilighted.
  
  
- Add the currency to the pause menu so players know how much money they have.

- Add a day transition view.
  - Show how much money was earned, if any.
  - Give a little story info? (like "it will be a nice day" or "It will be rainy tomorrow!")
  
- Add progression system for crops.

- Update/Finish Crop growth cycles.

- Update/add Crops harvest sales. 
 
- Add weather.
  - Right now maybe just rain or nah.
  
- Update/Add animations
  - Crops
  - Tool usage
    - When used, the affected tile should be animated somehow
  - Harvest + Money earned animation
  - Movement(?)
  - Rain
  - Water in the pond
  
- Add sfx and music

- Add Title menu (start/new game, delete data)

- Finish/Update character select screen (after Title screen if New Game selected)

- Change "real" crops for pretend ones.

- Move the Crop rendering logic outside of the Crop object. 
- Crop objects will only store data (type, growth, x, y, watered) allowing a LOT more of them to be stored at once.
- Only store actual Crops in the array. Update the `field` file to accept 4 data parameters per crop (type, growth, x, y). This way we only store actual crops and not empty items.


