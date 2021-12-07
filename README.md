# TheaterDesign
Draw lighitng plots from XML descriptions.

Given an XML description of a theatrical venue, with its varioius installed and temporary equipment, draw lighting plots.

The drawing is generated in SVG.

'Theatrical venue' could be a theater with a proscenium stage and linesets, a black box with a fixed grid, or arbitrary spaces (e.g. convention center or hotel ballroom) with no inbuilt infrastructure.

Arbitrary lighting instruments can be defined in the XML.



# Libraries

This should be managed by a Maven or Gradle configuration, but it is working now and I don't have time to mess with it.
Currently using:
* batik-all 1.14
* xerxes 2.12.1 -- all the jars, although I'm not sure I need all of them
* scala-sdk-2.12.7
* JOpenDocument 1.3

... and the IntelliJ TestNG plugin


# Cleanup

This project has suffered from bit rot.
Current plan is to clean it up in small stages. 
I think the thing to do is to move each parsable module into Plot, which can take advantage of the `Populate` mixin.
As I do so, clean up that module (porting it to scala if needed) and make sure its tests are passing and complete.
