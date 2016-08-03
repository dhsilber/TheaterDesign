package com.mobiletheatertech.plot;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.TreeMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 6:20 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.7
 */
public class LegendTest {

    private int calledBack = 0;
    private Element eventElement;
    private Element venueElement;
    private Element drawingElement;

    class testLegendable implements Legendable {
        @Override
        public void legendCountReset() {}

        @Override
        public PagePoint domLegendItem( Draw draw, PagePoint start ) {
            calledBack++;
            return new PagePoint( 12.0, 13.0 );
        }
    }

    @Test
    public void hasList() throws Exception {
        Object thingy = TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend",
                                                        "LEGENDLIST" );
        assertNotNull( thingy );
        assert TreeMap.class.isInstance( thingy );
    }

    @Test
    public void registerCallback() throws Exception {
        testLegendable legendableObject = new testLegendable();
        Legend.Register( legendableObject, 1.0, 2.0, LegendOrder.Show );

        TreeMap<Integer, Legendable> thingy = (TreeMap<Integer, Legendable>) TestHelpers.accessStaticObject(
                "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertNotNull( thingy );
        assertEquals( thingy.size(), 1 );
    }

    @Test
    public void invokeCallback() throws Exception {
        new Event( eventElement );
        new Venue( venueElement );

        testLegendable legendableObject = new testLegendable();
        Legend.Register( legendableObject, 1.0, 2.0, LegendOrder.Show );
        Draw draw = new Draw();
        draw.establishRoot();

        Legend.Startup( draw, View.PLAN, 100.0, 100 );
//        TestHelpers.setStaticObject(
//                "com.mobiletheatertech.plot.Legend", "INITIAL", new PagePoint( 1, 2 ) );

        Legend.Callback();

        assertEquals( calledBack, 1 );
    }

    @Test
    public void widest() {
        testLegendable legendableObject = new testLegendable();
        Double wildlyLarge = Double.MAX_VALUE;
        Legend.Register( legendableObject, wildlyLarge, 7.0, LegendOrder.Show );

        assertEquals( Legend.Widest(), wildlyLarge );
    }

    @Test
    public void startupHeadings() throws Exception {
        new Event( eventElement );
        new Venue( venueElement );

        testLegendable legendableObject = new testLegendable();
        Legend.Register( legendableObject, 1.0, 2.0, LegendOrder.Show );
        Draw draw = new Draw();
        draw.establishRoot();

        Legend.Startup(draw, View.PLAN, 100.0, 100);

        NodeList list = draw.root().getElementsByTagName( "text" );
        assertEquals( list.getLength(), 3 );

        Node eventNameNode = list.item( 0 );
        assertEquals( eventNameNode.getNodeType(), Node.ELEMENT_NODE );
        Element eventNameElement = (Element) eventNameNode;
        String text = eventNameElement.getTextContent();
        assertEquals( text, Event.Name() );

        Node venueBuildingNode = list.item( 1 );
        assertEquals( venueBuildingNode.getNodeType(), Node.ELEMENT_NODE );
        Element venueBuildingElement = (Element) venueBuildingNode;
        text = venueBuildingElement.getTextContent();
        assertEquals( text, Venue.Building() );

        Node VenueNameNode = list.item( 2 );
        assertEquals( VenueNameNode.getNodeType(), Node.ELEMENT_NODE );
        Element venueNameElement = (Element) VenueNameNode;
        text = venueNameElement.getTextContent();
        assertEquals( text, Venue.Name() );
    }

    @Test
    public void startupDrawingHeadings() throws Exception {
        new Event( eventElement );
        new Venue( venueElement );
        Drawing drawing = new Drawing( drawingElement );

        testLegendable legendableObject = new testLegendable();
        Legend.Register( legendableObject, 1.0, 2.0, LegendOrder.Show );
        Draw draw = new Draw();
        draw.establishRoot();

        Legend.Startup( draw, drawing, View.PLAN, 100.0, 100 );

        NodeList list = draw.root().getElementsByTagName( "text" );
        assertEquals( list.getLength(), 4 );

        Node eventNameNode = list.item( 0 );
        assertEquals( eventNameNode.getNodeType(), Node.ELEMENT_NODE );
        Element eventNameElement = (Element) eventNameNode;
        String text = eventNameElement.getTextContent();
        assertEquals( text, Event.Name() );

        Node venueBuildingNode = list.item( 1 );
        assertEquals( venueBuildingNode.getNodeType(), Node.ELEMENT_NODE );
        Element venueBuildingElement = (Element) venueBuildingNode;
        text = venueBuildingElement.getTextContent();
        assertEquals( text, Venue.Building() );

        Node VenueNameNode = list.item( 2 );
        assertEquals( VenueNameNode.getNodeType(), Node.ELEMENT_NODE );
        Element venueNameElement = (Element) VenueNameNode;
        text = venueNameElement.getTextContent();
        assertEquals( text, Venue.Name() );

        Node drawingNameNode = list.item( 3 );
        assertEquals( drawingNameNode.getNodeType(), Node.ELEMENT_NODE );
        Element drawingNameElement = (Element) drawingNameNode;
        text = drawingNameElement.getTextContent();
        assertEquals(text, drawing.legend());
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.LegendReset();
        UniqueId.Reset();
        Event.Reset();

        calledBack = 0;

        eventElement = new IIOMetadataNode( "event" );
        eventElement.setAttribute( "id", "name" );

        venueElement = new IIOMetadataNode( "venue" );
//        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "room", "Test Room" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );

        drawingElement = new IIOMetadataNode( "drawsing" );
        drawingElement.setAttribute( "id", "drawingID" );
        drawingElement.setAttribute( "filename", "drawingFilename" );
        drawingElement.setAttribute( "view", "" );
        drawingElement.setAttribute( "legend", "... of the lost mines" );
    }

}
