# TheaterDesign
Draw lighitng plots from XML descriptions.

I have a list of things that I need to do to this code. In no particular order:
- Clean up the backlog of failing tests.
- Regularize the XML that I parse. For example, a trussBase should be the parent of a Truss or Pipe rather than a child of one.
- Make a "Mountable" trait for Truss, Pipe, and LightingStand, which will take care of finding all the things that hang from
those items. 
- Convert Truss and Lighting stand to Scala, so that the Mountable trait can be applied to them and Pipe.
- Make the generated ECMAScript do more and better things with the Luminaire data that it has to work with.
- 1. Show all of the Luminaire data instead of just the id.
- 2. Show a table of all the Luminaire data from an entire Mountable.

Luminaire deals with placing an instance on a rotated truss, but since Pipe works differently from Truss, if fails.
This will be a lot easier to fix once all of the Mountable things have been ported to scala and I can factor common functionality out into traits.
In the interim, rotated pipes just won't be work.

