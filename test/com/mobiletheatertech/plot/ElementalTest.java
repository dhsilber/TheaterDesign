package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Test {@code Elemental}
 *
 * @author dhs
 * @since 0.0.7
 */
public class ElementalTest {

    /**
     * Extended {@code Elemental} so that there is a concrete class to test with.
     */
    private class Ellie extends Elemental {
        String foo;
        Integer fuu;
        Integer unset;
        Integer empty;
        Integer used;

        public Ellie( Element element ) throws AttributeMissingException {
            id = getOptionalStringAttribute( element, "id" );
            foo = getStringAttribute( element, "foo" );
            empty = getOptionalIntegerAttribute( element, "empty" );
            unset = getOptionalIntegerAttribute( element, "unset" );
            used = getOptionalIntegerAttribute( element, "used" );
            fuu = getIntegerAttribute( element, "fuu" );
        }
    }

    Element element = null;

    public ElementalTest() {
    }

    @Test
    public void isElemental() throws Exception {
        Ellie ellie = new Ellie( element );

        assert Elemental.class.isInstance( ellie );
    }

    @Test
    public void hasId() throws Exception {
        Ellie foo = new Ellie( element );
//        foo.getClass().getDeclaredField( "id" );
        foo.getClass().getField( "id" );
    }

    @Test
    public void getStringAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessString( ellie, "foo" ), "6x9" );
    }

    @Test
    public void getIntegerAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "fuu" ), (Integer) 609 );
    }

    @Test
    public void getOptionalIntegerAttributeEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "empty" ), (Integer) 0 );
    }

    @Test
    public void getOptionalIntegerAttributeUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "unset" ), (Integer) 0 );
    }

    @Test
    public void getOptionalIntegerAttributeUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "used" ), (Integer) 17 );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie instance is missing required 'foo' attribute.")
    public void noStringAttribute() throws Exception {
        element.removeAttribute( "foo" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie \\(frank\\) is missing required 'foo' attribute.")
    public void noStringAttributeWithID() throws Exception {
        element.setAttribute( "id", "frank" );
        element.removeAttribute( "foo" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie instance is missing required 'fuu' attribute.")
    public void noIntegerAttribute() throws Exception {
        element.removeAttribute( "fuu" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie \\(sally\\) is missing required 'fuu' attribute.")
    public void noIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "fuu" );
        new Ellie( element );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "elemental" );
        element.setAttribute( "foo", "6x9" );
        element.setAttribute( "fuu", "609" );
        element.setAttribute( "used", "17" );
        element.setAttribute( "empty", "" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}