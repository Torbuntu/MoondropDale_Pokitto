# Moondrop Dale - Pokitto

# Building
This project is built using [FemtoIDE](https://github.com/felipemanga/femtoIDE) for the [Pokitto](https://talk.pokitto.com/)

#### Title music: 
https://opengameart.org/content/a-new-town-rpg-theme

#### Garden music:
Sun: https://opengameart.org/content/town-theme-rpg
Rain: https://opengameart.org/content/school-day-rain-sun-loop
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

? Change "real" crops for pretend ones.

- Add donk sound for forbidden actions (empty water/seed, untillable land, not harvestable etc...)
- Add a pling + few second timer after successful save before return to Title
- Add atmosphere in Garden scene when music off

