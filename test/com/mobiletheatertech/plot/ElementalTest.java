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
        String stringValue;
        Integer integerValue;
        Integer positiveIntegerValue;
        Integer unset;
        Integer empty;
        Integer used;
        Double twiceUnset;
        Double twiceEmpty;
        Double twiceUsed;

        public Ellie( Element element ) throws AttributeMissingException,InvalidXMLException {
            super ( element );

            id = getOptionalStringAttribute( element, "id" );
            stringValue = getStringAttribute( element, "stringValue" );
            empty = getOptionalIntegerAttribute( element, "empty" );
            unset = getOptionalIntegerAttribute( element, "unset" );
            used = getOptionalIntegerAttribute( element, "used" );
            integerValue = getIntegerAttribute( element, "integerValue" );
            positiveIntegerValue=getPositiveIntegerAttribute(element,"positiveIntegerValue");
            twiceUnset = getOptionalDoubleAttribute( element, "twiceUnset");
            twiceEmpty = getOptionalDoubleAttribute( element, "twiceEmpty" );
            twiceUsed = getOptionalDoubleAttribute( element, "twiceUsed");
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

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Gearlie element unexpectedly null!" )
    public void nullElement() throws Exception {
        new Ellie( null );
    }

    @Test
    public void hasId() throws Exception {
        Ellie foo = new Ellie( element );
//        stringValue.getClass().getDeclaredField( "id" );
        foo.getClass().getField( "id" );
    }

    @Test
    public void getStringAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals(TestHelpers.accessString(ellie, "stringValue"), "6x9");
    }

    @Test
    public void getIntegerAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger(ellie, "integerValue"), (Integer) 609 );
    }

    @Test
    public void getPositiveIntegerAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "positiveIntegerValue" ), (Integer) 1 );
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

    @Test
    public void getOptionalDoubleAttributeEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble( ellie, "twiceEmpty" ), (Double) 0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble( ellie, "twiceUnset" ), (Double) 0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble( ellie, "twiceUsed" ), (Double) 4.32 );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Gearlie instance is missing required 'stringValue' attribute.")
    public void noStringAttribute() throws Exception {
        element.removeAttribute( "stringValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Gearlie \\(frank\\) is missing required 'stringValue' attribute.")
    public void noStringAttributeWithID() throws Exception {
        element.setAttribute( "id", "frank" );
        element.removeAttribute( "stringValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Gearlie instance is missing required 'integerValue' attribute.")
    public void noIntegerAttribute() throws Exception {
        element.removeAttribute( "integerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Gearlie \\(sally\\) is missing required 'integerValue' attribute.")
    public void noIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "integerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Gearlie \\(sally\\) is missing required 'positiveIntegerValue' attribute.")
    public void noPositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "positiveIntegerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp ="Gearlie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
//                    "Gearlie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
    public void negativePositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.setAttribute( "positiveIntegerValue","-1" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Gearlie \\(sally\\) value for 'positiveIntegerValue' attribute should not be zero.")
    public void zeroPositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.setAttribute( "positiveIntegerValue","0" );
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
        element.setAttribute( "stringValue", "6x9" );
        element.setAttribute( "integerValue", "609" );
        element.setAttribute( "positiveIntegerValue", "1");
        element.setAttribute( "used", "17" );
        element.setAttribute( "empty", "" );
        element.setAttribute( "twiceUsed", "4.32" );
        element.setAttribute( "twiceEmpty", "" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}