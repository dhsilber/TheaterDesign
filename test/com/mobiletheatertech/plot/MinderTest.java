package com.mobiletheatertech.plot;

import mockit.Expectations;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.util.ArrayList;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * Test {@code Minder}
 *
 * @author dhs
 * @since 0.0.2
 */
public class MinderTest {

    /**
     * Extended {@code Minder} so that there is a concrete class to test with.
     */
    private class Minded extends Minder {

        public Minded( Element element ) throws InvalidXMLException {
            super( element );
        }

        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void drawPlan( Graphics2D draw ) {
            throw new UnsupportedOperationException(
                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void drawSection( Graphics2D draw ) {
            throw new UnsupportedOperationException(
                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void drawFront( Graphics2D draw ) {
            throw new UnsupportedOperationException(
                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void dom( Draw draw, View mode ) {
            throw new UnsupportedOperationException(
                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private static Draw draw = null;
    private Element element = null;

    public MinderTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        Minded foo = new Minded( element );

        assert MinderDom.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        Minded foo = new Minded( element );
        ArrayList<MinderDom> thing = Drawable.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( foo );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp = "Element unexpectedly null!" )
    public void NullElement() throws Exception {
        new Minded( null );
    }

    @Test
    public void drawAllList() {
        fail( "Test that drawAll finds all of the plot items and invokes their drawPlan() method." );
    }

    @Test
    public void drawAllTransform() {
        fail( "Test that before drawing anything, drawAll invokes transform with appropriate settings from point." );
    }

    @Test
    public void drawAll() throws Exception {
        fail( "Test passes falsely" );

        final Graphics2D canvas = draw.canvas();
        Element element = new IIOMetadataNode();
        element.setAttribute( "name", "Phooy" );
        element.setAttribute( "width", "30" );
        element.setAttribute( "depth", "14" );
        element.setAttribute( "height", "4" );
        final Venue venue = new Venue( element );
//        final Minded foo = new Minded();

        new Expectations() {
            {
                venue.drawPlan( canvas );
//                foo.drawPlan( canvas );
//                drawPlan.transform( 1, 2 );
            }
        };
        Minder.DrawAllPlan( canvas );
    }

    @Test
    public void drawProvidedWithCanvas() {
        fail( "Must create test" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        draw = new Draw();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "bogus" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}