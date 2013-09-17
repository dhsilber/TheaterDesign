/**
 * Plot draws a theatrical lighting plot in SVG from an XML description.
 *
 * Version 0.0.1 opens a file named as
 * {$user.home}/Plot/plotfiles/{@code <filename>}.xml,
 * and writes out an SVG drawing as {$user.home}/Plot/out/{@code <filename>}.svg .
 * In this version, no actual data is manipulated, but a hard-coded box is drawn
 * and a title is inserted into the generated DOM to make sure I can do these
 * things.
 *
 * <h2>Software Debt</h2>
 * No software is perfect. In the interest of taking a finite amount of time to 
 * complete this release I have incurred a bit of debt. I hope to address these
 * issues shortly:
 * <ul>
 * <li>Not enough tests are written - mostly because they require facilities of
 * <code>testng</code> or <code>jmockit</code> that I do not yet understand.
 * (0.0.1)</li>
 * <li>I need to upgrade to Java 7. (0.0.1)</li>
 * <li>Locations of input and output files are hard-coded into the program.
 * There should be a facility for specifying these locations either on the
 * command line or in a configuration file, or perhaps both. (0.0.1)</li>
 * <li>Attempting to skip a test shows the test as skipped in the command line
 * output but hangs in the NetBeans "Test Results" panel. Perhaps this will
 * resolve itself when I upgrade to Java 7? (0.0.3)</li>
 * <li>Made NetBeans Javadoc generator find the overview.html file by giving it
 * a full pathname. How do I give it a relative pathname? (0.0.3)</li>
 * <li>Uche Ogbuji suggests in 
 * <a href="http://www.ibm.com/developerworks/xml/library/x-eleatt/index.html">
 * Principles of XML design: When to use elements versus attributes</a> 
 * considerations that lead me to think that 'name' should not be an attribute
 * of the {@code venue} tag, but should instead be a child of it. (0.0.5)</li>
 * </ul>
 *
 * <h2>Outstanding Issues</h2>
 * <ul>
 * <li>MobileTheaterTech web site is not yet configured. (0.0.1)</li>
 * <li>Firefox 20.0 (on Mac OS X 10.8.2) fails to drawPlan a the bottom part
 * of a 1296 X 1320 rectangle. Opera 12.15 & Safari 6.0.2 display this just
 * fine. (0.0.2)</li>
 * <li>Does {@code Suspend} need to keep a reference to the {@code Truss} that
 * it holds up? A {@code Truss} needs to reference its {@code Suspend}s so that
 * it knows where to drawPlan itself, but I'm not sure the other direction is needed.
 * (0.0.5)</li>
 * </ul>
 *
 * <h2>Business Logic Debt</h2>
 * Likewise, not every feature I could want is present in the software.
 * <ul>
 * <li>Of the Venue elements, only the outer perimeter of the room is supported.
 * (0.0.2)</li>
 * <li>Of the Plot elements, only {@code Stage} is supported. (0.0.3)</li>
 * <li>It would be really nice if a bit of SVG animation showed the name (and
 * other values?) of any given plot item. (0.0.5)</li>
 * <li>The {@link Truss} representation is wrong. If I am representing 
 * individual pieces of gear, the length needs to be limited. If I am
 * representing assemblies of truss segments, I should implement some means of
 * breaking out the individual parts.</li>
 * </ul>
 *
 * @since 0.0.1
 */
package com.mobiletheatertech.plot;