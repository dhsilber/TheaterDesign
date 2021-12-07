Focus now (2021-12-06) is to be able to make drawings for an upcoming event.
I don't have time to fix everything as I would like, but I should, at least, note things that are found to be broken.

# Pipe.scala

1. I no longer have a way to position a pipe without a base. 
This is due to parsing the heirarchy that begins with PipeBase rather than also parsing Pipe.
Perhaps the way to fix this is to have a tag that is parsed that can contain a Pipe.
2. Pipes attached to pipes on bases with a horizontalRotation of 90.0 are not displayed in the correct position.
See TODO in domPlan().
3. offsety should be called drawing-offset-y
4. I've added the ability to attach a cross-pipe to a vertical pipe, but no tests have been written.


# Write.scala

1. Commented out drawTruss because it wasn't drawing anything useful.
2. drawSection has promise, but it will need some work. Commenting it out for now.