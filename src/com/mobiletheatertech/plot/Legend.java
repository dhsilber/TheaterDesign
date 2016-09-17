package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.TreeMap;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 6:21 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * @author dhs
 * @since 0.0.7
 */
public class Legend {

    public static final String Tag = "legend";

    public static final String TEXTCOLOR = "black";

    private static TreeMap<Integer, Legendable> LEGENDLIST = new TreeMap<>();
    protected static Double HEIGHT = 0.0;
    protected static Double WIDEST = 0.0;

    private static PagePoint INITIAL;
    private static Draw DRAW;
    protected static Double Y;
    private static Double Center;

    static final String CATEGORY = "legend";

    static final Double TEXTOFFSET = 40.0;
    static final Double QUANTITYOFFSET = 200.0;

    /**
     * Register a callback function that will draw an individual legend entry
     *
     * @param callback {@code Legendable} method which will provide the SVG DOM for an individual
     *                 legend entry
     * @param width    to allow for this entry
     * @param height   to allow for this entry
     */
    public static void Register( Legendable callback, Double width, Double height, LegendOrder order ) {

        LEGENDLIST.put( order.next(), callback );

        WIDEST = (width > WIDEST)
                 ? width
                 : WIDEST;

        HEIGHT += height + 7;
    }

    /**
     * After everything that might want a Legend slot has registered, draw the outline and start off
     * the legend with the name of the plot.
     */
    public static void Startup( Draw draw, View mode, Double x, Double width )
            throws ReferenceException {
        Element group = MainHeaders(draw, x, width);

        DrawBox(draw, x, width, group);
    }

    static void DrawBox(Draw draw, Double x, Double width, Element group) {
        INITIAL = new PagePoint(x + 25, Y);

        // TODO Adding the Y offset to the box height is really cheating
        Double boxHeight = Y + HEIGHT + SvgElement.OffsetY();

        Element box = draw.document().createElement("rect");
        box.setAttribute("fill", "none");
        box.setAttribute("x", x.toString());
        box.setAttribute("y", "1");
        box.setAttribute("width", width.toString());
        box.setAttribute("height", boxHeight.toString());

        group.appendChild(box);
    }

    static Element MainHeaders(Draw draw, Double x, Double width) throws ReferenceException {
        DRAW = draw;

        Element group = draw.document().createElement("g");
        group.setAttribute( "class", Legend.Tag );
        draw.appendRootChild(group);

//        Double x = start;
        Y = 17.0;

        Center = x + (width / 2);
        headerText(draw, group, Center, Y, Event.Name());
        headerText(draw, group, Center, Y, Venue.Building());
        headerText(draw, group, Center, Y, Venue.Name());

        return group;
    }

    public static void Startup( Draw draw, Drawing drawing, View mode, Double x, Double width )
            throws ReferenceException {
        Element group = MainHeaders(draw, x, width);

        headerText(draw, group, Center, Y, drawing.legend() );

        DrawBox(draw, x, width, group);
    }

    /**
     * Draw a centered heading for the Legend
     *
     * @param draw
     * @param parent
     * @param center
     * @param y
     * @param text
     * @throws ReferenceException
     */
    public static void headerText(
            Draw draw, Element parent, Double center, Double y, String text )
            throws ReferenceException
    {
        Text textNode = draw.document().createTextNode( text );
        SvgElement element = draw.element("text");
        element.attribute( "class", "heading" );
        element.attribute( "x", center.toString() );
        element.attribute( "y", y.toString() );
        element.appendChild( textNode );
        parent.appendChild( element.element() );

        Y += 17;
    }

    /**
     * Provide the width of the widest legend entry.
     *
     * @return width of widest legend
     * @since 0.0.13
     */
    public static Double Widest() {
        return WIDEST;
    }

    public static Double QuantityOffset() { return TEXTOFFSET + WIDEST; }

    /**
     * Provide a width for the Legend box which will give the completed
     * drawing the proportions of 11" x 17" paper.
     *
     * @return
     * @throws ReferenceException
     */
    public static Double PlanWidth() throws ReferenceException {
        Double widthAvailable;
        if ( Venue.Depth() < Venue.Width() ) {
            widthAvailable = (Venue.Depth() * 1.54)
                    - Venue.Width() - SvgElement.OffsetX() * 2 - 5 - 2;
        }
        else {
            widthAvailable = (Venue.Depth() * 0.65)
                    - Venue.Width() - SvgElement.OffsetX() * 2 - 5 - 2;
        }

        if (widthAvailable < 0 ) {
            widthAvailable = (Venue.Depth() * 1.54)
                    - Venue.Width() - SvgElement.OffsetX() * 2 - 5 - 2;
        }

        return widthAvailable;
    }

    /**
     * Invoke all of the registered callbacks.
     *
     * TODO Fix this so that I'm not passing coordinates around.
     * Legend should create a group element with a transform="translate(..)" attribute.
     * The callback should get just the group and populate it as needed.
     * It might also need to return the amount of vertical space required.
     * This will simplify:
     *   a) the logic of this, and so my understanding of it
     *   b) the tests
     */
    public static void Callback() {
        PagePoint start = INITIAL;
        PagePoint finish;
        for (Map.Entry<Integer, Legendable> entry : LEGENDLIST.entrySet()) {
            finish = entry.getValue().domLegendItem( DRAW, start );
            start = new PagePoint( INITIAL.x(), finish.y() + 7 );
        }
    }
}
