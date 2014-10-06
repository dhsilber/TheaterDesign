package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;

/**
 * Test {@code Parse}.
 *
 * @author dhs
 * @since 0.0.1
 */
public class ParseTest {

    private String layerId = "LayerID";
    private String layername = "Layer Name";
//
//    @Test
//    public void parseNoContent() throws Exception {
//        String xml = "<plot>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 0 );
//    }
//
//    @Test
//    public void createsLayer() throws Exception {
//        String xml = "<plot>" +
//                "<layer id=\""+layerId+"\" name=\""+layername+"\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        // Initial size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        int size = list.size();
//
//        new Parse( stream );
//
//        // Final size of list
//        assertEquals( list.size(), size + 1 );
//        ElementalLister thing = list.get( list.size() - 1 );
//
//        assert Elemental.class.isInstance( thing );
//        assert UserLayer.class.isInstance( thing );
//    }
//
//    @Test
//    public void createsLuminaireDefinition() throws Exception {
//        String xml = "<plot>" +
//                "<luminaire-definition name=\"6x9\">" +
//                "<svg>" +
//                "<path fill=\"none\" stroke=\"black\" stroke-width=\"2\"" +
//                "    d=\"M -16 76 L -16 33 L -22 20 C -22 10 -22 -20 -12 -20 L -12 -40 L 12 -40 L 12 -20 C 22 -20 22 10 22 20 L 16 33 L 16 76 \"" +
//                "    />" +
//                "</svg>" +
//                "</luminaire-definition>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        // Initial size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        int size = list.size();
//
//        new Parse( stream );
//
//        // Final size of list
//        assertEquals( list.size(), size + 1 );
//        ElementalLister thing = list.get( list.size() - 1 );
//
//        assert MinderDom.class.isInstance( thing );
//        assert LuminaireDefinition.class.isInstance( thing );
//    }
//
//    @Test
//    public void createsVenue() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        // Initial size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        int size = list.size();
//
//        new Parse( stream );
//
//        // Final size of list
//        assertEquals( list.size(), size + 1 );
//        ElementalLister thing = list.get( list.size() - 1 );
//
//        assert Minder.class.isInstance( thing );
//        assert Venue.class.isInstance( thing );
//    }
//
//    @Test
//    public void createsVenueAndStage() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" />" +
//                "<stage width=\"12\" depth=\"15\" x=\"20\" y=\"30\" z=\"11\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister stage = list.get( 1 );
//        assert Minder.class.isInstance( stage );
//        assert Stage.class.isInstance( stage );
//    }
//
//    @Test
//    public void createsVenueAndHangPoint() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<hangpoint x=\"20\" y=\"30\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister hangpoint = list.get( 1 );
//        assert MinderDom.class.isInstance( hangpoint );
//        assert HangPoint.class.isInstance( hangpoint );
//    }
//
//    @Test
//    public void createsVenueAndProscenium() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"480\" depth=\"720\" height=\"240\" >" +
//                "<proscenium width=\"300\" depth=\"23\" height=\"180\" x=\"200\" y=\"30\" z=\"0\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister minderProscenium = list.get( 1 );
//        assert Minder.class.isInstance( minderProscenium );
//        assert Proscenium.class.isInstance( minderProscenium );
////        Proscenium proscenium = (Proscenium) minderProscenium;
//        assertEquals( Proscenium.Origin(), new Point( 200, 30, 0 ) );
//
//    }
//
//    @Test
//    public void createsVenueAndTwoHangPoint() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<hangpoint x=\"20\" y=\"30\" />" +
//                "<hangpoint x=\"25\" y=\"35\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 3 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister hangpoint = list.get( 1 );
//        assert MinderDom.class.isInstance( hangpoint );
//        assert HangPoint.class.isInstance( hangpoint );
//
//        ElementalLister hangpoint2 = list.get( 2 );
//        assert MinderDom.class.isInstance( hangpoint2 );
//        assert HangPoint.class.isInstance( hangpoint2 );
//
//        assertNotSame( hangpoint, hangpoint2 );
//    }
//
//    @Test
//    public void createsVenueAndPipe() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<pipe id=\"fred\" length=\"24\" x=\"20\" y=\"30\" z=\"34\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister pipe = list.get( 1 );
//        assert MinderDom.class.isInstance( pipe );
//        assert Pipe.class.isInstance( pipe );
//    }
//
//    @Test
//    public void createsVenueAndWall() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<wall x1=\"24\" y1=\"20\" x2=\"30\" y2=\"34\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister wall = list.get( 1 );
//        assert MinderDom.class.isInstance( wall );
//        assert Wall.class.isInstance( wall );
//    }
//
//    @Test
//    public void createsVenueAndBalcony() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<balcony floor-height=\"24\" under-height=\"20\" wall-height=\"30\" rail-height=\"34\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister balcony = list.get( 1 );
//        assert MinderDom.class.isInstance( balcony );
//        assert Balcony.class.isInstance( balcony );
//    }
//
//    @Test
//    public void createsVenueAndTwoPipe() throws Exception {
//        String xml = "<plot>" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<pipe id=\"james\" length=\"24\" x=\"20\" y=\"30\" z=\"34\" />" +
//                "<pipe id=\"martha\" length=\"24\" x=\"25\" y=\"35\" z=\"34\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
//        assertEquals( list.size(), 3 );
//
//        ElementalLister venue = list.get( 0 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister pipe = list.get( 1 );
//        assert MinderDom.class.isInstance( pipe );
//        assert Pipe.class.isInstance( pipe );
//
//        ElementalLister pipe2 = list.get( 2 );
//        assert MinderDom.class.isInstance( pipe2 );
//        assert Pipe.class.isInstance( pipe2 );
//
//        assertNotSame( pipe, pipe2 );
//    }

