package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 11:40 AM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.18
 */
public class SetupTest {

    Element element = null;

    String name = "Name text";
    String tag = "Tag text";

    @Test
    public void isElemental() throws Exception {
        Setup setup = new Setup( element );

        assert Elemental.class.isInstance( setup );
    }

    @Test
    public void storesAttributes() throws Exception {
        Setup setup = new Setup( element );

        assertEquals( TestHelpers.accessString( setup, "name" ), name );
        assertEquals( TestHelpers.accessString( setup, "tag" ), tag );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Setup instance is missing required 'name' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "name" );
        new Setup( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Setup instance is missing required 'tag' attribute." )
    public void noTag() throws Exception {
        element.removeAttribute( "tag" );
        new Setup( element );
    }

    @Test
    public void providesListWithOne() throws Exception {
        new Setup( element );

        String thing = Setup.List();

        assertEquals( thing,
                      "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" checked=\"checked\" value=\"" +
                              tag + "\" />" +
                              name + "<br />\n" );
    }

    @Test
    public void providesListWithMultiple() throws Exception {
        new Setup( element );

        String name2 = "Second name";
        String tag2 = "Tag number two";
        Element element2 = new IIOMetadataNode( "setup" );
        element2.setAttribute( "name", name2 );
        element2.setAttribute( "tag", tag2 );
        new Setup( element2 );

        String thing = Setup.List();

        assertEquals( thing,
                      "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" checked=\"checked\" value=\"" +
                              tag + "\" />" +
                              name + "<br />\n" +
                              "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" value=\"" +
                              tag2 + "\" />" +
                              name2 + "<br />\n" );
    }

//    @Test
//    public void finds() throws Exception {
//        Setup setup = new Setup( elementOnPipe );
//
//        HangPoint found = HangPoint.Find( "Blather" );
//
//        assertSame( found, hangPoint );
//    }
//
//    @Test
//    public void findsNothing() throws Exception {
//        HangPoint found = HangPoint.Find( "Nothing" );
//
//        assertNull( found );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.SetupReset();

        element = new IIOMetadataNode( "setup" );
        element.setAttribute( "name", name );
        element.setAttribute( "tag", tag );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
