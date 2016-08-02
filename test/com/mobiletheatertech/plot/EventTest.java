package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.testng.Assert.*;

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
    public void isA() throws Exception {
        Event instance = new Event( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertTrue( UniqueId.class.isInstance( instance ) );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assertFalse( LinearSupportsClamp.class.isInstance( instance ) );
        assertFalse( Populate.class.isInstance( instance ) );
        assertFalse( Legendable.class.isInstance( instance ) );
//        assert Schematicable.class.isInstance( instance );
    }

//    @Test
//    public void isMinderDom() throws Exception {
//        Event event = new Event( element );
//
//        assert MinderDom.class.isInstance( event );
//    }

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

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp = "Event is not defined." )
    public void noEventName() throws Exception {
        Event.Name();
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Event instance is missing required 'id' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "id" );
        new Event( element );
    }

    @Test
    public void name() throws Exception {
        Element eventElement = new IIOMetadataNode();
        eventElement.setAttribute( "id", "Thingy Name" );

        new Event( eventElement );

        assertEquals( Event.Name(), "Thingy Name" );

        new Event( element );

        assertEquals( Event.Name(), name );
    }

    @Test
    public void parseXML() throws Exception {
        String name = "Name of event";
        String xml = "<plot>" +
                "<event id=\"" + name + "\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestResets.MinderDomReset();

        new Parse( stream );

        assertEquals( Event.Name(), name );
    }

    @Test
    public void domSetsTitlePlan() throws Exception {
        Draw draw = new Draw();
        Event event = new Event( element );
        draw.establishRoot();

        event.dom( draw, View.PLAN );

        String titleActual = TestHelpers.getDomElementText( draw, "title" );
        assertEquals( titleActual, name + " - Plan view" );
    }

    @Test
    public void domSetsTitleSection() throws Exception {
        Draw draw = new Draw();
        Event event = new Event( element );
        draw.establishRoot();

        event.dom( draw, View.SECTION );

        String titleActual = TestHelpers.getDomElementText( draw, "title" );
        assertEquals( titleActual, name );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
//        TestResets.EventReset();
        UniqueId.Reset();
        Event.Reset();

        element = new IIOMetadataNode( "event" );
        element.setAttribute( "id", name );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}