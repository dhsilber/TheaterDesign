//package com.mobiletheatertech.plot;
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import static org.testng.Assert.assertEquals;
//
///**
// * Created by dhs on 1/9/15.
// */
//public class UserMulticableTest {
//
//    Element element = null;
//
//    String id = "flavor";
//    Double startX = 6.7;
//    Double startY = 3.6;
//    Double startZ = 33.0;
//    Double endX = 9.7;
//    Double endY = 23.1;
//    Double endZ = 1.1;
//    String color = "black";
//
//    @Test
//    public void isA() throws Exception {
//        UserMulticable instance = new UserMulticable( element );
//
//        assert Elemental.class.isInstance( instance );
//
////        assert Schematicable.class.isInstance( instance );
////        assert Legendable.class.isInstance( instance );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        UserMulticable instance = new UserMulticable( element );
//
//        assertEquals(TestHelpers.accessString(instance, "id"), id );
//        assertEquals(TestHelpers.accessDouble(instance, "startX"), startX.doubleValue());
//        assertEquals(TestHelpers.accessDouble(instance, "startY"), startY.doubleValue());
//        assertEquals(TestHelpers.accessDouble(instance, "startZ"), startZ.doubleValue());
//        assertEquals(TestHelpers.accessDouble(instance, "endX"), endX.doubleValue());
//        assertEquals(TestHelpers.accessDouble(instance, "endY"), endY.doubleValue());
//        assertEquals(TestHelpers.accessDouble(instance, "endZ"), endZ.doubleValue());
//    }
//
//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<multicable id=\"" + id +
//                "\" start-x=\"" + startX +
//                "\" start-y=\"" + startY +
//                "\" start-z=\"" + startZ +
//                "\" end-x=\"" + endX +
//                "\" end-y=\"" + endY +
//                "\" end-z=\"" + endZ +
//                "\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream(xml.getBytes());
//
//        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals( list.size(), 0 );
//
////        new Parse(stream);
//
//        assertEquals( list.size(), 1 );
//    }
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
//        TestResets.ElementalListerReset();
//
//        element = new IIOMetadataNode("multicable");
//        element.setAttribute("id", id);
//        element.setAttribute("start-x", startX.toString() );
//        element.setAttribute("start-y", startY.toString());
//        element.setAttribute("start-z", startZ.toString() );
//        element.setAttribute("end-x", endX.toString() );
//        element.setAttribute("end-y", endY.toString() );
//        element.setAttribute("end-z", endZ.toString() );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}
