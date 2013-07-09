package com.mobiletheatertech.plot;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author dhs
 * @since 0.0.1
 */
public class ParseTest {
    
    public ParseTest() {
    }

    @Test
    public void parseNoContent() throws Exception {
        String xml = "<plot>"+
            "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestHelpers.MinderReset();
        
        new Parse( stream );

        // Final size of list
        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 0 );
    }

    @Test
    public void createsVenue() throws Exception {
        String xml = "<plot>"+
            "<venue name=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" />"+
            "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        // Initial size of list
        ArrayList<Minder> list = Drawable.List();
        int size = list.size();

        new Parse( stream );

        // Final size of list
        assertEquals( list.size(), size + 1 );
        Minder thing = list.get( list.size()-1);
        
        assert Minder.class.isInstance( thing );
        assert Venue.class.isInstance( thing );
    }

    @Test
    public void createsVenueAndStage() throws Exception {
        String xml = "<plot>"+
            "<venue name=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" />"+
            "<stage width=\"12\" depth=\"15\" x=\"20\" y=\"30\" z=\"11\" />"+
            "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestHelpers.MinderReset();
        
        new Parse( stream );

        // Final size of list
        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 2 );

        Minder venue = list.get( 0 );
        assert Minder.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        Minder stage = list.get( 1 );
        assert Minder.class.isInstance( stage );
        assert Stage.class.isInstance( stage );
    }

    @Test
    public void createsVenueAndHangPoint() throws Exception {
        String xml = "<plot>"+
            "<venue name=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >"+
            "<hangpoint x=\"20\" y=\"30\" />"+
            "</venue>"+
            "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestHelpers.MinderReset();
        
        new Parse( stream );

        // Final size of list
        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 2 );

        Minder venue = list.get( 0 );
        assert Minder.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        Minder hangpoint = list.get( 1 );
        assert Minder.class.isInstance( hangpoint );
        assert HangPoint.class.isInstance( hangpoint );
    }

    @Test
    public void createsVenueAndTwoHangPoint() throws Exception {
        String xml = "<plot>"+
            "<venue name=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >"+
            "<hangpoint x=\"20\" y=\"30\" />"+
            "<hangpoint x=\"25\" y=\"35\" />"+
            "</venue>"+
            "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestHelpers.MinderReset();
        
        new Parse( stream );

        // Final size of list
        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 3 );

        Minder venue = list.get( 0 );
        assert Minder.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        Minder hangpoint = list.get( 1 );
        assert Minder.class.isInstance( hangpoint );
        assert HangPoint.class.isInstance( hangpoint );

        Minder hangpoint2 = list.get( 2 );
        assert Minder.class.isInstance( hangpoint2 );
        assert HangPoint.class.isInstance( hangpoint2 );
        
        assertNotSame( hangpoint, hangpoint2);
    }
    
    /**
     *
     * @throws Exception
     */
    @Test( expectedExceptions=InvalidXMLException.class,
        expectedExceptionsMessageRegExp = "Top level element must be 'plot'" )
    public void noPlot() throws Exception {
        String xml = "<pot><thingy  /></pot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        new Parse( stream );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}