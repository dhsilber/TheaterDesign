package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code Proscenium}.
 *
 * @author dhs
 * @since 0.0.7
 */
public class ProsceniumTest {

    Element element = null;

    Double width = 330.0;
    Double depth = 22.0;
    Double height = 250.0;
    Double x = 250.0;
    Double y = 144.0;
    Double z = 12.0;

    Venue venue = null;
    Element venueElement = null;

    String venueRoom = "Test Room";
    Double venueWidth = 550.0;
    Double venueDepth = 400.0;
    Double venueHeight = 263.0;


    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ProsceniumReset();
        TestResets.PointReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", venueRoom );
        venueElement.setAttribute( "width", venueWidth.toString() );
        venueElement.setAttribute( "depth", venueDepth.toString() );
        venueElement.setAttribute( "height", venueHeight.toString() );
        venue = new Venue( venueElement );

        element = new IIOMetadataNode( "proscenium" );
        element.setAttribute( "width", width.toString() );
        element.setAttribute( "height", height.toString() );
        element.setAttribute( "depth", depth.toString() );
        element.setAttribute( "x", x.toString() );
        element.setAttribute( "y", y.toString() );
        element.setAttribute( "z", z.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void constantTag() {
        assertEquals( Proscenium.Tag, "proscenium" );
    }

    @Test
    public void constantColor() {
        assertEquals( Proscenium.Color, "black" );
    }

    @Test
    public void constantFadedColor() {
        assertEquals( Proscenium.FadedColor, "gray" );
    }

    @Test
    public void constantStageColor() {
        assertEquals( Proscenium.StageColor, "black" );
    }

    @Test
    public void isA() throws Exception {
        Proscenium instance = new Proscenium(element);

        assert Elemental.class.isInstance(instance);
        assert ElementalLister.class.isInstance(instance);
        assert Verifier.class.isInstance(instance);
        assert Layerer.class.isInstance(instance);
        assert MinderDom.class.isInstance(instance);
    }

    @Test
    public void storesAttributes() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        assertEquals( TestHelpers.accessDouble( proscenium, "width" ), width );
        assertEquals( TestHelpers.accessDouble( proscenium, "depth" ), depth );
        assertEquals( TestHelpers.accessDouble( proscenium, "height" ), height );
        assertEquals( TestHelpers.accessDouble( proscenium, "x" ), x );
        assertEquals( TestHelpers.accessDouble( proscenium, "y" ), y );
        assertEquals( TestHelpers.accessDouble( proscenium, "z" ), z );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        assertNull( TestHelpers.accessString( proscenium, "id" ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( proscenium );
    }

    @Test
    public void active() throws Exception {
        new Proscenium( element );
        assertTrue( Proscenium.Active() );
    }

    @Test
    public void activeNoProscenium() throws Exception {
        assertFalse( Proscenium.Active() );
    }

    @Test
    public void origin() throws Exception {
        new Proscenium( element );

        assertEquals( Proscenium.Origin(), new Point( 250.0, 144.0, 12.0 ) );
    }

    @Test
    public void originNoProscenium() throws Exception {
        assertEquals( Proscenium.Origin(), new Point( 0.0, 0.0, 0.0 ) );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'width' attribute.")
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'depth' attribute.")
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'height' attribute.")
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'z' attribute.")
    public void noZ() throws Exception {
        element.removeAttribute( "z" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeWidth() throws Exception {
        element.setAttribute( "width", "600" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeDepth() throws Exception {
        element.setAttribute( "depth", "257" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeHeight() throws Exception {
        element.setAttribute( "height", "252" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "436" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "379" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeZ() throws Exception {
        element.setAttribute( "z", "14" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should have a positive width.")
    public void tooSmallWidth() throws Exception {
        element.setAttribute( "width", "0" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should have a positive depth.")
    public void tooSmallDepth() throws Exception {
        element.setAttribute( "depth", "0" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should have a positive height.")
    public void tooSmallHeight() throws Exception {
        element.setAttribute( "height", "0" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "114" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooSmallZ() throws Exception {
        element.setAttribute( "z", "-1" );
        new Proscenium( element );
    }

    @Test
    public void creationUpdatesMinimumZ() throws Exception {
        assertEquals( Point.SmallZ(), 0.0 );

        new Proscenium( element );

        assertEquals( Point.SmallZ(), -z );
    }

    @Test
    public void locateOrigin() throws Exception {
        new Proscenium( element );
        Point origin = new Point( 0.0, 0.0, 0.0 );

        Point fixed = Proscenium.LocateIfActive( origin );

        Point expected = new Point( 250.0, 144.0, 12.0 );
        assertEquals( fixed, expected );

        assert new Point( 250.0, 144.0, 12.0 ).equals( fixed );
    }

    @Test
    public void locateNoProsceniumOrigin() throws Exception {
        Point origin = new Point( 0.0, 0.0, 0.0 );

        Point fixed = Proscenium.LocateIfActive( origin );

        assertEquals( fixed, origin );
    }

    @Test
    public void locateUSRHigh() throws Exception {
        new Proscenium( element );
        Point fixed = Proscenium.LocateIfActive( new Point( 100.0, 120.0, 60.0 ) );
//        assert new Point( 150, 24, 72 ).equals( fixed );
        assertEquals( fixed.x(), 350.0, "X" );
        assertEquals( fixed.y(), 24.0, "Y" );
        assertEquals( fixed.z(), 72.0, "Z" );
    }

    @Test
    public void locateNoProsceniumUSRHigh() throws Exception {
        Point initial = new Point( 100.0, 120.0, 60.0 );

        Point fixed = Proscenium.LocateIfActive( initial );

        assertEquals( fixed, initial );
    }

    @Test
    public void locateOriginPathString() throws Exception {
        new Proscenium( element );

        String fixed = Proscenium.LocateIfActivePathString( 0.0, 0.0 );

        assertEquals( fixed, "250.0 144.0 " );
    }

    @Test
    public void locateNoProsceniumOriginPathString() throws Exception {
        String fixed = Proscenium.LocateIfActivePathString( 0.0, 0.0 );

        assertEquals( fixed, "0.0 0.0 " );
    }

    @Test
    public void locateUSRHighPathString() throws Exception {
        new Proscenium( element );

        String fixed = Proscenium.LocateIfActivePathString( 100.0, 120.0 );

        assertEquals( fixed, "350.0 24.0 " );
    }

    @Test
    public void locateNoProsceniumUSRHighPathString() throws Exception {
        String fixed = Proscenium.LocateIfActivePathString( 100.0, 120.0 );

        assertEquals( fixed, "100.0 120.0 " );
    }
//
//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        // TODO Takes too long
////        new Parse( stream );
//
//        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals( list.size(), 1 );
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//          expectedExceptionsMessageRegExp =
//                  "Multiple Prosceniums are not currently supported.")
//    public void parseMultiple() throws Exception {
//        String xml = "<plot>" +
//                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
//                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        // TODO Takes too long
////        new Parse( stream );
//
//        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals( list.size(), 2 );
//    }

//    @Mocked
//    Graphics2D mockCanvas;

//    @Test
//    public void draw() throws Exception {
//        Proscenium proscenium = new Proscenium( baseElement );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.orange );
//                mockCanvas.draw( new Rectangle( 56, 16, 288, 144 ) );
//            }
//        };
//        proscenium.drawPlan( mockCanvas );
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        Proscenium proscenium = new Proscenium( baseElement );
//
//        proscenium.dom( null );
//    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Proscenium proscenium = new Proscenium( element );

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        proscenium.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "85.0" );
        assertEquals( element.getAttribute( "y1" ), "144.0" );
        assertEquals( element.getAttribute( "x2" ), "85.0" );
        assertEquals( element.getAttribute( "y2" ), "166.0" );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.Color );
        assertEquals( element.getAttribute( "stroke-opacity" ), "" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "415.0" );
        assertEquals( element.getAttribute( "y1" ), "144.0" );
        assertEquals( element.getAttribute( "x2" ), "415.0" );
        assertEquals( element.getAttribute( "y2" ), "166.0" );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.Color );
        assertEquals( element.getAttribute( "stroke-opacity" ), "" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "85.0" );
        assertEquals( element.getAttribute( "y1" ), "144.0" );
        assertEquals( element.getAttribute( "x2" ), "415.0" );
        assertEquals( element.getAttribute( "y2" ), "144.0" );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.FadedColor );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.3" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 3 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "85.0" );
        assertEquals( element.getAttribute( "y1" ), "166.0" );
        assertEquals( element.getAttribute( "x2" ), "415.0" );
        assertEquals( element.getAttribute( "y2" ), "166.0" );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.FadedColor );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.1" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        assertEquals( list.getLength(), 4 );

    }

    @Test
    public void domSection() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Proscenium proscenium = new Proscenium( element );

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        proscenium.dom( draw, View.SECTION );

        NodeList list = draw.root().getElementsByTagName( "line" );

        // Y values are inverted.
        Double floor = Venue.Height();
        Double ceiling = floor - Venue.Height();

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), y.toString() );
        assertEquals( element.getAttribute( "y1" ), floor.toString() );
        assertEquals( element.getAttribute( "x2" ), y.toString() );
        assertEquals( element.getAttribute( "y2" ), ceiling.toString() );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.FadedColor );
//        assertEquals( element.getAttribute( "stroke-opacity" ), "" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        Double frontWall = y + depth;
        assertEquals( element.getAttribute( "x1" ), frontWall.toString() );
        assertEquals( element.getAttribute( "y1" ), floor.toString() );
        assertEquals( element.getAttribute( "x2" ), frontWall.toString() );
        assertEquals( element.getAttribute( "y2" ), ceiling.toString() );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.FadedColor );
//        assertEquals( element.getAttribute( "stroke-opacity" ), "" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        Double archTop = floor - height;
        assertEquals( element.getAttribute( "x1" ), y.toString() );
        assertEquals( element.getAttribute( "y1" ), archTop.toString() );
        assertEquals( element.getAttribute( "x2" ), frontWall.toString() );
        assertEquals( element.getAttribute( "y2" ), archTop.toString() );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.FadedColor );
//        assertEquals( element.getAttribute( "stroke-opacity" ), "0.3" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 3 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "0.0" );
        assertEquals( element.getAttribute( "y1" ), floor.toString() );
        assertEquals( element.getAttribute( "x2" ), y.toString() );
        assertEquals( element.getAttribute( "y2" ), floor.toString() );
        assertEquals( element.getAttribute( "stroke" ), Proscenium.StageColor );
//        assertEquals( element.getAttribute( "stroke-opacity" ), "0.1" );
        assertEquals( element.getAttribute( "stroke-width" ), "2" );

        assertEquals( list.getLength(), 4 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}