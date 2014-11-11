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
import static org.testng.Assert.assertEquals;

/**
 * Test {@code Pipe}.
 *
 * @author dhs
 * @since 0.0.6
 */
public class PipeTest {

    Element element = null;
    Element prosceniumElement = null;

    Double x = 12.0;
    Double y = 23.0;
    Double z = 34.0;
    Double length = 120.0;   // 10' pipe.

    Integer prosceniumX = 200;
    Integer prosceniumY = 144;
    Integer prosceniumZ = 12;
    final String pipeId = "balconyId";

    @Test
    public void isMountable() throws Exception {
        Pipe pipe = new Pipe(element);

        assert Mountable.class.isInstance(pipe);
    }

    @Test
    public void constantDiameter() {
        assertEquals( Pipe.DIAMETER, 2.0 );
    }

    @Test
    public void constantLayerName() {
        assertEquals(Pipe.LAYERNAME, "Pipes");
    }

    @Test
    public void constantLayerTag() {
        assertEquals(Pipe.LAYERTAG, "pipe");
    }

    @Test
    public void storesAttributes() throws Exception {
        // These are optional, so their absence should not cause a problem:
//        diversionElement.removeAttribute("orientation");
//        diversionElement.removeAttribute("offsetx");

        Pipe pipe = new Pipe(element);

        assertEquals( TestHelpers.accessString( pipe, "id" ), pipeId );
        assertEquals( TestHelpers.accessDouble( pipe, "length" ), length );
//        assertEquals( TestHelpers.accessString( pipe, "x" ), x.toString() );
//        assertEquals( TestHelpers.accessString( pipe, "y" ), y.toString() );
//        assertEquals( TestHelpers.accessString( pipe, "z" ), z.toString() );
        assertEquals(TestHelpers.accessPoint(pipe, "start"), new Point(12, 23, 34));
        assertEquals( TestHelpers.accessDouble( pipe, "orientation" ), 0.0 );
        assertEquals( TestHelpers.accessDouble( pipe, "offsetX" ), 0.0 );
    }
//    @Test
//    public void storesAttributes() throws Exception {
//        Pipe pipe = new Pipe(diversionElement);
//
//        assertEquals(TestHelpers.accessInteger(pipe, "length"), (Integer) 120);
//        assertTrue(new Point(12, 23, 34).equals(TestHelpers.accessPoint(pipe, "start")));
//        assertEquals(TestHelpers.accessString(pipe, "id"), balconyId);
//    }


    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute("orientation", "-90");
        element.setAttribute("offsetx", "-50");

        Pipe pipe = new Pipe(element);

