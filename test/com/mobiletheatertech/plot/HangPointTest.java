package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Test {@code HangPoint }
 *
 * @author dhs
 * @since 0.0.4
 */
public class HangPointTest {

    Element element = null;

    Double x = 296.0;
    Double y = 320.0;

    public HangPointTest() {
    }

    @Test
    public void isA() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        assert MinderDom.class.isInstance( hangPoint );
    }

    @Test
    public void isLegendable() throws Exception {
        HangPoint hangPoint = new HangPoint( element );
        assert Legendable.class.isInstance( hangPoint );
    }

    @Test
    public void storesAttributes() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        assertEquals( TestHelpers.accessString( hangPoint, "id" ), "Blather" );
        assertEquals( TestHelpers.accessDouble( hangPoint, "x" ), x );
        assertEquals( TestHelpers.accessDouble( hangPoint, "y" ), y );
    }

    @Test
    public void storesSelf() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( hangPoint );
    }

    @Test
    public void registersLayer() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        HashMap<String, Layer> layers = Layer.List();

        assertTrue( layers.containsKey( HangPoint.LAYERTAG ) );
        assertEquals( layers.get( HangPoint.LAYERTAG ).name(), HangPoint.LAYERNAME );
    }

    @Test
    public void finds() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

    @Test
    public void findsNothing() throws Exception {
        HangPoint found = HangPoint.Find( "Nothing" );

        assertNull( found );
    }

    @Test
    public void findIgnoresOther() throws Exception {
        Element pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute( "id", "pipe id" );
        pipeElement.setAttribute( "x", "2" );
        pipeElement.setAttribute( "y", "4" );
        pipeElement.setAttribute( "z", "6" );
        new Pipe( pipeElement );
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

    @Test
    public void findIgnoresOtherHangPoint() throws Exception {
        Element hangpointElement = new IIOMetadataNode( "hangpoint" );
        hangpointElement.setAttribute( "id", "Not Our Victim" );
        hangpointElement.setAttribute( "x", "2" );
        hangpointElement.setAttribute( "y", "4" );
        new HangPoint( hangpointElement );
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( HangPoint.Tag ) );
//
//        new HangPoint( element );
//
//        assertNotNull( Category.Select( HangPoint.Tag ) );
//    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new HangPoint( element );
    }

    /**
     * The lack of an id attribute is not an error.
     *
     * @throws Exception
     */
    @Test
    public void noUnused() throws Exception {
        element.removeAttribute( "id" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint \\(Blather\\) is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint instance is missing required 'x' attribute.")
    public void noXWithoutID() throws Exception {
        element.removeAttribute( "id" );
        element.removeAttribute( "x" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint \\(Blather\\) is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint instance is missing required 'y' attribute.")
    public void noYWithoutID() throws Exception {
        element.removeAttribute( "id" );
        element.removeAttribute( "y" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint x value outside boundary of the venue")
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "351" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint y value outside boundary of the venue")
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "401" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint x value outside boundary of the venue")
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "-1" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint y value outside boundary of the venue")
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new HangPoint( element );
    }


    @Test
    public void parseTwoHangPoints() throws Exception {
        String xml = "<plot>" +
                "<hangpoint x=\"20\" y=\"30\" />" +
                "<hangpoint x=\"25\" y=\"35\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        // TODO Takes too long
        new Parse( stream );

        // Final size of list
        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 2 );

        ElementalLister hangpoint = list.get( 0 );
        assert MinderDom.class.isInstance( hangpoint );
        assert HangPoint.class.isInstance( hangpoint );

        ElementalLister hangpoint2 = list.get( 1 );
        assert MinderDom.class.isInstance( hangpoint2 );
        assert HangPoint.class.isInstance( hangpoint2 );

        assertNotSame( hangpoint, hangpoint2 );
    }

    @Test
    public void locate() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        Point location = hangPoint.locate();
        assertEquals( location.x(), x );
        assertEquals( location.y(), y );
        assertEquals( location.z(), 240.0 );
    }

//    @Mocked
//    Graphics2D mockCanvas;
//
//    @Test
//    public void drawPlan() throws Exception {
//        HangPoint hangPoint = new HangPoint( baseElement );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.BLUE );
//                mockCanvas.draw( new Line2D.Float( 294, 318, 298, 322 ) );
//                mockCanvas.draw( new Line2D.Float( 298, 318, 294, 322 ) );
//            }
//        };
//        hangPoint.drawPlan( mockCanvas );
//    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        HangPoint hangPoint = new HangPoint( element );

        hangPoint.dom( draw, View.PLAN );

//        NodeList list = draw.root().getElementsByTagName( "use" );
        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), HangPoint.LAYERTAG );

        NodeList list = groupElement.getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#hangpoint" );
    }

//    @Test
//    public void domUnused() throws Exception {
//        HangPoint hangPoint = new HangPoint( baseElement );
//
//        hangPoint.dom( null, View.PLAN );
//    }

    @Test
    public void domLegendItem() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        HangPoint hangPoint = new HangPoint(element);
        hangPoint.verify();

        NodeList preGroup = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroup.getLength(), 1 );

        PagePoint startPoint = new PagePoint( 20.0, 10.0 );
        PagePoint endPoint = hangPoint.domLegendItem( draw, startPoint );

//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 1 );
//        Node groupNod = group.item(0);
//        Element groupElem = (Element) groupNod;

//        NodeList groupList = draw.root().getElementsByTagName( "g" );
//        assertEquals( groupList.getLength(), 2 );
//        Node groupNode = groupList.item(1);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element groupElement = (Element) groupNode;

        NodeList useList = draw.root().getElementsByTagName( "use" );
        assertEquals( useList.getLength(), 1 );
        Node useNode = useList.item(0);
        assertEquals(useNode.getNodeType(), Node.ELEMENT_NODE);
        Element useElement = (Element) useNode;
        Double startX = startPoint.x() + HangPoint.RADIUS;
        Double startY = startPoint.y() + HangPoint.RADIUS;
        Integer endX = startPoint.x().intValue() + 12;
        assertEquals(useElement.getAttribute("x"), startX.toString() );
        assertEquals(useElement.getAttribute("y"), startY.toString());
        assertEquals(useElement.getAttribute("xlink:href"), "#"+HangPoint.SYMBOL );

        NodeList textList = draw.root().getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 1 );
        Node textNode = textList.item(0);
        assertEquals(textNode.getNodeType(), Node.ELEMENT_NODE);
        Element textElement = (Element) textNode;
        Double x = startPoint.x() + 20;
        Double y = startPoint.y() + 3;
        assertEquals(textElement.getAttribute("x"), x.toString() );
        assertEquals(textElement.getAttribute("y"), y.toString() );
        assertEquals(textElement.getAttribute("fill"), "black" );

        // TODO Check for text here

        assertEquals( endPoint, new PagePoint( startPoint.x(), startPoint.y() + 7 ));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Venue.Reset();
        TestResets.MinderDomReset();
        TestResets.LayerReset();
        TestResets.ElementalListerReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "hangpoint" );
        element.setAttribute( "id", "Blather" );
        element.setAttribute( "x", x.toString() );
        element.setAttribute( "y", y.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}