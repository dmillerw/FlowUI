# FlowUI

Welcome to FlowUI!

This library aims to solve two of my biggest issues with developing GUIs in Minecraft today, which are ease-of-use,  and the speed and effort required to get something functional. 

The system as it stands today is very much like a blank canvas, with each element having to be placed specifically in code, and can commonly lead to GUI render and functional issues. Mistakes are easy, and overall it's not a joy to work with. Other developers have tried tackling this in various ways, but the most common implementation appears to be at least modelled off Minecraft system. 

That brings us to the main point of this library, and why it stands out from the rest. Like the word Flow in the title might imply, GUIs built through this method rely on a series of layouts and widgets to get the desired outcome. This approach is similar to those found in most app development, and was originally based heavily on how Flutter from Google does widgets. Instead of placing out buttons pixel by pixel, just build up a vertical linear layout, add some button subwidgets, set your callbacks, and you're done!

TODO: Examples, more useful info 
