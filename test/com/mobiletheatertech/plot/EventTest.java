package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

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

    private Element baseForPipeElement = null;
    private Double baseX = 42.1;
    private Double baseY = 57.9;

    Element flatElement = null;
    Double x1 = 1.1;
    Double y1 = 2.2;
    Double x2 = 3.3;
    Double y2 = 4.4;

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
        assertTrue( Populate.class.isInstance( instance ) );
        assertFalse( Legendable.class.isInstance( instance ) );
//        assert Schematicable.class.isInstance( instance );
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

//    @Test( expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp = "Event requires that the Venue be defined." )
//    public void noVenue() throws Exception {
//        new Event( element );
//    }

    @Test
    public void tagCallbackRegisteredPipeBase() {
        Event event = new Event( element );

        assertTrue( event.tags().contains( PipeBase.Tag() ) );
        assertEquals( event.tags().size(), 2 );
    }

    @Test
    public void tagCallbackRegisteredFlat() {
        Event event = new Event( element );

        assertTrue( event.tags().contains( Flat.Tag() ) );
        assertEquals( event.tags().size(), 2 );
    }

    @Test
    public void populateChildrenPipeBase() {
        element.appendChild( baseForPipeElement );
        new Event( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister event = list.get( 0 );
        assert MinderDom.class.isInstance( event );
        assert Event.class.isInstance( event );

        ElementalLister pipebase = list.get( 1 );
        assert MinderDom.class.isInstance( pipebase );
        assert PipeBase.class.isInstance( pipebase );

        assertEquals( list.size(), 2 );
    }

    @Test
    public void populateChildrenFlat() {
        element.appendChild( flatElement );
        new Event( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister event = list.get( 0 );
        assert MinderDom.class.isInstance( event );
        assert Event.class.isInstance( event );

        ElementalLister flat = list.get( 1 );
        assert MinderDom.class.isInstance( flat );
        assert Flat.class.isInstance( flat );

        assertEquals( list.size(), 2 );
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
        TestResets.ElementalListerReset();
//        TestResets.EventReset();
        UniqueId.Reset();
        Event.Reset();

        baseForPipeElement = new IIOMetadataNode( PipeBase.Tag() );
        baseForPipeElement.setAttribute( "x", baseX.toString() );
        baseForPipeElement.setAttribute( "y", baseY.toString() );

        flatElement = new IIOMetadataNode( Flat.Tag() );
        flatElement.setAttribute( "x1", x1.toString() );
        flatElement.setAttribute( "y1", y1.toString() );
        flatElement.setAttribute( "x2", x2.toString() );
        flatElement.setAttribute( "y2", y2.toString() );

        element = new IIOMetadataNode( "event" );
        element.setAttribute( "id", name );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}