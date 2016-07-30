package com.mobiletheatertech.plot;

import static org.testng.Assert.*;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

/**
 * Created by dhs on 12/15/14.
 */
public class LuminaireInformationTest {

    Element element = null;

    Element venueElement;
    Element elementOnPipe = null;
    Element definitionElement = null;
    Luminaire luminaire;

    final String type = "6x9";
    final String pipeName = "luminaireTestPipe";
    String pipeLocation = "12";
    final String target = "frank";
    final String dimmer = "dimmer";
    final String circuit = "circuit";
    final String channel = "channel";
    final String color = "color";
    final String unit = "unit";

    @Test
    public void isA() throws Exception {
        System.err.println( "A" );
        LuminaireInformation instance = new LuminaireInformation( element, null );
//        LuminaireInformation instance = new LuminaireInformation( element, luminaire );
        System.err.println( "B" );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
    }

    @Test
    public void luminaire() throws Exception {
        LuminaireInformation instance = new LuminaireInformation( element, luminaire );

        assertSame( instance.luminaire(), luminaire );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "bogus" );

//        TestResets.VenueReset();
//        TestResets.MinderDomReset();
//        TestResets.YokeableReset();
//        TestResets.LuminaireReset();
//
//        venueElement = new IIOMetadataNode( "venue" );
//        venueElement.setAttribute( "room", "Test Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        Venue venue = new Venue( venueElement );
//        venue.verify();
//
//        Element pipeElement = new IIOMetadataNode( "pipe" );
//        pipeElement.setAttribute( "id", pipeName );
//        pipeElement.setAttribute( "length", "120" );
//        pipeElement.setAttribute( "x", "12" );
//        pipeElement.setAttribute( "y", "34" );
//        pipeElement.setAttribute( "z", "56" );
//        Pipe pipe = new Pipe( pipeElement );
//        pipe.verify();
//
////        Integer width = 13;
////        Integer length = 27;
////        definitionElement = new IIOMetadataNode( "luminaire-definition" );
////        definitionElement.setAttribute( "name", "6x9" );
////        definitionElement.setAttribute( "width", width.toString() );
////        definitionElement.setAttribute( "length", length.toString() );
////        new LuminaireDefinition( definitionElement );
//
//        elementOnPipe = new IIOMetadataNode( "luminaire" );
//        elementOnPipe.setAttribute( "type", type );
//        elementOnPipe.setAttribute("on", pipeName);
//        elementOnPipe.setAttribute("location", pipeLocation );
//        elementOnPipe.setAttribute("dimmer", dimmer);
//        elementOnPipe.setAttribute("circuit", circuit);
//        elementOnPipe.setAttribute("channel", channel);
//        elementOnPipe.setAttribute("color", color);
//        elementOnPipe.setAttribute("unit", unit);
//        luminaire = new Luminaire( elementOnPipe );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
