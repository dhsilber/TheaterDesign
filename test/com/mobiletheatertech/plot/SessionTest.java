package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 12:18 AM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.20
 */
public class SessionTest {

    Session show;

    Element element = null;
    String setup = "SetupName";
    String name = "Session Name";

    public SessionTest() {
    }

    @Test
    public void isElemental() throws Exception {
        Session show = new Session( element );

        assert Elemental.class.isInstance( show );
    }

    @Test
    public void storesAttributes() throws Exception {
        element.removeAttribute( "setup" );

        Session show = new Session( element );

        assertEquals( TestHelpers.accessString( show, "id" ), name );
        assertEquals( TestHelpers.accessString( show, "setup" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        assertEquals( TestHelpers.accessString( show, "id" ), name );
        assertEquals( TestHelpers.accessString( show, "setup" ), setup );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Session instance is missing required 'name' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "name" );
        new Session( element );
    }

    @Test
    public void parseXML() {
        // See ParseTest.createsShow*()
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode();
        element.setAttribute( "setup", setup );
        element.setAttribute( "name", name );

        show = new Session( element );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}