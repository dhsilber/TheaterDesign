//package com.mobiletheatertech.plot;
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static org.testng.Assert.*;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertSame;
//
///**
// * Created by dhs on 4/14/14.
// *
// * @author dhs
// * @since 0.0.24
// */
//public class BalconyTest {
//
//    Element element = null;
//    Element prosceniumElement = null;
//
//    Double x = 12.0;
//    Double y = 23.0;
//    Double z = 34.0;
//    Double floorHeight = 194.0;
//    Double underHeight = 100.0;
//    Double wallHeight = 37.0;
//    Double railHeight = 9.0;
//
//    Double length = 120.0;   // 10' pipe.
//
//    Double prosceniumX = 200.0;
//    Double prosceniumY = 144.0;
//    Double prosceniumZ = 12.0;
////    final String balconyId = "balconyId";
//
//    @Test
//    public void constantLayerName() {
//        assertEquals(Balcony.LAYERNAME, "Balconies");
//    }
//
//    @Test
//    public void constantLayerTag() {
//        assertEquals(Balcony.LAYERTAG, "balcony");
//    }
//
//    @Test
//    public void isMinderDom() throws Exception {
//        Balcony balcony = new Balcony(element);
//
//        assert MinderDom.class.isInstance( balcony );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        Balcony balcony = new Balcony(element);
//
////        assertEquals( TestHelpers.accessString( balcony, "id" ), balconyId);
//        assertEquals( TestHelpers.accessDouble(balcony, "floorHeight"), floorHeight);
//        assertEquals( TestHelpers.accessDouble(balcony, "underHeight"), underHeight);
//        assertEquals( TestHelpers.accessDouble(balcony, "wallHeight"), wallHeight);
//        assertEquals( TestHelpers.accessDouble( balcony, "railHeight" ), railHeight);
//    }
//
////    @Test
////    public void storesOptionalAttributes() throws Exception {
////    }
//
//    @Test
//    public void registersLayer() throws Exception {
//        new Balcony(element);
//
//        HashMap<String, Layer> layers = Layer.List();
//
//        assertTrue(layers.containsKey(Balcony.LAYERTAG ));
//        assertEquals( layers.get( Balcony.LAYERTAG ).name(), Balcony.LAYERNAME );
//    }
//
//    @Test
//    public void storesSelf() throws Exception {
//        Balcony balcony = new Balcony(element);
//
//        ArrayList<ElementalLister> thing = ElementalLister.List();
//
//        assert thing.contains(balcony);
//    }
//
//    /*
//     * This is to ensure that no exception is thrown if data is OK.
//     */
//    @Test
//    public void justFine() throws Exception {
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "Balcony instance is missing required 'floor-height' attribute.")
//    public void noFloorHeight() throws Exception {
//        element.removeAttribute("floor-height");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "Balcony instance is missing required 'under-height' attribute.")
//    public void noUnderHeight() throws Exception {
//        element.removeAttribute("under-height");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "Balcony instance is missing required 'wall-height' attribute.")
//    public void noWallHeight() throws Exception {
//        element.removeAttribute("wall-height");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance is missing required 'rail-height' attribute.")
//    public void noRailHeight() throws Exception {
//        element.removeAttribute("rail-height");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'floor-height' attribute should not be zero.")
//    public void tooSmallFloorHeightZero() throws Exception {
//        element.setAttribute("floor-height", "0");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'floor-height' attribute should not be negative.")
//    public void tooSmallFloorHeight() throws Exception {
//        element.setAttribute("floor-height", "-1");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance should not extend beyond the boundaries of the venue.")
//    public void tooLargeFloorHeight() throws Exception {
//        element.setAttribute("floor-height", "195");
//        Balcony balcony = new Balcony(element);
//        balcony.verify();
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'wall-height' attribute should not be zero.")
//    public void tooSmallWallHeightZero() throws Exception {
//        element.setAttribute("wall-height", "0");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'wall-height' attribute should not be negative.")
//    public void tooSmallWallHeight() throws Exception {
//        element.setAttribute("wall-height", "-1");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance should not extend beyond the boundaries of the venue.")
//    public void tooLargeWallHeight() throws Exception {
//        element.setAttribute("wall-height", "38");
//        Balcony balcony = new Balcony(element);
//        balcony.verify();
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'rail-height' attribute should not be zero.")
//    public void tooSmallRailHeightZero() throws Exception {
//        element.setAttribute("rail-height", "0");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'rail-height' attribute should not be negative.")
//    public void tooSmallRailHeight() throws Exception {
//        element.setAttribute("rail-height", "-1");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance should not extend beyond the boundaries of the venue.")
//    public void tooLargeRailHeight() throws Exception {
//        element.setAttribute("rail-height", "10");
//        Balcony balcony = new Balcony(element);
//        balcony.verify();
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'under-height' attribute should not be zero.")
//    public void tooSmallUnderHeightZero() throws Exception {
//        element.setAttribute("under-height", "0");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance value for 'under-height' attribute should not be negative.")
//    public void tooSmallUnderHeight() throws Exception {
//        element.setAttribute("under-height", "-1");
//        new Balcony(element);
//    }
//
//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "Balcony instance under-height should be lower then the floor-height.")
//    public void tooLargeUnderHeight() throws Exception {
//        element.setAttribute("under-height", "194");
//        Balcony balcony = new Balcony(element);
//        balcony.verify();
//    }
//
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
//        TestResets.ProsceniumReset();
//        TestResets.MountableReset();
//        TestResets.LayerReset();
//        TestResets.ElementalListerReset();
//
//        Element venueElement = new IIOMetadataNode("venue");
//        venueElement.setAttribute("room", "Test Name");
//        venueElement.setAttribute("width", "350");
//        venueElement.setAttribute("depth", "400");
//        venueElement.setAttribute("height", "240");
//        new Venue(venueElement);
//
//        prosceniumElement = new IIOMetadataNode("proscenium");
//        prosceniumElement.setAttribute("width", "260");
//        prosceniumElement.setAttribute("height", "200");
//        prosceniumElement.setAttribute("depth", "22");
//        prosceniumElement.setAttribute("x", prosceniumX.toString());
//        prosceniumElement.setAttribute("y", prosceniumY.toString());
//        prosceniumElement.setAttribute("z", prosceniumZ.toString());
//
//        element = new IIOMetadataNode("balcony");
//        element.setAttribute("id", "balcony pipe Id" );
//        element.setAttribute( "length", "7" );
//        element.setAttribute("floor-height", floorHeight.toString());
//        element.setAttribute("under-height", underHeight.toString());
//        element.setAttribute("wall-height", wallHeight.toString());
//        element.setAttribute("rail-height", railHeight.toString());
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}
