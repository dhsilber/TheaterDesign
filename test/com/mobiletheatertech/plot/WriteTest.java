package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.File;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * Test {@code Write}.
 *
 * @author dhs
 * @since 0.0.1
 */
public class WriteTest {

    Element venueElement;

    public WriteTest() {
    }

//    @Test
//    public void drawAndCreate() throws Exception {
//
//        new Expectations() {
//            Draw draw;
//
//            {
//                draw = new Draw();
//                Graphics2D canvas = draw.canvas();
//                Minder.DrawAllPlan( canvas );
//                draw.getRoot();
//                Minder.DomAllPlan( draw );
//                draw.create( "/Users/dhs/Plot/out/Fiddle-Faddle.svg" );
//            }
//        };
//        String filename = "Fiddle-Faddle";
//        new Write( filename );
//    }

    @Test
//        ( expectedExceptions=SystemDataMissingException.class,
//        expectedExceptionsMessageRegExp = "User has no home directory")
    public void noHome() throws Exception
    {
        fail( "Must throw exception if user's home is not available." );
    }

    @Test
    public void directory() throws Exception {
        Random random = new Random();
        String directoryName = ((Integer) random.nextInt()).toString();
        String pathName = System.getProperty( "user.home" ) + "/Plot/out/" + directoryName;
        System.err.println( "Pathname: " + pathName );
        File tmp = new File( pathName );
        assertFalse( tmp.exists() );

        new Write( directoryName );
        tmp = new File( pathName );
        assertTrue( tmp.exists() );
        assertTrue( tmp.isDirectory() );

        File index = new File( pathName + "/index.html" );
        assertTrue( index.exists() );

        File css = new File( pathName + "/styles.css" );
        assertTrue( css.exists() );

        File plan = new File( pathName + "/plan.svg" );
        assertTrue( plan.exists() );

        File section = new File( pathName + "/section.svg" );
        assertTrue( section.exists() );

        File front = new File( pathName + "/front.svg" );
        assertTrue( front.exists() );

        File[] contents = tmp.listFiles();
        assertEquals( contents.length, 5 );
    }

//    @Test
//    public void checkbox() throws Exception {
//        String name="name of thing";
//        String tag="tag for thing";
//        HashMap<String,String> thing=new HashMap<>(  );
//        thing                       .put( name,tag );
//
//        Write write=new Write( "stringValue" );
//
//        write.
//
//        String result=
//
//        assertEquals( thing,
//                      "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" checked=\"checked\" value=\"" +
//                              tag + "\" />" +
//                              name + "<br />\n" );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.VenueReset();
        TestResets.MinderDomReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}