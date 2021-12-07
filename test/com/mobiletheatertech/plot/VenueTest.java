package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.util.ArrayList;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Test {@code Venue}.
 *
 * @author dhs
 * @since 0.0.2
 */
public class VenueTest {

    Element element = null;
    String building = "Building Name";
    String room = "Room Name";

    Double width = 1296.0;
    Double depth = 1320.0;
    Double height = 240.0;

    private Element prosceniumElement = null;
    private Integer prosceniumX = 600;
    private Integer prosceniumY = 144;
    private Integer prosceniumZ = 12;

    private Element pipeCrossesProsceniumCenterElement = null;
    Double longLength = 360.0;
    Double negativeX = -180.0;

    Element pipeElement = null;
    Double x = 92.0;
    Double y = 83.0;
    Double z = 74.0;
    String pipeId = "pipe id";

    Element racewayElement = null;
    Double startx = 1.1;
    Double starty = 1.2;
    Double startz = 1.3;
    Double endx = 2.1;
    Double endy = 2.2;
    Double endz = 2.3;


    @BeforeMethod
    public void setUpMethod() throws Exception {
        Venue.Reset();
        TestResets.ElementalListerReset();
        UniqueId.Reset();
        Proscenium.Reset();

        element = new IIOMetadataNode();
        element.setAttribute( "building", building );
        element.setAttribute( "room", room );
        element.setAttribute( "width", width.toString() );
        element.setAttribute( "depth", depth.toString() );
        element.setAttribute( "height", height.toString() );

        prosceniumElement = new IIOMetadataNode("proscenium");
        prosceniumElement.setAttribute("width", "260");
        prosceniumElement.setAttribute("height", "200");
        prosceniumElement.setAttribute("depth", "22");
        prosceniumElement.setAttribute("x", prosceniumX.toString());
        prosceniumElement.setAttribute("y", prosceniumY.toString());
        prosceniumElement.setAttribute("z", prosceniumZ.toString());

        pipeCrossesProsceniumCenterElement = new IIOMetadataNode("pipe");
        pipeCrossesProsceniumCenterElement.setAttribute("id", pipeId);
        pipeCrossesProsceniumCenterElement.setAttribute("length", longLength.toString());
        pipeCrossesProsceniumCenterElement.setAttribute("x", negativeX.toString());
        pipeCrossesProsceniumCenterElement.setAttribute("y", y.toString());
        pipeCrossesProsceniumCenterElement.setAttribute( "z", z.toString());

        pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "id", pipeId);
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute("x", x.toString());
        pipeElement.setAttribute("y", y.toString());
        pipeElement.setAttribute("z", z.toString());

        racewayElement = new IIOMetadataNode( Raceway.Tag() );
        racewayElement.setAttribute( "startx", startx.toString() );
        racewayElement.setAttribute( "starty", starty.toString() );
        racewayElement.setAttribute( "startz", startz.toString() );
        racewayElement.setAttribute( "endx", endx.toString() );
        racewayElement.setAttribute( "endy", endy.toString() );
        racewayElement.setAttribute( "endz", endz.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }


