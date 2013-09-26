package com.mobiletheatertech.plot;

import mockit.Expectations;
import mockit.Mocked;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code Stage}.
 *
 * @author dhs
 * @since 0.0.3
 */
public class StageTest {

    Element element = null;
    Element elementP = null;

    public StageTest() {
    }

    @Test
    public void isMinder() throws Exception {
        Stage stage = new Stage( element );

        assert Minder.class.isInstance( stage );
    }

    @Test
    public void storesFlatAttributes() throws Exception {
        Stage stage = new Stage( element );

        assertEquals( TestHelpers.accessInteger( stage, "width" ), (Integer) 288 );
        assertEquals( TestHelpers.accessInteger( stage, "depth" ), (Integer) 144 );
        assertEquals( TestHelpers.accessInteger( stage, "x" ), (Integer) 56 );
        assertEquals( TestHelpers.accessInteger( stage, "y" ), (Integer) 16 );
        assertEquals( TestHelpers.accessInteger( stage, "z" ), (Integer) 12 );
    }

    @Test
    public void storesProsceniumAttributes() throws Exception {
        Stage stage = new Stage( elementP );

        assertEquals( TestHelpers.accessInteger( stage, "prosceniumWidth" ), (Integer) 288 );
        assertEquals( TestHelpers.accessInteger( stage, "prosceniumDepth" ), (Integer) 144 );
        assertEquals( TestHelpers.accessInteger( stage, "prosceniumHeight" ), (Integer) 144 );
        assertEquals( TestHelpers.accessInteger( stage, "apronDepth" ), (Integer) 56 );
        assertEquals( TestHelpers.accessInteger( stage, "apronWidth" ), (Integer) 16 );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Stage stage = new Stage( element );

        assertNull( TestHelpers.accessString( stage, "id" ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Stage stage = new Stage( element );

        ArrayList<Minder> thing = Drawable.List();

        assert thing.contains( stage );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Stage( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Stage instance is missing required 'width' attribute." )
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Stage( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Stage instance is missing required 'depth' attribute." )
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Stage( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Stage instance is missing required 'x' attribute." )
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new Stage( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Stage instance is missing required 'y' attribute." )
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new Stage( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Stage instance is missing required 'z' attribute." )
    public void noZ() throws Exception {
        element.removeAttribute( "z" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooLargeWidth() throws Exception {
        element.setAttribute( "width", "302" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooLargeDepth() throws Exception {
        element.setAttribute( "depth", "401" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "207" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "401" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooLargeZ() throws Exception {
        element.setAttribute( "z", "241" );
        new Stage( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should have a positive width." )
    public void tooSmallWidth() throws Exception {
        element.setAttribute( "width", "-1" );
        new Stage( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should have a positive depth." )
    public void tooSmallDepth() throws Exception {
        element.setAttribute( "depth", "-1" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "-1" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new Stage( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Stage should not extend beyond the boundaries of the venue." )
    public void tooSmallZ() throws Exception {
        element.setAttribute( "z", "-1" );
        new Stage( element );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<stage width=\"12\" depth=\"65\" x=\"3\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderReset();

        new Parse( stream );

        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 1 );
    }

    @Test
    public void parseMultiple() throws Exception {
        String xml = "<plot>" +
                "<stage width=\"12\" depth=\"65\" x=\"3\" y=\"6\" z=\"9\" />" +
                "<stage width=\"12\" depth=\"65\" x=\"3\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderReset();

        new Parse( stream );

        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 2 );
    }

    @Mocked
    Graphics2D mockCanvas;

    @Test
    public void draw() throws Exception {
        Stage stage = new Stage( element );

        new Expectations() {
            {
                mockCanvas.setPaint( Color.orange );
                mockCanvas.draw( new Rectangle( 56, 16, 288, 144 ) );
            }
        };
        stage.drawPlan( mockCanvas );
    }

    @Test
    public void domUnused() throws Exception {
        Stage stage = new Stage( element );

        stage.dom( null, View.PLAN );
    }

    @Test
    public void stagesCreatesMultiple() {
        fail( "Stage does not yet support multiples" );
        throw new SkipException( "Stage does not yet support multiples" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "width", "550" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "stage" );
        element.setAttribute( "width", "288" );
        element.setAttribute( "depth", "144" );
        element.setAttribute( "x", "56" );
        element.setAttribute( "y", "16" );
        element.setAttribute( "z", "12" );

        elementP = new IIOMetadataNode( "stage" );
        elementP.setAttribute( "proscenium-width", "330" );
        elementP.setAttribute( "proscenium-height", "250" );
        elementP.setAttribute( "proscenium-depth", "22" );
        elementP.setAttribute( "apron-depth", "56" );
        elementP.setAttribute( "apron-width", "440" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}