//package com.mobiletheatertech.plot;
//
//import mockit.Mocked;
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.awt.*;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNull;
//
///**
// * Created with IntelliJ IDEA. User: dhs Date: 11/28/13 Time: 5:32 PM To change this template use
// * File | Settings | File Templates.
// */
//
///**
// * Test {@code ScaffoldTower}.
// *
// * @author dhs
// * @since 0.0.3
// */
//public class ScaffoldTowerTest {
//
//    Element element = null;
//    Element elementP = null;
//
//    public ScaffoldTowerTest() {
//    }
//
//    @Test
//    public void isMinderDom() throws Exception {
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        assert MinderDom.class.isInstance(scaffoldTower);
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        assertEquals(TestHelpers.accessDouble(scaffoldTower, "width"), 288.0);
//        assertEquals(TestHelpers.accessDouble(scaffoldTower, "depth"), 144.0);
//        assertEquals(TestHelpers.accessDouble(scaffoldTower, "x"), 56.0);
//        assertEquals(TestHelpers.accessDouble(scaffoldTower, "y"), 16.0);
//        assertEquals(TestHelpers.accessDouble(scaffoldTower, "z"), 16.0);
//        assertEquals(TestHelpers.accessDouble(scaffoldTower, "height"), 12.0);
//    }
//
//    // Until such time as I properly implement this class' use of id.
//    @Test
//    public void idUnused() throws Exception {
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        assertEquals(TestHelpers.accessString(scaffoldTower, "id"), "");
//    }
//
//    @Test
//    public void storesSelf() throws Exception {
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        ArrayList<ElementalLister> thing = ElementalLister.List();
//
//        assert thing.contains(scaffoldTower);
//    }
//
//    /*
//     * This is to ensure that no exception is thrown if data is OK.
//     */
//    @Test
//    public void justFine() throws Exception {
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "ScaffoldTower instance is missing required 'width' attribute.")
//    public void noWidth() throws Exception {
//        element.removeAttribute("width");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "ScaffoldTower instance is missing required 'depth' attribute.")
//    public void noDepth() throws Exception {
//        element.removeAttribute("depth");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "ScaffoldTower instance is missing required 'x' attribute.")
//    public void noX() throws Exception {
//        element.removeAttribute("x");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "ScaffoldTower instance is missing required 'y' attribute.")
//    public void noY() throws Exception {
//        element.removeAttribute("y");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "ScaffoldTower instance is missing required 'z' attribute.")
//    public void noZ() throws Exception {
//        element.removeAttribute("z");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "ScaffoldTower instance is missing required 'height' attribute.")
//    public void noHeight() throws Exception {
//        element.removeAttribute("height");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooLargeWidth() throws Exception {
//        element.setAttribute("width", "495");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooLargeDepth() throws Exception {
//        element.setAttribute("depth", "401");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooLargeX() throws Exception {
//        element.setAttribute("x", "263");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooLargeY() throws Exception {
//        element.setAttribute("y", "401");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooLargeZ() throws Exception {
//        element.setAttribute("z", "229");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooLargeHeight() throws Exception {
//        element.setAttribute("height", "225");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = SizeException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should have a positive width.")
//    public void tooSmallWidth() throws Exception {
//        element.setAttribute("width", "-1");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = SizeException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should have a positive depth.")
//    public void tooSmallDepth() throws Exception {
//        element.setAttribute("depth", "-1");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = SizeException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should have a positive height.")
//    public void tooSmallHeight() throws Exception {
//        element.setAttribute("height", "-1");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooSmallX() throws Exception {
//        element.setAttribute("x", "-1");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooSmallY() throws Exception {
//        element.setAttribute("y", "-1");
//        new ScaffoldTower(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "ScaffoldTower should not extend beyond the boundaries of the venue.")
//    public void tooSmallZ() throws Exception {
//        element.setAttribute("z", "-1");
//        new ScaffoldTower(element);
//    }
//
//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<scaffoldTower width=\"72\" depth=\"30\" x=\"3\" y=\"6\" z=\"0\" height=\"36\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream(xml.getBytes());
//
//        TestResets.MinderDomReset();
//
//        // TODO Takes too long
//        new Parse(stream);
//
//        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals(list.size(), 1);
//    }
//
//    @Test
//    public void parseMultiple() throws Exception {
//        String xml = "<plot>" +
//                "<scaffoldTower width=\"72\" depth=\"30\" x=\"3\" y=\"6\" z=\"0\" height=\"36\" />" +
//                "<scaffoldTower width=\"72\" depth=\"30\" x=\"3\" y=\"6\" z=\"0\" height=\"36\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream(xml.getBytes());
//
//        TestResets.MinderDomReset();
//
//        // TODO Takes too long
//        new Parse(stream);
//
//        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals(list.size(), 2);
//    }
//
//    @Mocked
//    Graphics2D mockCanvas;
//
////    @Test
////    public void draw() throws Exception {
////        ScaffoldTower scaffoldTower = new ScaffoldTower( baseElement );
////
////        new Expectations() {
////            {
////                mockCanvas.setPaint( Color.orange );
////                mockCanvas.draw( new Rectangle( 56, 16, 288, 144 ) );
////            }
////        };
////        scaffoldTower.drawPlan( mockCanvas );
////    }
//
//    @Test
//    public void domPlan() throws Exception {
//        Draw draw = new Draw();
//
//        draw.establishRoot();
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        NodeList existingGroups = draw.root().getElementsByTagName("rect");
//        assertEquals(existingGroups.getLength(), 0);
//
//        scaffoldTower.dom(draw, View.PLAN);
//
//
////        NodeList createdGroups = draw.root().getElementsByTagName( "g" );
////        assertEquals( createdGroups.getLength(), 2 );
//
//        NodeList rectangles = draw.root().getElementsByTagName("rect");
//        assertEquals(rectangles.getLength(), 1);
//        Node groupNode = rectangles.item(0);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element scaffoldTowerElement = (Element) groupNode;
//        assertEquals(scaffoldTowerElement.getAttribute("class"), ScaffoldTower.LAYERTAG);
//        assertEquals(scaffoldTowerElement.getAttribute("x"), "56.0");
//        assertEquals(scaffoldTowerElement.getAttribute("y"), "16.0");
//        assertEquals(scaffoldTowerElement.getAttribute("width"), "288.0");
//        assertEquals(scaffoldTowerElement.getAttribute("height"), "144.0");
//        assertEquals(scaffoldTowerElement.getAttribute("fill"), "none");
//        assertEquals(scaffoldTowerElement.getAttribute("stroke"), "brown");
//
////        int CHAIRCOUNT = (WIDTH / CHAIRWIDTH) * (DEPTH / (CHAIRDEPTH + FOOTSPACE));
////        String expectedX = Integer.toString( X + CHAIRWIDTH / 2 + 2 );
////        String expectedY = Integer.toString( Y + CHAIRDEPTH / 2 + FOOTSPACE );
////        assert (CHAIRCOUNT > 0);
////
////        NodeList list = groupElement.getElementsByTagName( "use" );
////        assertEquals( list.getLength(), CHAIRCOUNT );
////
////        Node node = list.item( 0 );
////        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        Element baseElement = (Element) node;
////        assertEquals( baseElement.attribute( "xlink:href" ), "#chair" );
////        assertEquals( baseElement.attribute( "x" ), expectedX );
////        assertEquals( baseElement.attribute( "y" ), expectedY );
//    }
//
//    @Test
//    public void domSectionUndrawn() throws Exception {
//        Draw draw = new Draw();
//
//        draw.establishRoot();
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        NodeList existingGroups = draw.root().getElementsByTagName("rect");
//        assertEquals(existingGroups.getLength(), 0);
//
//        scaffoldTower.dom(draw, View.SECTION);
//
//        NodeList rectangles = draw.root().getElementsByTagName("rect");
//        assertEquals(rectangles.getLength(), 0);
//    }
//
//    @Test
//    public void domFrontUndrawn() throws Exception {
//        Draw draw = new Draw();
//
//        draw.establishRoot();
//        ScaffoldTower scaffoldTower = new ScaffoldTower(element);
//
//        NodeList existingGroups = draw.root().getElementsByTagName("rect");
//        assertEquals(existingGroups.getLength(), 0);
//
//        scaffoldTower.dom(draw, View.FRONT);
//
//        NodeList rectangles = draw.root().getElementsByTagName("rect");
//        assertEquals(rectangles.getLength(), 0);
//    }
//
////    @Test
////    public void scaffoldTowersCreatesMultiple() {
//////        fail( "ScaffoldTower does not yet support multiples" );
////        throw new SkipException( "ScaffoldTower does not yet support multiples" );
////    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @BeforeMethod
//    public void setUpMethod() throws Exception {
//        Element venueElement = new IIOMetadataNode();
//        venueElement.setAttribute("room", "Test Name");
//        venueElement.setAttribute("width", "550");
//        venueElement.setAttribute("depth", "400");
//        venueElement.setAttribute("height", "240");
//        new Venue(venueElement);
//
//        element = new IIOMetadataNode("scaffoldTower");
//        element.setAttribute("width", "288");
//        element.setAttribute("depth", "144");
//        element.setAttribute("x", "56");
//        element.setAttribute("y", "16");
//        element.setAttribute("z", "16");
//        element.setAttribute("height", "12");
//
////        elementP = new IIOMetadataNode( "scaffoldTower" );
////        elementP.setAttribute( "proscenium-width", "330" );
////        elementP.setAttribute( "proscenium-height", "250" );
////        elementP.setAttribute( "proscenium-depth", "22" );
////        elementP.setAttribute( "apron-depth", "56" );
////        elementP.setAttribute( "apron-width", "440" );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}