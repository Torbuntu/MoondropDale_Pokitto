New Game -> 
Character Select (only affects skins) -> 
Short intro story (some anonymous donor left you their garden next to the river) -> 
Start with 4 seeds of Turnip -> 
(tutorial?) ->

Player tills with hoe -> player plants the seeds -> waters the crops -> 

Here is where the player has "free time" to do whatever until the day is over. Options are:
 - Enter the house to "sleep" to the next day. -> day over scene (shows any items sold, gives a little udpate to the next day like "it'll be a good day")
 - Fish? I would like there to be fishing... Basically a way to increase funds while also spending some off time. 
 
day over -> day over scene (shows any items sold, gives a little udpate to the next day like "it'll be a good day")

REpeat -^



```

-- tuxinator2009#1907 --
So say 1 byte represents a crop you'd have something like:

code:

const byte WATERED = 128;
const byte GROWTH_SHIFT = 3;
const byte GROWTH_BITS = 15;
const byte TYPE_BITS = 7;
byte[] field = Globals.load("field");
for (int i = 0; i < FIELD_WIDTH * FIELD_HEIGHT; ++i)
  System.out.print("Crop (");
  System.out.print(i % FIELD_WIDTH); //x
  System.out.print(", ");
  System.out.print(i / FIELD_WIDTH); //y
  System.out.print("): ");
  if ((field[i] & WATERED) != 0)
    System.out.print("watered ");
  else
    System.out.print("not watered ");
  System.out.print("growth=");
  System.out.print((field[i] >> GROWTH_SHIFT) & GROWTH_BITS);
  System.out.print(" type=");
  System.out.println(field[i] & TYPE_BITS);
  
:code

To change the add 1 to the crop's growth value you'd just field[i] += 1 << GROWTH_SHIFT
To set the crops type you would clear it's current type and set the new type field[i] = (field[i] & ~TYPE_BITS) | newType;
I know the bit manipulation stuff is definitely going to be a little more advanced, but once you get the feel of it it's actually not too difficult.
The main thing to grasp really is how to extract a value with a given bit_size and offset.
```
