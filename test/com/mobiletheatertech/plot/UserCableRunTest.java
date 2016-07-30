//package com.mobiletheatertech.plot;
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.util.ArrayList;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertSame;
//
///**
// * Created by dhs on 1/9/15.
// */
//public class UserCableRunTest {
//    Element venueElement = null;
//    Element wallElement1 = null;
//    Element wallElement2 = null;
//    Element wallElement3 = null;
//    Element wallElement4 = null;
//
//    Element tableElement = null;
//    Element tableElement2 = null;
//    Element templateElement = null;
//    Element sourceElement = null;
//    Element sinkElement = null;
//    Element sourceLuminaireElement = null;
//    Element sinkLuminaireElement = null;
//    private Element element = null;
//
//    Element deviceToLuminaireElement = null;
//    Element luminaireToLuminaireElement = null;
//    Element luminaireToDeviceElement = null;
//
//    private final String signalName = "Signal";
//    //    private final String bogusSignal = "Bogus Signal";
//    private final String sourceName = "Source device";
//    private final String sinkName = "Sink device";
//    private final String channel = "Optional Channel";
//    private final String invalidRouting = "Place-marker routing";
//    private final String validRouting = "direct";
//    private final String tableName = "control";
//    private final Double tableX = 4.0;
//    private final Double tableY = 5.0;
//    private final Double tableZ = 6.0;
//    private final String table2Name = "other table";
//    private final Double table2X = 14.0;
//    private final Double table2Y = 15.0;
//    private final Double table2Z = 16.0;
//    private final String templateName = "thingy";
//    private final String color = "tangimarine";
//
//    Device sourceDevice = null;
//    Device sinkDevice = null;
//    LightingStand lightingStand = null;
//    LuminaireDefinition luminaireDefinition = null;
//    Luminaire sourceLuminaire = null;
//    Luminaire sinkLuminaire = null;
//
//    String standId = "stand name";
//    Double standX = 4.6;
//    Double standY = 2.9;
//    String luminaireType = "light type";
//    String sourceLuminaireLocation = "a";
//    String sourceLuminaireUnit = "source light unit";
//    String sourceLuminaireId = standId + ":" + sourceLuminaireUnit;
//    String sinkLuminaireLocation = "d";
//    String sinkLuminaireUnit = "sink light unit";
//    String sinkLuminaireId = standId + ":" + sinkLuminaireUnit;
//
////    private Solid solid = null;
//
//    @Test
//    public void isA() throws Exception {
//        UserCableRun instance = new UserCableRun( element );
//
//        assert Elemental.class.isInstance( instance );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        UserCableRun userCableRun=new UserCableRun( element );
//
//        assertEquals( TestHelpers.accessString( userCableRun, "signal" ), signalName );
//        assertEquals( TestHelpers.accessString( userCableRun, "source" ), sourceName );
//        assertEquals( TestHelpers.accessString( userCableRun, "sink" ), sinkName );
//        assertEquals( TestHelpers.accessString(userCableRun, "channel"), "" );
//        assertEquals( TestHelpers.accessString(userCableRun, "routing"), "" );
//    }
//
//    @Test
//    public void storesOptionalAttributes() throws Exception {
//        element.setAttribute("channel", channel);
//        element.setAttribute("routing", validRouting);
//        UserCableRun userCableRun=new UserCableRun( element );
//
//        assertEquals( TestHelpers.accessString( userCableRun, "signal" ), signalName );
//        assertEquals( TestHelpers.accessString( userCableRun, "source" ), sourceName );
//        assertEquals( TestHelpers.accessString( userCableRun, "sink" ), sinkName );
//        assertEquals( TestHelpers.accessString(userCableRun, "channel"), channel );
//        assertEquals( TestHelpers.accessString(userCableRun, "routing"), validRouting );
//    }
//
//    @Test
//    public void storesLuminaireAttributes() throws Exception {
//        luminaireToLuminaireElement.setAttribute("channel", channel);
//        luminaireToLuminaireElement.setAttribute("routing", validRouting);
//        UserCableRun userCableRun = new UserCableRun( luminaireToLuminaireElement );
//
//        assertEquals( TestHelpers.accessString( userCableRun, "signal" ), signalName );
//        assertEquals( TestHelpers.accessString( userCableRun, "source" ), sourceLuminaireId );
//        assertEquals( TestHelpers.accessString( userCableRun, "sink" ), sinkLuminaireId );
//        assertEquals( TestHelpers.accessString(userCableRun, "channel"), channel );
//        assertEquals( TestHelpers.accessString(userCableRun, "routing"), validRouting );
//    }
//
//    @Test
//    public void storesCableRunReference() throws Exception {
//        UserCableRun instance = new UserCableRun( element );
//
//        CableRun cableRun = instance.cableRun();
//        assertNotNull( cableRun );
//
//        Object object = TestHelpers.accessObject( instance, "cableRun" );
//        assert CableRun.class.isInstance( object );
//        assertSame( cableRun, object );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun is missing required 'signal', 'source', and 'sink' attributes.")
//    public void noRequiredAttributes() throws Exception {
//        element.removeAttribute("signal");
//        element.removeAttribute("source");
//        element.removeAttribute("sink");
//        new UserCableRun(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun from "+sourceName+" to "+sinkName+" is missing required 'signal' attribute.")
//    public void noSignal() throws Exception {
//        element.removeAttribute("signal");
//        new UserCableRun(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun of "+signalName+" to "+sinkName+" is missing required 'source' attribute.")
//    public void noSource() throws Exception {
//        element.removeAttribute("source");
//        new UserCableRun(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun of "+signalName+" from "+sourceName+" is missing required 'sink' attribute.")
//    public void noSink() throws Exception {
//        element.removeAttribute("sink");
//        new UserCableRun(element);
//    }
//
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun to "+sinkName+" is missing required 'signal' and 'source' attributes.")
//    public void noSignalSource() throws Exception {
//        element.removeAttribute("signal");
//        element.removeAttribute("source");
//        new UserCableRun(element);
//    }
//
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun from "+sourceName+" is missing required 'signal' and 'sink' attributes.")
//    public void noSignalSink() throws Exception {
//        element.removeAttribute("signal");
//        element.removeAttribute("sink");
//        new UserCableRun(element);
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun of "+signalName+" is missing required 'source' and 'sink' attributes.")
//    public void noSourceSink() throws Exception {
//        element.removeAttribute("source");
//        element.removeAttribute("sink");
//        new UserCableRun(element);
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp =
//                    "CableRun from "+sourceName+" to "+sinkName+" has invalid routing attribute '"+invalidRouting+"'.")
//    public void invalidRouting() throws Exception {
//        element.setAttribute("routing", invalidRouting);
//        new UserCableRun(element);
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
//        TestResets.WallReset();
//        TestResets.DeviceReset();
//        TestResets.ElementalListerReset();
//        TestResets.StackableReset();
////        TestResets.DeviceReset();
//        TestResets.YokeableReset();
//        TestResets.LuminaireReset();
//
//        venueElement = new IIOMetadataNode( "venue" );
//        venueElement.setAttribute( "room", "Test Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        new Venue( venueElement );
//
//        wallElement1 = new IIOMetadataNode( "wall" );
//        wallElement1.setAttribute( "x1", "0" );
//        wallElement1.setAttribute( "y1", "0" );
//        wallElement1.setAttribute( "x2", "350" );
//        wallElement1.setAttribute( "y2", "0" );
//        Wall wall1 = new Wall( wallElement1 );
//
//        wallElement2 = new IIOMetadataNode( "wall" );
//        wallElement2.setAttribute( "x1", "350" );
//        wallElement2.setAttribute( "y1", "0" );
//        wallElement2.setAttribute( "x2", "350" );
//        wallElement2.setAttribute( "y2", "400" );
//        Wall wall2 = new Wall( wallElement2 );
//
//        wallElement3 = new IIOMetadataNode( "wall" );
//        wallElement3.setAttribute( "x1", "350" );
//        wallElement3.setAttribute( "y1", "400" );
//        wallElement3.setAttribute( "x2", "0" );
//        wallElement3.setAttribute( "y2", "400" );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wallElement4 = new IIOMetadataNode( "wall" );
//        wallElement4.setAttribute( "x1", "0" );
//        wallElement4.setAttribute( "y1", "400" );
//        wallElement4.setAttribute( "x2", "0" );
//        wallElement4.setAttribute( "y2", "0" );
//        Wall wall4 = new Wall( wallElement4 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//        wall4.verify();
//
//        tableElement = new IIOMetadataNode("table");
//        tableElement.setAttribute("id", tableName);
//        tableElement.setAttribute("width", "1");
//        tableElement.setAttribute("depth", "2");
//        tableElement.setAttribute("height", "3");
//        tableElement.setAttribute("x", tableX.toString() );
//        tableElement.setAttribute("y", tableY.toString() );
//        tableElement.setAttribute("z", tableZ.toString() );
//        new Table( tableElement );
//
//        tableElement2 = new IIOMetadataNode("table");
//        tableElement2.setAttribute("id", table2Name );
//        tableElement2.setAttribute("width", "1");
//        tableElement2.setAttribute("depth", "2");
//        tableElement2.setAttribute("height", "3");
//        tableElement2.setAttribute("x", table2X.toString() );
//        tableElement2.setAttribute("y", table2Y.toString() );
//        tableElement2.setAttribute("z", table2Z.toString() );
//        new Table( tableElement2 );
//
//        templateElement = new IIOMetadataNode("device-template");
//        templateElement.setAttribute("type", templateName );
//        templateElement.setAttribute("width", "7");
//        templateElement.setAttribute("depth", "8");
//        templateElement.setAttribute("height", "9");
//        new DeviceTemplate( templateElement );
//
//        sourceElement = new IIOMetadataNode( "device" );
//        sourceElement.setAttribute( "id", sourceName );
//        sourceElement.setAttribute( "on", tableName );
//        sourceElement.setAttribute( "is", templateName );
//        sourceDevice = new Device( sourceElement );
//        sourceDevice.verify();
//
//        sinkElement = new IIOMetadataNode( "device" );
//        sinkElement.setAttribute( "id", sinkName );
//        sinkElement.setAttribute( "on", table2Name );
//        sinkElement.setAttribute( "is", templateName );
//        sinkDevice = new Device( sinkElement );
//        sinkDevice.verify();
//
//        element = new IIOMetadataNode("cable-run");
//        element.setAttribute("signal", signalName );
//        element.setAttribute("source", sourceName );
//        element.setAttribute("sink", sinkName );
//
//        Integer width = 13;
//        Integer length = 27;
//        Double weight = 17.6;
//        Element definitionElement = new IIOMetadataNode( "luminaire-definition" );
//        definitionElement.setAttribute( "name", luminaireType );
//        definitionElement.setAttribute( "width", width.toString() );
//        definitionElement.setAttribute("length", length.toString());
//        definitionElement.setAttribute( "weight", weight.toString() );
//        definitionElement.appendChild( new IIOMetadataNode( "svg" ) );
//        luminaireDefinition = new LuminaireDefinition( definitionElement );
//        luminaireDefinition.verify();
//
//        Element lightingStandElement = new IIOMetadataNode( "lighting-stand" );
//        lightingStandElement.setAttribute( "id", standId );
//        lightingStandElement.setAttribute( "x", standX.toString() );
//        lightingStandElement.setAttribute( "y", standY.toString() );
//        lightingStand = new LightingStand( lightingStandElement );
//        lightingStand.verify();
//
//        sourceLuminaireElement = new IIOMetadataNode( "luminaire" );
//        sourceLuminaireElement.setAttribute( "on", standId );
//        sourceLuminaireElement.setAttribute( "type", luminaireType );
//        sourceLuminaireElement.setAttribute( "unit", sourceLuminaireUnit );
//        sourceLuminaireElement.setAttribute( "location", sourceLuminaireLocation );
//        sourceLuminaire = new Luminaire( sourceLuminaireElement );
//        sourceLuminaire.verify();
//
//        sinkLuminaireElement = new IIOMetadataNode( "luminaire" );
//        sinkLuminaireElement.setAttribute( "on", standId );
//        sinkLuminaireElement.setAttribute( "type", luminaireType );
//        sinkLuminaireElement.setAttribute( "unit", sinkLuminaireUnit );
//        sinkLuminaireElement.setAttribute( "location", sinkLuminaireLocation );
//        sinkLuminaire = new Luminaire( sinkLuminaireElement );
//        sinkLuminaire.verify();
//
//        Element cableTypeElement = new IIOMetadataNode( "cable-type" );
//        cableTypeElement.setAttribute( "id", signalName );
//        cableTypeElement.setAttribute( "schematic-color", color );
//        new CableType( cableTypeElement );
//
//        deviceToLuminaireElement = new IIOMetadataNode( "cable-run" );
//        deviceToLuminaireElement.setAttribute( "signal", signalName );
//        deviceToLuminaireElement.setAttribute( "source", sourceName );
//        deviceToLuminaireElement.setAttribute("sink", sinkLuminaireId);
//
//        luminaireToLuminaireElement = new IIOMetadataNode( "cable-run" );
//        luminaireToLuminaireElement.setAttribute( "signal", signalName );
//        luminaireToLuminaireElement.setAttribute( "source", sourceLuminaireId );
//        luminaireToLuminaireElement.setAttribute( "sink", sinkLuminaireId );
//
//        luminaireToDeviceElement = new IIOMetadataNode( "cable-run" );
//        luminaireToDeviceElement.setAttribute( "signal", signalName );
//        luminaireToDeviceElement.setAttribute( "source", sourceLuminaireId );
//        luminaireToDeviceElement.setAttribute( "sink", sinkName );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
////        solid=null;
//    }
//
//}
