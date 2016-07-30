package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code Stage}.
 *nstage
 * @author dhs
 * @since 0.0.3
 */
public class StageTest {

    Element element = null;
    Element elementP = null;

    String stageID = "name";
    Double x = 56.0;
    Double y = 16.0;
    Double z = 12.0;
    Double width = 288.0;
    Double depth = 144.0;

    String foldingRiser = "Folding riser";
    Integer riserX = 34;
    Integer riserY = 23;
    Integer riserOrientation = 0;

    public StageTest() {
    }

    @Test
    public void isA() throws Exception {
        Stage stage = new Stage(element);

        assert Stackable.class.isInstance(stage);
    }

    @Test
    public void isLegendable() throws Exception {
        Stage stage = new Stage( element );
        assert Legendable.class.isInstance( stage );
    }

    @Test
    public void storesAttributes() throws Exception {
        Stage stage = new Stage(element);

        assertEquals(TestHelpers.accessString(stage, "id"), "" );
        assertEquals(TestHelpers.accessDouble(stage, "width"), width );
        assertEquals(TestHelpers.accessDouble(stage, "depth"), depth );
        assertEquals(TestHelpers.accessDouble(stage, "x"), x );
        assertEquals(TestHelpers.accessDouble(stage, "y"), y );
        assertEquals(TestHelpers.accessDouble(stage, "z"), z );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "id", stageID );
        Stage stage = new Stage(element);

        assertEquals(TestHelpers.accessString(stage, "id"), stageID );
        assertEquals(TestHelpers.accessDouble(stage, "width"), width );
        assertEquals(TestHelpers.accessDouble(stage, "depth"), depth );
        assertEquals(TestHelpers.accessDouble(stage, "x"), x );
        assertEquals(TestHelpers.accessDouble(stage, "y"), y );
        assertEquals(TestHelpers.accessDouble(stage, "z"), z );
    }

//    @Test
//    public void storesProsceniumAttributes() throws Exception {
//        Stage stage = new Stage( elementP );
//
//        assertEquals( TestHelpers.accessInteger( stage, "prosceniumWidth" ), (Integer) 288 );
//        assertEquals( TestHelpers.accessInteger( stage, "prosceniumDepth" ), (Integer) 144 );
//        assertEquals( TestHelpers.accessInteger( stage, "prosceniumHeight" ), (Integer) 144 );
//        assertEquals( TestHelpers.accessInteger( stage, "apronDepth" ), (Integer) 56 );
//        assertEquals( TestHelpers.accessInteger( stage, "apronWidth" ), (Integer) 16 );
//    }

//    // Until such time as I properly implement this class' use of id.
//    @Test
//    public void idUnused() throws Exception {
//        Stage stage = new Stage(element);
//
//        assertNull(TestHelpers.accessString(stage, "id"));
//    }

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( Stage.CATEGORY ) );
//
//        new Stage( element );
//
//        assertNotNull( Category.Select( Stage.CATEGORY ) );
//    }

    @Test
    public void storesSelf() throws Exception {
        Stage stage = new Stage(element);

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains(stage);
    }

