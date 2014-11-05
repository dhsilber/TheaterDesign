package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
        Double twiceUnsetZero;
        Double twiceEmptyZero;
        Double twiceUsedZero;
        Double twiceZeroZero;
        Double twiceUnsetNull;
        Double twiceEmptyNull;
        Double twiceUsedNull;
        Double twiceZeroNull;
        String stringNull;
        String stringNotNull;
        String stringEmpty;
        String stringNotEmpty;

        public Ellie( Element element ) throws AttributeMissingException,InvalidXMLException {
            super ( element );

            id = getOptionalStringAttribute( element, "id" );
            stringValue = getStringAttribute(element, "stringValue");
            empty = getOptionalIntegerAttribute(element, "empty");
            unset = getOptionalIntegerAttribute(element, "unset");
            used = getOptionalIntegerAttribute(element, "used");
            integerValue = getIntegerAttribute(element, "integerValue");
            positiveIntegerValue=getPositiveIntegerAttribute(element, "positiveIntegerValue");
            twiceUnsetZero = getOptionalDoubleAttributeOrZero(element, "twiceUnset");
            twiceEmptyZero = getOptionalDoubleAttributeOrZero(element, "twiceEmpty");
            twiceUsedZero = getOptionalDoubleAttributeOrZero(element, "twiceUsed");
            twiceZeroZero = getOptionalDoubleAttributeOrZero( element, "twiceZero" );
            twiceUnsetNull = getOptionalDoubleAttributeOrNull(element, "twiceUnset");
            twiceEmptyNull = getOptionalDoubleAttributeOrNull(element, "twiceEmpty");
            twiceUsedNull = getOptionalDoubleAttributeOrNull(element, "twiceUsed");
            twiceZeroNull = getOptionalDoubleAttributeOrNull( element, "twiceZero" );
            stringNotEmpty = getOptionalStringAttribute(element, "stringNotEmpty");
            stringEmpty = getOptionalStringAttribute(element, "stringEmpty");
            stringNotNull = getOptionalStringAttributeOrNull(element, "stringNotNull");
            stringNull = getOptionalStringAttributeOrNull( element, "stringNull" );
        }
    }

    Element element = null;

    public ElementalTest() {
    }

    @Test
    public void isa() throws Exception {
        Ellie ellie = new Ellie( element );

        assert Elemental.class.isInstance( ellie );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Ellie element unexpectedly null!" )
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

        assertEquals(TestHelpers.accessInteger(ellie, "integerValue"), (Integer) 609);
    }

    @Test
    public void getPositiveIntegerAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals(TestHelpers.accessInteger(ellie, "positiveIntegerValue"), (Integer) 1);
    }

    @Test
    public void getOptionalIntegerAttributeEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals(TestHelpers.accessInteger(ellie, "empty"), (Integer) 0);
    }

    @Test
    public void getOptionalIntegerAttributeUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger(ellie, "unset"), (Integer) 0 );
    }

    @Test
    public void getOptionalIntegerAttributeUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "used" ), (Integer) 17 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceEmptyZero"), null );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUnsetZero"), 0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroZero() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceZeroZero"), (Double) 0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUsedZero"), (Double) 4.32 );
    }


    @Test
    public void getOptionalDoubleAttributeOrNullEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceEmptyNull"), null );
    }

    @Test
    public void getOptionalDoubleAttributeOrNullUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUnsetNull"), null );
    }

    @Test
    public void getOptionalDoubleAttributeOrNullZero() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceZeroNull"), (Double) 0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrNullUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUsedNull"), (Double) 4.32 );
    }

    @Test
    public void getOptionalStringAttributeUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessString(ellie, "stringNotEmpty"), "Not Empty" );
    }

    @Test
    public void getOptionalStringAttributeUnused() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessString(ellie, "stringEmpty"), "" );
    }

    @Test
    public void getOptionalStringOrNullAttributeUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessString(ellie, "stringNotNull"), "Not Null" );
    }

    @Test
    public void getOptionalStringOrNullAttributeUnused() throws Exception {
        Ellie ellie = new Ellie( element );

        assertNull(TestHelpers.accessString(ellie, "stringNull"));
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie instance is missing required 'stringValue' attribute.")
    public void noStringAttribute() throws Exception {
        element.removeAttribute( "stringValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie \\(frank\\) is missing required 'stringValue' attribute.")
    public void noStringAttributeWithID() throws Exception {
        element.setAttribute( "id", "frank" );
        element.removeAttribute( "stringValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Ellie instance is missing required 'integerValue' attribute.")
    public void noIntegerAttribute() throws Exception {
        element.removeAttribute( "integerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie \\(sally\\) is missing required 'integerValue' attribute.")
    public void noIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "integerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie \\(sally\\) is missing required 'positiveIntegerValue' attribute.")
    public void noPositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "positiveIntegerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp ="Ellie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
//                    "Gearlie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
    public void negativePositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.setAttribute( "positiveIntegerValue","-1" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Ellie \\(sally\\) value for 'positiveIntegerValue' attribute should not be zero.")
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
        element.setAttribute( "twiceZero", "0.0" );
        element.setAttribute( "stringNotEmpty", "Not Empty" );
        element.setAttribute( "stringNotNull", "Not Null" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}