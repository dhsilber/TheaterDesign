package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 8:00 PM To change this template use
 * File | Settings | File Templates.
 * <p/>
 * Test {@code Event}.
 *
 * @author dhs
 * @since 0.0.2
 */
public class EventTest {

    Element element = null;
    String name = "Event Name";

    public EventTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        Event event = new Event( element );

        assert MinderDom.class.isInstance( event );
    }

    @Test
    public void storesAttributes() throws Exception {
        Event event = new Event( element );

        assertEquals( TestHelpers.accessString( event, "id" ), name );
    }

    @Test
    public void idUsed() throws Exception {
        Event event = new Event( element );

        assertNotNull( TestHelpers.accessString( event, "id" ) );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Event is not defined." )
    public void noEventName() throws Exception {
        Event.Name();
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Event instance is missing required 'name' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "name" );
        new Event( element );
    }

    @Test
    public void name() throws Exception {
        Element eventElement = new IIOMetadataNode();
        eventElement.setAttribute( "name", "Thingy Name" );

        new Event( eventElement );

        assertEquals( Event.Name(), "Thingy Name" );

        new Event( element );

        assertEquals( Event.Name(), name );
    }

    @Test
    public void parseXML() {
        // See ParseTest.createsVenue*()
    }

    @Test
    public void domSetsTitle() throws Exception {
        Draw draw = new Draw();
        Event event = new Event( element );
        draw.establishRoot();

        event.dom( draw, View.PLAN );

        String titleActual = TestHelpers.getDomElementText( draw, "title" );
        assertEquals( titleActual, name + " - Plan view" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.EventReset();

        element = new IIOMetadataNode( "event" );
        element.setAttribute( "name", name );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}