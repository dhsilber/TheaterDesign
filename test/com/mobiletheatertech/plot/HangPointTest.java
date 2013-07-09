package com.mobiletheatertech.plot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.metadata.IIOMetadataNode;
import mockit.Expectations;
import mockit.Mocked;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

/**
 *
 * @author dhs
 * @since 0.0.4
 */
public class HangPointTest {
    
    Element element = null;
    
    public HangPointTest() {
    }
    
    @Test
    public void isMinder() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        assert Minder.class.isInstance( hangPoint );
    }

    @Test
    public void storesAttributes() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        assertEquals( TestHelpers.accessString( hangPoint, "id"), "Blather" );
        assertEquals( TestHelpers.accessInteger( hangPoint, "x"), 296 );
        assertEquals( TestHelpers.accessInteger( hangPoint, "y"), 320 );
    }

    @Test
    public void storesSelf() throws AttributeMissingException,LocationException{
        HangPoint hangPoint = new HangPoint( element );

        ArrayList<Minder> thing = Drawable.List();

        assert thing.contains( hangPoint );
    }

    @Test
    public void finds() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        HangPoint hangPoint = new HangPoint( element );
    }

    /**
     * The lack of an id attribute is not an error.
     *
     * @throws Exception
     */
    @Test
    public void noId() throws Exception {
        element.removeAttribute( "id" );
        HangPoint hangPoint = new HangPoint( element );
    }

    @Test( expectedExceptions=AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "HangPoint is missing required 'x' attribute")
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        HangPoint hangPoint = new HangPoint( element );
    }

    @Test( expectedExceptions=AttributeMissingException.class,
        expectedExceptionsMessageRegExp = "HangPoint is missing required 'y' attribute")
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        HangPoint hangPoint = new HangPoint( element );
    }
    
    @Test( expectedExceptions = LocationException.class,
        expectedExceptionsMessageRegExp = 
        "HangPoint x value outside boundary of the venue")
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "351");
        HangPoint hangPoint = new HangPoint( element );
    }
    
    @Test( expectedExceptions = LocationException.class,
        expectedExceptionsMessageRegExp = 
        "HangPoint y value outside boundary of the venue")
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "401");
        HangPoint hangPoint = new HangPoint( element );
    }
    
    @Test( expectedExceptions = LocationException.class,
        expectedExceptionsMessageRegExp = 
        "HangPoint x value outside boundary of the venue")
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "-1");
        HangPoint hangPoint = new HangPoint( element );
    }
    
    @Test( expectedExceptions = LocationException.class,
        expectedExceptionsMessageRegExp = 
        "HangPoint y value outside boundary of the venue")
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1");
        HangPoint hangPoint = new HangPoint( element );
    }
    

    @Test
    public void parseTwoHangPoints() throws Exception {
        String xml = "<plot>"+
            "<hangpoint x=\"20\" y=\"30\" />"+
            "<hangpoint x=\"25\" y=\"35\" />"+
            "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestHelpers.MinderReset();
        
        new Parse( stream );

        // Final size of list
        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 2 );

        Minder hangpoint = list.get( 0 );
        assert Minder.class.isInstance( hangpoint );
        assert HangPoint.class.isInstance( hangpoint );

        Minder hangpoint2 = list.get( 1 );
        assert Minder.class.isInstance( hangpoint2 );
        assert HangPoint.class.isInstance( hangpoint2 );
        
        assertNotSame( hangpoint, hangpoint2);
    }

    @Test
    public void locate() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        Point location = hangPoint.locate();
        assertEquals( location.x(), 296 );
        assertEquals( location.y(), 320 );
        assertEquals( location.z(), 240 );
    }
    
    @Mocked Graphics2D mockCanvas;
    @Mocked Line2D.Float mockLine2D;
    
    @Test
    public void draw() throws Exception {
        HangPoint hangPoint = new HangPoint( element );
        
        new Expectations() {
            {
                mockCanvas.setPaint( Color.BLUE );
                mockCanvas.draw( new Line2D.Float( 294, 318, 298, 322 ) );
                mockCanvas.draw( new Line2D.Float( 298, 318, 294, 322 ) );
            }
        };
        hangPoint.drawPlan( mockCanvas );
    }

    @Test
    public void domUnused()   throws Exception
    {
        HangPoint hangPoint = new HangPoint( element );

        hangPoint.dom( null );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception
    {
        TestHelpers.MinderReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Test Name");
        venueElement.setAttribute( "width", "350");
        venueElement.setAttribute( "depth", "400");
        venueElement.setAttribute( "height", "240");
        Venue venue = new Venue( venueElement );
        Venue.Height();

        element = new IIOMetadataNode("hangpoint");
        element.setAttribute( "id", "Blather");
        element.setAttribute( "x", "296");
        element.setAttribute( "y", "320");
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}