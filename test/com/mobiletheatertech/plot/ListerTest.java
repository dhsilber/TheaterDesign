//package com.mobiletheatertech.plot;
//
//import mockit.Expectations;
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.awt.*;
//import java.util.ArrayList;
//
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.fail;
//
///**
// * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 7:03 PM To change this template use
// * File | Settings | File Templates.
// *
// * Test {@code Lister}
// *
// * @author dhs
// * @since 0.0.20
// */
//public class ListerTest {
//
//    /**
//     * Extended {@code Minder} so that there is a concrete class to test with.
//     */
//    private class Listed extends Lister {
//
//        public Listed( Element baseElement ) throws InvalidXMLException {
//            super( baseElement );
//        }
//    }
//
//    private static Draw draw = null;
//    private Element baseElement = null;
//
//    public ListerTest() {
//    }
//
//    @Test
//    public void isElemental() throws Exception {
//        Listed stringValue = new Listed( baseElement );
//
//        assert Elemental.class.isInstance( stringValue );
//    }
//
//    @Test
//    public void stores() throws Exception {
//        Listed stringValue = new Listed( baseElement );
//        ArrayList<Minder> thing = Drawable.List();
//        assertNotNull( thing, "List should exist" );
//
//        assert thing.contains( stringValue );
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//          expectedExceptionsMessageRegExp = "Element unexpectedly null!")
//    public void NullElement() throws Exception {
//        new Listed( null );
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
//        baseElement = new IIOMetadataNode( "bogus" );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}