    @Test
    public void isA() throws Exception {
        Venue instance = new Venue( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertFalse( UniqueId.class.isInstance( instance ) );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assertFalse( LinearSupportsClamp.class.isInstance( instance ) );
        assertTrue( Populate.class.isInstance( instance ) );
        assertTrue( Legendable.class.isInstance( instance ) );
//        assert Schematicable.class.isInstance( instance );
    }

    @Test
    public void constantTag() throws Exception {
        assertEquals( Venue$.MODULE$.Tag(), "venue" );
    }

    @Test
    public void constantOneToOne() throws Exception {
        assertEquals( Venue$.MODULE$.ONETOONE(), "one-to-one" );
    }

    @Test
    public void constantOneToMany() throws Exception {
        assertEquals( Venue$.MODULE$.ONETOMANY(), "one-to-many" );
    }

    @Test
    public void storesAttributes() throws Exception {
//        baseElement.removeAttribute( "circuiting" );
        element.removeAttribute( "building" );

        Venue venue = new Venue( element );

        assertEquals( TestHelpers.accessString( venue, "building" ), "" );
        assertEquals( TestHelpers.accessString( venue, "room" ), room );
        assertEquals( TestHelpers.accessDouble(venue, "width"), (Double)1296.0 );
        assertEquals( TestHelpers.accessDouble(venue, "depth"), (Double)1320.0 );
        assertEquals( TestHelpers.accessDouble(venue, "height"), (Double)240.0 );
        assertEquals( TestHelpers.accessString( venue, "circuiting" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "circuiting", "one-to-one" );

        Venue venue = new Venue( element );

        assertEquals( TestHelpers.accessString( venue, "building" ), building );
        assertEquals( TestHelpers.accessString( venue, "room" ), room );
        assertEquals( TestHelpers.accessDouble(venue, "width"), (Double)1296.0 );
        assertEquals( TestHelpers.accessDouble(venue, "depth"), (Double)1320.0 );
        assertEquals( TestHelpers.accessDouble( venue, "height" ), (Double)240.0 );
        assertEquals( TestHelpers.accessString( venue, "circuiting" ), "one-to-one" );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Venue venue = new Venue( element );

        assertNull( TestHelpers.accessString( venue, "id" ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Venue venue = new Venue( element );
        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( venue );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueContains() throws Exception {
        Point point = new Point( 1, 2, 3 );
        Space space = new Space( point, 4, 5, 6 );
        Venue.Contains( space );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueContains2Dboolean() throws Exception {
        Rectangle rectangle = new Rectangle( 1, 2, 3, 4 );
        Venue.Contains2D( rectangle );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueContains2Dint() throws Exception {
        Venue.Contains2D( 1, 2 );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueName() throws Exception {
        Venue.Name();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueWidth() throws Exception {
        Venue.Width();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueDepth() throws Exception {
        Venue.Depth();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueHeight() throws Exception {
        Venue.Height();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueCircuiting() throws Exception {
        Venue.Circuiting();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueBuilding() throws Exception {
        Venue.Building();
    }

    @Test
    public void storesExtremePoints() throws Exception {

        TestResets.PointReset();

        new Venue( element );

        assertEquals( Point.LargeX(), (Double)1296.0 );
        assertEquals( Point.LargeY(), (Double)1320.0 );
        assertEquals( Point.LargeZ(), (Double)240.0 );
        assertEquals( Point.SmallX(), (Double)0.0 );
        assertEquals( Point.SmallY(), (Double)0.0 );
        assertEquals( Point.SmallZ(), (Double)0.0 );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'room' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "room" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'width' attribute." )
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'depth' attribute." )
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'height' attribute." )
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Venue( element );
    }

    @Test
    public void containsRectangle() throws Exception {
        new Venue( element );

        assert Venue.Contains2D( new Rectangle( 0, 0, 1296, 1320 ) );
        assert Venue.Contains2D( new Rectangle( 1, 1, 1295, 1319 ) );
        assert !Venue.Contains2D( new Rectangle( -1, -1, 1296, 1320 ) );
    }

    @Test
    public void containsCoordinate() throws Exception {
        new Venue( element );

        assertEquals( Venue.Contains2D( 1295, 1319 ), 0 );
        assertEquals( Venue.Contains2D( 1, 1 ), 0 );
        assertEquals( Venue.Contains2D( 0, 0 ), 0 );
        assertEquals( Venue.Contains2D( -1, 1 ), Rectangle.OUT_LEFT );
        assertEquals( Venue.Contains2D( 1, -1 ), Rectangle.OUT_TOP );
        assertEquals( Venue.Contains2D( 1297, 1320 ), Rectangle.OUT_RIGHT );
        assertEquals( Venue.Contains2D( 1296, 1321 ), Rectangle.OUT_BOTTOM );
    }

    @Test
    public void containsBoxFits() throws Exception {
        new Venue( element );
        Point point = new Point( 2, 4, 6 );
        Space space = new Space( point, 33, 55, 11 );

        assertTrue( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxTooWide() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Space space = new Space( point, 1296, 55, 11 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxTooDeep() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Space space = new Space( point, 33, 1320, 11 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxTooTall() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Space space = new Space( point, 33, 55, 240 );

        assertFalse( Venue.Contains( space ) );
    }


    @Test
    public void containsBoxOriginXTooSmall() throws Exception {
        new Venue( element );
        Point point = new Point( -1, 1, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxOriginXTooLarge() throws Exception {
        new Venue( element );
        Point point = new Point( 1297, 1, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxOriginYTooSmall() throws Exception {
        new Venue( element );
        Point point = new Point( 1, -1, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxOriginYTooLarge() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1321, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxOriginZTooSmall() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, -1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void containsBoxOriginZTooLarge() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 241 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( Venue.Contains( space ) );
    }

    @Test
    public void height() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Height(), 24.0 );

        new Venue( element );

        assertEquals( Venue.Height(), 240.0 );
    }

    @Test
    public void width() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Width(), 129.0 );

        new Venue( element );

        assertEquals( Venue.Width(), 1296.0 );
    }

    @Test
    public void depth() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Depth(), 132.0 );

        new Venue( element );

        assertEquals( Venue.Depth(), 1320.0 );
    }

    @Test
    public void room() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Name(), "Venue Name" );

        new Venue( element );

        assertEquals( Venue.Name(), room );
    }

    @Test
    public void building() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "building", building );
        venueElement.setAttribute( "room", room );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Building(), building );

        element.removeAttribute( "building" );

        new Venue( element );

        assertEquals( Venue.Building(), "" );
    }

    @Test
    public void circuiting() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", room );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );
        venueElement.setAttribute( "circuiting", "one-to-many" );

        new Venue( venueElement );

        assertEquals( Venue.Circuiting(), "one-to-many" );

        new Venue( element );

        assertEquals( Venue.Circuiting(), "" );
    }

    @Test
    public void circuitingOne() throws Exception {
        element.setAttribute( "circuiting", "one-to-one" );
        new Venue( element );

        assertEquals( Venue.Circuiting(), "one-to-one" );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp = "'circuiting' attribute invalid." )
    public void circuitingInvalid() throws Exception {
        element.setAttribute( "circuiting", "bogus" );
        new Venue( element );
    }

//    @Mocked
//    Graphics2D mockCanvas;
//
//    @Test
//    public void drawPlan() throws Exception {
//        Venue venue = new Venue( element );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.BLACK );
//                mockCanvas.draw( new Rectangle( 0, 0, 1296, 1320 ) );
//            }
//        };
//        venue.drawPlan( mockCanvas );
//    }
//
//    @Test
//    public void drawSection() throws Exception {
//        Venue venue = new Venue( element );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.BLACK );
//                mockCanvas.draw( new Rectangle( 0, 0, 1320, 240 ) );
//            }
//        };
//        venue.drawSection( mockCanvas );
//    }
//
//    @Test
//    public void drawFront() throws Exception {
//        Venue venue = new Venue( element );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.BLACK );
//                mockCanvas.draw( new Rectangle( 0, 0, 1296, 240 ) );
//            }
//        };
//        venue.drawFront( mockCanvas );
//    }


    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Venue venue = new Venue( element );

        NodeList existingChildren = draw.root().getChildNodes();//  getElementsByTagName("rect");
        assertEquals( existingChildren.getLength(), 3 );

        NodeList existingRectangles = draw.root().getElementsByTagName("rect");
        assertEquals(existingRectangles.getLength(), 0);

        venue.dom( draw, View.PLAN );

        NodeList children = draw.root().getChildNodes();
        assertEquals( children.getLength(), 4 );

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element tableElement = (Element) groupNode;
        assertEquals(tableElement.getAttribute("x"), "0.0" );
        assertEquals(tableElement.getAttribute("y"), "0.0" );
        assertEquals(tableElement.getAttribute("width"), width.toString() );
        // Plot attribute is 'depth'. SVG attribute is 'height'.
        assertEquals(tableElement.getAttribute("height"), depth.toString() );
        assertEquals(tableElement.getAttribute("fill"), "none");
        assertEquals(tableElement.getAttribute("stroke"), "black");
    }

//    @Test
//    public void domFront() throws Exception {
//        Draw draw = new Draw();
//
//        draw.establishRoot();
//        Venue venue = new Venue( element );
//
//        NodeList existingRectangles = draw.root().getElementsByTagName("rect");
//        assertEquals(existingRectangles.getLength(), 0);
//
//        venue.dom(draw, View.FRONT);
//
//        NodeList rectangles = draw.root().getElementsByTagName("rect");
//        assertEquals(rectangles.getLength(), 1);
//        Node groupNode = rectangles.item(0);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element tableElement = (Element) groupNode;
//        assertEquals(tableElement.getAttribute("x"), "0.0" );
//        assertEquals(tableElement.getAttribute("y"), "0.0" );
//        assertEquals(tableElement.getAttribute("width"), width.toString() );
//        // Plot attribute is 'depth'. SVG attribute is 'height'.
//        assertEquals(tableElement.getAttribute("height"), height.toString() );
//        assertEquals(tableElement.getAttribute("fill"), "none");
//        assertEquals(tableElement.getAttribute("stroke"), "black");
//    }

    @Test
    public void domSection() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Venue venue = new Venue( element );

        NodeList existingRectangles = draw.root().getElementsByTagName("rect");
        assertEquals(existingRectangles.getLength(), 0);

        venue.dom(draw, View.SECTION);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element tableElement = (Element) groupNode;
        assertEquals(tableElement.getAttribute("x"), "0.0" );
        assertEquals(tableElement.getAttribute("y"), "0.0" );
        assertEquals(tableElement.getAttribute("width"), depth.toString() );
        Double tallness = height - Point.SmallZ();
        assertEquals(tableElement.getAttribute("height"), tallness.toString() );
        assertEquals(tableElement.getAttribute("fill"), "none");
        assertEquals(tableElement.getAttribute("stroke"), "black");
    }


    @Test
    public void tagCallbackRegisteredPipe() {
        Venue venue = new Venue( element );

        assertTrue( venue.populateTags().contains( Pipe.LayerTag() ) );
        assertEquals( venue.populateTags().size(), 3 );
    }

    @Test
    public void tagCallbackRegisteredRaceway() {
        Venue venue = new Venue( element );

        assertTrue( venue.populateTags().contains( Raceway.Tag() ) );
        assertEquals( venue.populateTags().size(), 3 );
    }

    @Test
    public void tagCallbackRegisteredProscenium() {
        Venue venue = new Venue( element );

        assertTrue( venue.populateTags().contains( Proscenium.Tag() ) );
        assertEquals( venue.populateTags().size(), 3 );
    }

    @Test
    public void populateChildPipe() {
        element.appendChild( pipeElement );
        new Venue( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venue = list.get( 0 );
        assert MinderDom.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        ElementalLister pipe = list.get( 1 );
        assert MinderDom.class.isInstance( pipe );
        assert Pipe.class.isInstance( pipe );

        assertEquals( list.size(), 2 );
    }

    @Test
    public void populateChildRaceway() {
        element.appendChild( racewayElement );
        new Venue( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venue = list.get( 0 );
        assert MinderDom.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        ElementalLister raceway = list.get( 1 );
        assert MinderDom.class.isInstance( raceway );
        assert Raceway.class.isInstance( raceway );

        assertEquals( list.size(), 2 );
    }

    @Test
    public void populateChildPipeWithProscenium() {
        element.appendChild( prosceniumElement );
        element.appendChild( pipeCrossesProsceniumCenterElement );
        new Venue( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venue = list.get( 0 );
        assert MinderDom.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        ElementalLister proscenium = list.get( 1 );
        assert MinderDom.class.isInstance( proscenium );
        assert Proscenium.class.isInstance( proscenium );

        ElementalLister pipe = list.get( 2 );
        assert MinderDom.class.isInstance( pipe );
        assert Pipe.class.isInstance( pipe );

        assertEquals( list.size(), 3 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}