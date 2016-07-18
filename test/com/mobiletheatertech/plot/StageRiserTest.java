//package com.mobiletheatertech.plot;
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//
//import javax.imageio.metadata.IIOMetadataNode;
//
//import static org.testng.Assert.assertEquals;
//
///**
// * Created by dhs on 9/25/14.
// */
//public class StageRiserTest {
//
//    Element element = null;
//
//    String type = "Name of Category";
//    Integer x = 17;
//    Integer y = 28;
//    Integer orientation = 0;
//
//    @Test
//    public void isA() throws Exception {
//        StageRiser stageRiser = new StageRiser( element );
//
//        assert Elemental.class.isInstance( stageRiser );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        StageRiser stageRiser = new StageRiser( element );
//
//        assertEquals( TestHelpers.accessString( stageRiser, "type" ), type );
//        assertEquals( TestHelpers.accessInteger(stageRiser, "x"), x );
//        assertEquals( TestHelpers.accessInteger(stageRiser, "y"), y );
//        assertEquals( TestHelpers.accessInteger(stageRiser, "orientation"), orientation );
//    }
//
//    @Test( expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "Display instance is missing required 'layer' attribute." )
//    public void noCategory() throws Exception {
//        element.removeAttribute( "layer" );
//        new StageRiser( element );
//    }
//
//    @Test
//    public void category() throws Exception {
//        StageRiser stageRiser = new StageRiser( element );
//         assertEquals( stageRiser.type(), type );
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
//        element = new IIOMetadataNode( "stageRiser" );
//        element.setAttribute( "type", type );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//
//}