        assertEquals( TestHelpers.accessString( pipe, "id" ), pipeId );
        assertEquals( TestHelpers.accessDouble( pipe, "length" ), length );
//        assertEquals( TestHelpers.accessInteger( pipe, "length" ), length );
//        assertEquals( TestHelpers.accessString( pipe, "x" ), x.toString() );
//        assertEquals( TestHelpers.accessString( pipe, "y" ), y.toString() );
//        assertEquals( TestHelpers.accessString( pipe, "z" ), z.toString() );
        assertEquals(TestHelpers.accessPoint(pipe, "start"), new Point(12, 23, 34));
        assertEquals( TestHelpers.accessDouble( pipe, "orientation" ), -90.0 );
        assertEquals( TestHelpers.accessDouble( pipe, "offsetX" ), -50.0 );
    }

    @Test
    public void stores() throws Exception {
        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertEquals(list1.size(), 0);

        Pipe pipe = new Pipe(element);

        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assert list2.contains(pipe);
    }

    @Test
    public void storesOnlyWhenGood() throws Exception {
        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertEquals(list1.size(), 0);

        element.setAttribute("length", "0");
        try {
            new Pipe(element);
        } catch (Exception e) {
        }

        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertEquals(list2.size(), 0);
    }

    @Test
    public void UnstoresWhenBad() throws Exception {
        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertEquals(list1.size(), 0);

        element.setAttribute("length", "339");
        Pipe pipe = new Pipe(element);
        assert list1.contains(pipe);

        try {
            pipe.verify();
        } catch (Exception e) {
        }

        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertFalse(list2.contains(pipe));
    }

    @Test
    public void UnstoresWhenBadWithProscenium() throws Exception {
        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertEquals(list1.size(), 0);

        new Proscenium(prosceniumElement);
        element.setAttribute("length", "339");
        Pipe pipe = new Pipe(element);
        assert list1.contains(pipe);

        try {
            pipe.verify();
        } catch (Exception e) {
        }

        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST");
        assertFalse(list2.contains(pipe));
    }

    @Test
    public void registersLayer() throws Exception {
        Pipe pipe = new Pipe(element);

        HashMap<String, Layer> layers = Layer.List();

        assertTrue(layers.containsKey(Pipe.LAYERTAG));
        assertEquals(layers.get(Pipe.LAYERTAG).name(), Pipe.LAYERNAME);
    }

    @Test
    public void recallsNull() {
        assertNull(Mountable.Select("bogus"));
    }

    @Test
    public void select() throws Exception {
        element.setAttribute("id", "friendly");
        Pipe pipe = new Pipe(element);
        assertSame(Mountable.Select("friendly"), pipe);
    }

    @Test
    public void selectCoexistsWithTruss() throws Exception {
        Element hangPoint1 = new IIOMetadataNode("hangpoint");
        hangPoint1.setAttribute("id", "jim");
        hangPoint1.setAttribute("x", "100");
        hangPoint1.setAttribute("y", "102");
        new HangPoint(hangPoint1);

        Element hangPoint2 = new IIOMetadataNode("hangpoint");
        hangPoint2.setAttribute("id", "joan");
        hangPoint2.setAttribute("x", "200");
        hangPoint2.setAttribute("y", "202");
        new HangPoint(hangPoint2);

        Element suspendElement1 = new IIOMetadataNode("suspend");
        suspendElement1.setAttribute("ref", "jim");
        suspendElement1.setAttribute("distance", "1");

        Element suspendElement2 = new IIOMetadataNode("suspend");
        suspendElement2.setAttribute("ref", "joan");
        suspendElement2.setAttribute("distance", "2");

        Element trussElement = new IIOMetadataNode("truss");
        trussElement.setAttribute("id", "fred");
        trussElement.setAttribute("size", "12");
        trussElement.setAttribute("length", "120");
        trussElement.appendChild(suspendElement1);
        trussElement.appendChild(suspendElement2);
        Truss truss = new Truss(trussElement);

        element.setAttribute("id", "friendly");
        Pipe pipe = new Pipe(element);
        assertSame(Mountable.Select("friendly"), pipe);
        assertSame(Mountable.Select("fred"), truss);
    }

    @Test
    public void storesSelf() throws Exception {
        Pipe pipe = new Pipe(element);

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains(pipe);
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe instance is missing required 'id' attribute.")
    public void noId() throws Exception {
        element.removeAttribute("id");
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) is missing required 'length' attribute.")
    public void noLength() throws Exception {
        element.removeAttribute("length");
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute("x");
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute("y");
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) is missing required 'z' attribute.")
    public void noZ() throws Exception {
        element.removeAttribute("z");
        new Pipe(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\{ origin=Point \\{ x=12.0, y=23.0, z=34.0 \\}, length=0 \\} should have a positive length.")
    public void tooSmallLengthZero() throws Exception {
        element.setAttribute("length", "0");
        new Pipe(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\{ origin=Point \\{ x=12.0, y=23.0, z=34.0 \\}, length=-1 \\} should have a positive length.")
    public void tooSmallLength() throws Exception {
        element.setAttribute("length", "-1");
        new Pipe(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeLength() throws Exception {
        element.setAttribute("length", "339");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeLengthProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("length", "339");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallX() throws Exception {
        element.setAttribute("x", "-1");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeX() throws Exception {
        element.setAttribute("x", "351");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallY() throws Exception {
        element.setAttribute("y", "-1");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeY() throws Exception {
        element.setAttribute("y", "401");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallZ() throws Exception {
        element.setAttribute("z", "-1");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeZ() throws Exception {
        element.setAttribute("z", "241");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test
    public void location() throws Exception {
        Pipe pipe = new Pipe(element);

        Point place = pipe.location("15");
        assert place.equals(new Point(27, 23, 34));
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) location is not a number.")
    public void locationNotNumber() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.location("a");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "beyond the end of \\(non-proscenium\\) Pipe")
    public void locationOffPipe() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.location("121");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of \\(non-proscenium\\) Pipe")
    public void locationNegativeOffPipe() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.location("-1");
    }

    @Test
    public void locationWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        Point place = pipe.location("15");
        assertEquals(place.x, 227.0 );
        assertEquals(place.y, 122.0 );
        assertEquals(place.z, 45.0 );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of \\(proscenium, off-center\\) Pipe")
    public void locationOffPipeWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        pipe.location("121");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of \\(proscenium, off-center\\) Pipe")
    public void locationNegativeOffPipeWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        pipe.location("-1");
    }

    @Test
    public void locationPipeCrossesCenterlineWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        Point place = pipe.location("15");
        assertEquals(place.x, 215.0 );
        assertEquals(place.y, 122.0 );
        assertEquals(place.z, 45.0 );
    }

    @Test
    public void locationNegativePipeCrossesCenterlineWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        Point place = pipe.location("-5");
        assertEquals(place.x, 195.0 );
        assertEquals(place.y, 122.0 );
        assertEquals(place.z, 45.0 );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of \\(proscenium, crosses center\\) Pipe")
    public void locationOffPipeCrossingCenterlineOfProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        pipe.location("120");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of \\(proscenium, crosses center\\) Pipe")
    public void locationNegativeOffPipeCrossingCenterlineOfProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        pipe.location("-15");
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("width"), length);
        assertEquals(element.getAttribute("height"), Pipe.DIAMETER.toString());
        assertEquals(element.getAttribute("fill"), "none");
    }

    @Test
    public void domPlanProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        new Proscenium(prosceniumElement);
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.PLAN);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double ex = prosceniumX + x;
        Double wy = prosceniumY - (y - 1);
        assertEquals(element.getAttribute("x"), ex.toString());
        assertEquals(element.getAttribute("y"), wy.toString());
    }

    @Test
    public void domPlanNoProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.PLAN);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x"), x.toString());
        assertEquals(element.getAttribute("y"), ((Double) (y - 1)).toString());
    }

    @Test
    public void domSection() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.SECTION);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("width"), Pipe.DIAMETER.toString());
        assertEquals(element.getAttribute("height"), Pipe.DIAMETER.toString());
        assertEquals(element.getAttribute("fill"), "none");
    }

    @Test
    public void domSectionProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        new Proscenium(prosceniumElement);
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.SECTION);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double wye = prosceniumY - (y - 1);
        Double zee = Venue.Height() - (prosceniumZ + z - 1);
        assertEquals(element.getAttribute("x"), wye.toString());
        assertEquals(element.getAttribute("y"), zee.toString());
    }

    @Test
    public void domSectionNoProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.SECTION);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double wye = y - 1;
        Double zee = Venue.Height() - (z - 1);
        assertEquals(element.getAttribute("x"), wye.toString());
        assertEquals(element.getAttribute("y"), zee.toString());
    }

    @Test
    public void domFront() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.FRONT);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("width"), length);
        assertEquals(element.getAttribute("height"), Pipe.DIAMETER.toString());
        assertEquals(element.getAttribute("fill"), "none");
    }

    @Test
    public void domFrontProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        new Proscenium(prosceniumElement);
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.FRONT);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double exe = prosceniumX + x;
        Double zee = Venue.Height() - (prosceniumZ + z - 1);
        assertEquals(element.getAttribute("x"), exe.toString());
        assertEquals(element.getAttribute("y"), zee.toString());
    }

    @Test
    public void domFrontNoProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.FRONT);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double zee = Venue.Height() - (z - 1);
        assertEquals(element.getAttribute("x"), x.toString());
        assertEquals(element.getAttribute("y"), zee.toString());
    }

    @Test
    public void parseWithCoordinates() throws Exception {
        String xml = "<plot>" +
                "<pipe id=\"Identifier\" length=\"71\" x=\"4\" y=\"14\" z=\"2\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 1 );
    }

    @Test
    public void parseWithSuspends() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"bill\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"betty\" x=\"7\" y=\"8\" />" +
                "<pipe id=\"fineMe\" length=\"17\" >" +
                "<suspend ref=\"bill\" />" +
                "<suspend ref=\"betty\" />" +
                "</pipe>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 5 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ProsceniumReset();
        TestResets.MountableReset();
        TestResets.LayerReset();

        Element venueElement = new IIOMetadataNode("venue");
        venueElement.setAttribute("room", "Test Name");
        venueElement.setAttribute("width", "350");
        venueElement.setAttribute("depth", "400");
        venueElement.setAttribute("height", "240");
        new Venue(venueElement);

        prosceniumElement = new IIOMetadataNode("proscenium");
        prosceniumElement.setAttribute("width", "260");
        prosceniumElement.setAttribute("height", "200");
        prosceniumElement.setAttribute("depth", "22");
        prosceniumElement.setAttribute("x", prosceniumX.toString());
        prosceniumElement.setAttribute("y", prosceniumY.toString());
        prosceniumElement.setAttribute("z", prosceniumZ.toString());

        element = new IIOMetadataNode("pipe");
        element.setAttribute("id", pipeId);
        element.setAttribute("length", length.toString());
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString());
        element.setAttribute("z", z.toString());
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
