Focus now (2021-12-06) is to be able to make drawings for an upcoming event.
I don't have time to fix everything as I would like, but I should, at least, note things that are found to be broken.

I started on a new featureHack to draw (for drawing) a complete boom, see TODO notes in Pipe.scala

# null No Longer Works

* The Scala Double is now mapped to a JVM primitive rather than an object, so it can no longer hold a null value.
* It seems like it might also have changed semantics between Scala 2 and Scala 3.
I'm thinking that the simplest course might be to 
* I need to change the code everywhere that I use null to indicate an unset value.
1. Marked several `Elemental` methods as deprecated.
2. I'll need an abstract class to encapsulate an attribute's value and the fact of whether it was present: `Attribute`,
which would be extended into `AttributeDouble`, `AttributeInteger`, and `AttributeString`.
3. Those deprecated methods will be replaced with similar methods that return one of the `Attribute` types.

# Positioned items

1. `Pipe` wants to get its position, which might just not be specified (if the `Pipe` has a parent that it gets
its position from) so that might need an class to encapsulate that thought.
2. Other things might also use this class, but most of them don't have a parent from which they might get that
information and so those classes would not want the position to be optional.

# Connecting Pipes with things other than Luminaires (e.g. other Pipes)

1. `LinearSupportsClamp.hang(` should take an `IsClamp` instead of `Luminaire`
2. When a `Pipe` is attached to another `Pipe`, it should generate a `Cheeseburger` object
(which would be an `IsClamp`) to connect them.
3. That `Cheeseburger` would be `mount()`ed to the receiving `Pipe`.
4. I'm about to hack in a Pipe-only solution so that I can make detailed drawings of booms for an upcoming event.


# Pipe.scala

1. I no longer have a way to position a pipe without a base. 
This is due to parsing the heirarchy that begins with PipeBase rather than also parsing Pipe.
Perhaps the way to fix this is to have a tag that is parsed that can contain a Pipe?
2. Pipes attached to pipes on bases with a horizontalRotation of 90.0 are not displayed in the correct position.
See TODO in domPlan().
3. offsety should be called plan-offset-y and only affect plan view drawings, 
so that the front & section drawings can be made to work again.
4. I've added the ability to attach a cross-pipe to a vertical pipe, but no tests have been written.
5. Each Pipe knows its descendant Luminaires via the LinearSupportsClamp/IsClamp architecture,
but does not yet know its descendant Pipes.


# Write.scala

1. Commented out drawTruss because it wasn't drawing anything useful.
2. drawSection has promise, but it will need some work. Commenting it out for now.