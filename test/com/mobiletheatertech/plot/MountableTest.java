//package com.mobiletheatertech.plot;
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//import scala.collection.mutable.ArrayBuffer;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.util.ArrayList;
//
//import static org.testng.Assert.*;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
///**
// * Created by DHS on 7/27/16.
// */
//public class MountableTest {
//
//
//
///**
// * Test {@code Yokeable}
// *
// * @author dhs
// * @since 0.0.20 (split off from 0.0.2 code)
// */
//
//    /**
//     * Extend {@code Yokeable} so that there is a concrete class to test with.
//     */
//    private class Mounted implements Mountable {
//
//        public Mounted( Element element )
//                throws AttributeMissingException, DataException,
//                InvalidXMLException, MountingException {
////            super( element );
//        }
//
//        @Override
//        public PagePoint schematicLocation( String location ) throws InvalidXMLException {
//            return null;
//        }
//
//        @Override
//        public Point mountableLocation(String location) {
//            return new Point( 3.1, 4.1, 5.1 );
//        }
//
//        @Override
//        public Place rotatedLocation(String location)
//                throws InvalidXMLException, MountingException, ReferenceException {
//            Point locationPoint = new Point( 11, 12, 13 );
//            Point origin = new Point( 14, 15, 16 );
//            Double rotation = 17.8;
//            Place here = new Place( locationPoint, origin, rotation );
//
////            System.err.print( "Here: " + here.toString() );
//            return here;
//        }
//
//        @Override
//        public void hang(Luminaire luminaire) {
//            super.hang(luminaire);
//        }
//
//        @Override
//        public void verify() throws InvalidXMLException {
//            //To change body of implemented methods use File | Settings | File Templates.
//        }
//
//        @Override
//        public void dom( Draw draw, View mode ) {
//            throw new UnsupportedOperationException(
//                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
//        }
//
////        @Override
////        public PagePoint schematicPosition() { return null; }
////
////        /**
////         * This is schematicPosition() adjusted to take into account the number of
////         * cables attempting to intersect a thing from that direction.
////         *
////         * @return a position along the edge of a thing
////         */
////        @Override
////        public PagePoint schematicCableIntersectPosition( CableRun run )
////                throws CorruptedInternalInformationException, ReferenceException
////        { return null; }
////
////        @Override
////        public Rectangle2D.Double schematicBox()
////        { return null; }
////
////        @Override
////        public void schematicReset()  {}
////
////        @Override
////        public void useCount( Direction direction, CableRun run ) {}
////
////        @Override
////        public void preview( View view )
////                throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException
////        {}
////
////        @Override
////        public Place drawingLocation()
////                throws InvalidXMLException, MountingException, ReferenceException
////        {
////            Point location = new Point( 1, 2, 3 );
////            Point origin = new Point( 4, 5, 6 );
////            Double rotation = 7.8;
////            Place here = new Place( location, origin, rotation );
////
//////            System.err.print( "Here: " + here.toString() );
////            return here;
////        }
//
//        /**
//         * Elucidate the support for this.
//         //         * @param suspensions
//         * @return
//         */
//        @Override
//        public String suspensionPoints( /*ArrayList<Anchor> suspensions*/ ) {
//            return "suspension points";
//        }
//
//        @Override
//        public String calculateIndividualLoad( Luminaire luminaire ) throws InvalidXMLException, MountingException {
//            return "";
//        }
//
//        @Override
//        public String totalSuspendLoads() {
//            return "";
//        }
//
//        @Override
//        public String weights() throws InvalidXMLException, MountingException {
//            return super.weights();
//        }
//
//        @Override
//        public Integer locationDistance( String location ) {
//            return 0;
//        }
//
//        @Override
//        public Luminaire[] loads() {
//            return super.loads();
//        }
//
//        @Override
//        public boolean contains(Luminaire item) {
//            return super.contains(item);
//        }
//
//        public void clearLuminaires() {
//            LUMINAIRELIST.clear();
//        }
//
//        @Override
//        public ArrayBuffer<Luminaire> LuminaireList() {
//            return null;
//        }
//
//        @Override
//        public String identifier() {
//            return null;
//        }
//
//        @Override
//        public void identifier_$eq(String identifier) {
//
//        }
//    }
//
//    //    private static Draw draw = null;
//    private Element element = null;
//    private String id = "MountedID";
//
//    Element luminaireElement;
//    Double weight = 9.4;
//    String unit = "7";
//    String type = "6x9";
//    String location = "13";
//
//    public YokeableTest() {
//    }
//
//    @Test
//    public void isA() throws Exception {
//        Mounted instance = new Mounted( element );
//
//        assert Elemental.class.isInstance( instance );
//        assert ElementalLister.class.isInstance( instance );
//        assert Verifier.class.isInstance( instance );
//        assert Layerer.class.isInstance( instance );
//        assert MinderDom.class.isInstance( instance );
//        assert UniqueId.class.isInstance( instance );
//    }
//
//    @Test
//    public void stores() throws Exception {
//        ArrayList<Yokeable> list1 = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
//        assertEquals( list1.size(), 0 );
//
//        Mounted pipe = new Mounted( element );
//
//        ArrayList<Yokeable> list2 = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
//        assert list2.contains(pipe);
//        assertEquals( list2.size(), 1 );
//    }
//
////    @Test
////    public void UnstoresById() throws Exception {
////        Mounted pipe = new Mounted( diversionElement );
////        ArrayList<Yokeable> list1 = (ArrayList<Yokeable>)
////                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "MOUNTABLELIST" );
////        assertEquals( list1.size(), 1 );
////        assert list1.contains( pipe );
////
////        Yokeable.Remove( id );
////
////        ArrayList<Yokeable> list2 = (ArrayList<Yokeable>)
////                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "MOUNTABLELIST" );
////        assertFalse( list2.contains( pipe ) );
////    }
//
//    @Test
//    public void UnstoresByReference() throws Exception {
//        Mounted pipe = new Mounted( element );
//        ArrayList<Yokeable> list1 = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
//        assertEquals( list1.size(), 1 );
//        assert list1.contains( pipe );
//
//        Yokeable.Remove( pipe );
//
//        ArrayList<Yokeable> list2 = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
//        assertFalse( list2.contains( pipe ) );
//        assertEquals( list2.size(), 0 );
//    }
//
////    @Test
////    public void UnstoresByBogusId() throws Exception {
////        Mounted pipe = new Mounted( diversionElement );
////        ArrayList<Yokeable> list1 = (ArrayList<Yokeable>)
////                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
////        assertEquals( list1.size(), 1 );
////        assert list1.contains( pipe );
////
////        Yokeable.Remove( "bogusId" );
////
////        ArrayList<Yokeable> list2 = (ArrayList<Yokeable>)
////                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
////        assertEquals(list2.size(), 1);
////    }
//
//    @Test
//    public void UnstoresByBogusReference() throws Exception {
//        Mounted pipe = new Mounted( element );
//        Yokeable.Remove( pipe );
//
//        Yokeable.Remove( pipe );
//
//        ArrayList<Yokeable> list = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Yokeable", "YOKEABLELIST" );
//        assertEquals(list.size(), 0);
//    }
//
//    @Test(expectedExceptions = MountingException.class,
//            expectedExceptionsMessageRegExp = "'bogus' is not a mountable object.")
//    public void recallsBogus() throws Exception {
//        Yokeable.Select("bogus");
//    }
//
//    @Test
//    public void recalls() throws Exception {
//        element.setAttribute("id", "friendly");
//        Mounted pipe = new Mounted( element );
//        Yokeable found = Yokeable.Select("friendly");
//        assertNotNull( found );
//        assertSame( found, pipe);
//    }
//
//    @Test
//    public void storesIdAttribute() throws Exception {
//        element.setAttribute( "id", "Pipe name" );
//        Mounted pipe = new Mounted( element );
//        assertEquals( TestHelpers.accessString( pipe, "id" ), "Pipe name" );
//    }
//
//    @Test
//    public void storesSelf() throws Exception {
//        Mounted pipe = new Mounted( element );
//
//        ArrayList<ElementalLister> thing = ElementalLister.List();
//
//        assert thing.contains( pipe );
//    }
//
//    /*
//     * This is to ensure that no exception is thrown if data is OK.
//     */
//    @Test
//    public void justFine() throws Exception {
//        new Mounted( element );
//    }
//
//    @Test( expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp = "Mounted element unexpectedly null!" )
//    public void NullElement() throws Exception {
//        new Mounted( null );
//    }
//
//    @Test
//    public void registersOnMountable() throws Exception {
//        Luminaire luminaire = new Luminaire( luminaireElement );
//
//        Mounted pipe = new Mounted( element );
//        pipe.hang( luminaire );
//
//        assertTrue(pipe.loads().contains(luminaire));
//    }
//
//    @Test
//    public void WeightsText() throws Exception {
//        String weightsText = "Weights for " + id + "\n\n"
//                + "suspension points\n\n"
//                + unit + ": " + type + " at " + location + " weighs " + weight + " pounds.\n"
//                + "\nTotal: " + weight + " pounds\n";
//
//        Mounted pipe = new Mounted( element );
//        Luminaire luminaire = new Luminaire( luminaireElement );
//        luminaire.verify();
//
////        pipe.hang( luminaire );
//
//        assertEquals( pipe.weights(), weightsText );
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
////        draw = new Draw();
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @BeforeMethod
//    public void setUpMethod() throws Exception {
//        TestResets.YokeableReset();
//        TestResets.LuminaireReset();
//        UniqueId.Reset();
//
//        element = new IIOMetadataNode( "mounted" );
//        element.setAttribute( "id", id );
//
//        Integer width = 13;
//        Integer length = 27;
//        Element definitionElement = new IIOMetadataNode( "luminaire-definition" );
//        definitionElement.setAttribute( "name", type );
//        definitionElement.setAttribute( "width", width.toString() );
//        definitionElement.setAttribute( "length", length.toString() );
//        definitionElement.setAttribute( "weight", weight.toString() );
//        definitionElement.appendChild(new IIOMetadataNode("svg"));
//        new LuminaireDefinition( definitionElement );
//
//        luminaireElement = new IIOMetadataNode( "luminaire" );
//        luminaireElement.setAttribute( "type", type );
//        luminaireElement.setAttribute( "on", id );
//        luminaireElement.setAttribute( "location", location );
////        luminaireElement.setAttribute("dimmer", dimmer);
////        luminaireElement.setAttribute("circuit", circuit);
////        luminaireElement.setAttribute("channel", channel);
////        luminaireElement.setAttribute("color", color);
//        luminaireElement.setAttribute("unit", unit );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}