    @Test
    public void createsDeviceTemplate() throws Exception {
        String xml = "<plot>" +
                "<device-template type=\"intercom station\" width=\"1.2\" depth=\"3.4\" height=\"5.6\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        // TODO Takes too long
//        new Parse( stream );

        // Final size of list
        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals( list.size(), 3 );

        ElementalLister devicetemplate = list.get( 0 );
        assert Elemental.class.isInstance( devicetemplate );
        assert DeviceTemplate.class.isInstance( devicetemplate );
    }

//    @Test
//    public void createsDeviceOnTable() throws Exception {
//        String xml = "<plot>" +
//                "<device-template type=\"Clear-Com RS-100A\" width=\"2.75\" depth=\"1.6\" height=\"4.9\" />" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<table id=\"control\" width=\"60\" depth=\"32\" height=\"36\" x=\"25\" y=\"35\" z=\"34\" />" +
//                "<device id=\"intercom station\" on=\"control\" is=\"Clear-Com RS-100A\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
////        assertEquals( list.size(), 3 );
//
//        ElementalLister devicetemplate = list.get( 0 );
//        assert Elemental.class.isInstance( devicetemplate );
//        assert DeviceTemplate.class.isInstance( devicetemplate );
//
//        ElementalLister venue = list.get( 1 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister table = list.get( 2 );
//        assert MinderDom.class.isInstance( table );
//        assert Table.class.isInstance( table );
//
//        ElementalLister device = list.get( 3 );
//        assert MinderDom.class.isInstance( device );
//        assert Device.class.isInstance( device );
//
//        assertNotSame( table, device );
//    }
//
//    @Test
//    public void createsTwoConnectedDevices() throws Exception {
//        String xml = "<plot>" +
//                "<device-template type=\"Clear-Com RS-100A\" width=\"2.75\" depth=\"1.6\" height=\"4.9\" />" +
//                "<venue room=\"Bogus name\" width=\"330\" depth=\"132\" height=\"90\" >" +
//                "<table id=\"control\" width=\"60\" depth=\"32\" height=\"36\" x=\"25\" y=\"35\" z=\"34\" />" +
//                "<device id=\"intercom station one\" on=\"control\" is=\"Clear-Com RS-100A\" />" +
//                "<device id=\"intercom station two\" on=\"control\" is=\"Clear-Com RS-100A\" />" +
//                "<cable-run signal=\"ClearCom\" source=\"intercom station one\" sink=\"intercom station two\" />" +
//                "</venue>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<ElementalLister> list = Drawable.List();
////        assertEquals( list.size(), 3 );
//
//        ElementalLister devicetemplate = list.get( 0 );
//        assert Elemental.class.isInstance( devicetemplate );
//        assert DeviceTemplate.class.isInstance( devicetemplate );
//
//        ElementalLister venue = list.get( 1 );
//        assert Minder.class.isInstance( venue );
//        assert Venue.class.isInstance( venue );
//
//        ElementalLister table = list.get( 2 );
//        assert MinderDom.class.isInstance( table );
//        assert Table.class.isInstance( table );
//
//        ElementalLister device = list.get( 3 );
//        assert MinderDom.class.isInstance( device );
//        assert Device.class.isInstance( device );
//
//        ElementalLister device2 = list.get( 4 );
//        assert MinderDom.class.isInstance( device2 );
//        assert Device.class.isInstance( device2 );
//
//        ElementalLister cableRun = list.get( 5 );
//        assert MinderDom.class.isInstance( cableRun );
//        assert CableRun.class.isInstance( cableRun );
//
//        assertNotSame( device, device2 );
//    }
//
//    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
////    @Test
////    public void createsLuminaire() throws Exception {
////        String xml = "<plot>" +
////                "<luminaire type=\"6x9\" on=\"lineset 4\" location=\"12\" target=\"DSC\" />" +
////                "</plot>";
////        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
////
////        // Initial size of list
////        ArrayList<MinderDom> list = Drawable.List();
////        int size = list.size();
////
////        new Parse( stream );
////
////        // Final size of list
////        assertEquals( list.size(), size + 1 );
////        MinderDom thing = list.get( list.size() - 1 );
////
////        assert MinderDom.class.isInstance( thing );
////        assert Luminaire.class.isInstance( thing );
////    }
//
//    @Test
//    public void createsSetup() throws Exception {
//        String xml = "<plot>" +
//                "<setup name=\"Try this\" tag=\"flavor\" />" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        new Parse( stream );
//
//        assertEquals( Setup.List(),
//                      "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" checked=\"checked\" value=\"" +
//                              "flavor\" />" +
//                              "Try this<br />\n" );
//    }
//
//    /**
//     * @throws Exception
//     */
//    @Test(expectedExceptions = InvalidXMLException.class,
//          expectedExceptionsMessageRegExp = "Top level diversionElement must be 'plot'.")
//    public void noPlot() throws Exception {
//        String xml = "<pot><thingy  /></pot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        new Parse( stream );
//    }
//
//    @Test(expectedExceptions = InvalidXMLException.class,
//          expectedExceptionsMessageRegExp = "Error in parsing Plot XML description.")
//    public void noXML() throws Exception {
//        String xml = "";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        new Parse( stream );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
//        TestResets.MinderDomReset();
//        TestResets.MountableReset();
//        TestResets.ProsceniumReset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}