//    @Test
//    public void findChildRiser() throws Exception {
//        Element displayElement = new IIOMetadataNode( "riser" );
//        displayElement.setAttribute( "type", foldingRiser );
//        displayElement.setAttribute( "x", riserX.toString() );
//        displayElement.setAttribute( "y", riserY.toString() );
//        displayElement.setAttribute( "orientation", riserOrientation.toString() );
//        element.appendChild( displayElement );
//
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Stage stage = new Stage(element);
//
//        ArrayList<Device> risers = stage.risers();
//
//        assertEquals( risers.size(), 1 );
//        Device stageRiser = risers.get( 0 );
////        assertEquals(layer.name(), name);
//        assertEquals( stageRiser.is(), foldingRiser );
////        fail();
//    }

    /*
         * This is to ensure that no exception is thrown if data is OK.
         */
    @Test
    public void justFine() throws Exception {
        new Stage(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Stage instance is missing required 'width' attribute.")
    public void noWidth() throws Exception {
        element.removeAttribute("width");
        new Stage(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Stage instance is missing required 'depth' attribute.")
    public void noDepth() throws Exception {
        element.removeAttribute("depth");
        new Stage(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Stage instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute("x");
        new Stage(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Stage instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute("y");
        new Stage(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Stage instance is missing required 'z' attribute.")
    public void noZ() throws Exception {
        element.removeAttribute("z");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooLargeWidth() throws Exception {
        element.setAttribute("width", "495");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooLargeDepth() throws Exception {
        element.setAttribute("depth", "401");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooLargeX() throws Exception {
        element.setAttribute("x", "263");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooLargeY() throws Exception {
        element.setAttribute("y", "401");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooLargeZ() throws Exception {
        element.setAttribute("z", "241");
        new Stage(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should have a positive width.")
    public void tooSmallWidth() throws Exception {
        element.setAttribute("width", "-1");
        new Stage(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should have a positive depth.")
    public void tooSmallDepth() throws Exception {
        element.setAttribute("depth", "-1");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooSmallX() throws Exception {
        element.setAttribute("x", "-1");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooSmallY() throws Exception {
        element.setAttribute("y", "-1");
        new Stage(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Stage should not extend beyond the boundaries of the venue.")
    public void tooSmallZ() throws Exception {
        element.setAttribute("z", "-1");
        new Stage(element);
    }

    @Test
    public void parse() throws Exception {
//        fail();
        String xml = "<plot>" +
                "<stage width=\"12\" depth=\"65\" x=\"3\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        TestResets.MinderDomReset();

        new Parse(stream);

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals(list.size(), 1);
    }

    @Test
    public void parseMultiple() throws Exception {
//        fail();
        String xml = "<plot>" +
                "<stage width=\"12\" depth=\"65\" x=\"3\" y=\"6\" z=\"9\" />" +
                "<stage width=\"12\" depth=\"65\" x=\"3\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        TestResets.MinderDomReset();

        new Parse(stream);

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals(list.size(), 2);
    }

//    @Mocked
//    Graphics2D mockCanvas;
//
//    @Test
//    public void draw() throws Exception {
//        Stage stage = new Stage(element);
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint(Color.orange);
//                mockCanvas.draw(new Rectangle(56, 16, 288, 144));
//            }
//        };
//        stage.drawPlan(mockCanvas);
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        Stage stage = new Stage(element);
//
//        stage.dom(null, View.PLAN);
//    }


    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Stage stage = new Stage(element);

        NodeList existingRectangles = draw.root().getElementsByTagName("rect");
        assertEquals(existingRectangles.getLength(), 0);

        stage.dom(draw, View.PLAN);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element tableElement = (Element) groupNode;
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);
        assertEquals(tableElement.getAttribute("x"), x.toString() );
        assertEquals(tableElement.getAttribute("y"), y.toString() );
        assertEquals(tableElement.getAttribute("width"), width.toString() );
        // Plot attribute is 'depth'. SVG attribute is 'height'.
        assertEquals(tableElement.getAttribute("height"), depth.toString() );
        assertEquals(tableElement.getAttribute("fill"), "none");
        assertEquals(tableElement.getAttribute("stroke"), "orange");
    }

//    @Test
//    public void domFront() throws Exception {
//        Draw draw = new Draw();
//
//        draw.establishRoot();
//        Stage stage = new Stage(element);
//
//        NodeList existingLines = draw.root().getElementsByTagName("line");
//        assertEquals(existingLines.getLength(), 0);
//
//        stage.dom(draw, View.FRONT);
//
//        NodeList lines = draw.root().getElementsByTagName("line");
//        assertEquals(lines.getLength(), 3);
//        Node groupNode = lines.item(0);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element tableElement = (Element) groupNode;
////        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);
//
//        assertEquals(tableElement.getAttribute("x1"), x.toString() );
//        assertEquals(tableElement.getAttribute("y1"), y.toString() );
//        assertEquals(tableElement.getAttribute("x2"), width.toString() );
//        assertEquals(tableElement.getAttribute("y2"), depth.toString() );
//        assertEquals(tableElement.getAttribute("stroke-width"), "2");
//        assertEquals(tableElement.getAttribute("stroke"), "orange");
//    }
//
//    @Test
//    public void domSection() throws Exception {
//        Draw draw = new Draw();
//
//        draw.establishRoot();
//        Stage stage = new Stage(element);
//
//        NodeList existingLines = draw.root().getElementsByTagName("line");
//        assertEquals(existingLines.getLength(), 0);
//
//        stage.dom(draw, View.SECTION);
//
//        NodeList lines = draw.root().getElementsByTagName("line");
//        assertEquals(lines.getLength(), 3);
//        Node groupNode = lines.item(0);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element tableElement = (Element) groupNode;
////        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);
//
//        assertEquals(tableElement.getAttribute("x1"), x.toString() );
//        assertEquals(tableElement.getAttribute("y1"), y.toString() );
//        assertEquals(tableElement.getAttribute("x2"), width.toString() );
//        assertEquals(tableElement.getAttribute("y2"), depth.toString() );
//        assertEquals(tableElement.getAttribute("stroke-width"), "2");
//        assertEquals(tableElement.getAttribute("stroke"), "orange");
//    }

//    @Test
//    public void stagesCreatesMultiple() {
////        fail( "Stage does not yet support multiples" );
//        throw new SkipException( "Stage does not yet support multiples" );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ElementalListerReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute("room", "Test Name");
        venueElement.setAttribute("width", "550");
        venueElement.setAttribute("depth", "400");
        venueElement.setAttribute("height", "240");
        new Venue(venueElement);

        element = new IIOMetadataNode("stage");
        element.setAttribute("width", width.toString() );
        element.setAttribute("depth", depth.toString() );
        element.setAttribute("x", x.toString() );
        element.setAttribute("y", y.toString() );
        element.setAttribute("z", z.toString() );

//        elementP = new IIOMetadataNode( "stage" );
//        elementP.setAttribute( "proscenium-width", "330" );
//        elementP.setAttribute( "proscenium-height", "250" );
//        elementP.setAttribute( "proscenium-depth", "22" );
//        elementP.setAttribute( "apron-depth", "56" );
//        elementP.setAttribute( "apron-width", "440" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}