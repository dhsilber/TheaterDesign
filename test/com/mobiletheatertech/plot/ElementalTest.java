package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
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
        Integer unsetZero;
        Integer emptyZero;
        Integer usedZero;
        Integer unsetNull;
        Integer emptyNull;
        Integer usedNull;

        Double twiceUnsetZero;
        Double twiceEmptyZero;
        Double twiceUsedZero;
        Double twiceZeroZero;
        Double twiceUnsetNull;
        Double twiceEmptyNull;
        Double twiceUsedNull;
        Double twiceZeroNull;

        Double doubleValue;
        Double positiveDoubleValue;

        String stringNull;
        String stringNotNull;
        String stringEmpty;
        String stringNotEmpty;

        public Ellie( Element element ) throws AttributeMissingException,InvalidXMLException {
            super ( element );

            id = getOptionalStringAttribute( "id" );
            stringValue = getStringAttribute( "stringValue" );

            emptyZero = getOptionalIntegerAttributeOrZero( "empty" );
            unsetZero = getOptionalIntegerAttributeOrZero( "unset" );
            usedZero  = getOptionalIntegerAttributeOrZero( "used" );
            emptyNull = getOptionalIntegerAttributeOrNull( "empty" );
            unsetNull = getOptionalIntegerAttributeOrNull( "unset" );
            usedNull  = getOptionalIntegerAttributeOrNull( "used" );
            integerValue = getIntegerAttribute( "integerValue" );
            positiveIntegerValue=getPositiveIntegerAttribute( "positiveIntegerValue");

            twiceUnsetZero = getOptionalDoubleAttributeOrZero( "twiceUnset" );
            twiceEmptyZero = getOptionalDoubleAttributeOrZero( "twiceEmpty" );
            twiceUsedZero  = getOptionalDoubleAttributeOrZero( "twiceUsed" );
            twiceZeroZero  = getOptionalDoubleAttributeOrZero( "twiceZero" );
            twiceUnsetNull = getOptionalDoubleAttributeOrNull( "twiceUnset" );
            twiceEmptyNull = getOptionalDoubleAttributeOrNull( "twiceEmpty" );
            twiceUsedNull  = getOptionalDoubleAttributeOrNull( "twiceUsed" );
            twiceZeroNull  = getOptionalDoubleAttributeOrNull( "twiceZero" );

            doubleValue = getDoubleAttribute( "doubleValue");
            positiveDoubleValue = getPositiveDoubleAttribute( "positiveDoubleValue");

            stringNotEmpty = getOptionalStringAttribute( "stringNotEmpty");
            stringEmpty = getOptionalStringAttribute( "stringEmpty");
            stringNull    = getOptionalStringAttributeOrNull( "stringNull" );
            stringNotNull = getOptionalStringAttributeOrNull( "stringNotNull" );
        }
    }

    private class ElliePositiveDoubleNegative extends Elemental {

        Double negativeDoubleValue;

        public ElliePositiveDoubleNegative(Element element)
                throws AttributeMissingException, InvalidXMLException
        {
            super(element);

            id = getOptionalStringAttribute( "id" );
            negativeDoubleValue = getPositiveDoubleAttribute( "negativeDoubleValue");
        }
    }

    private class ElliePositiveDoubleZero extends Elemental {

        Double zeroDoubleValue;

        public ElliePositiveDoubleZero(Element element)
                throws AttributeMissingException, InvalidXMLException
        {
            super(element);

            id = getOptionalStringAttribute( "id" );
            zeroDoubleValue = getPositiveDoubleAttribute( "zeroDoubleValue");
        }
    }

    Element element = null;
    Element elementary = null;
    Element elementPositiveDoubleNegative = null;
    Element elementPositiveDoubleZero = null;

    String parentTag = "parentTag";

    public ElementalTest() {
    }

    @Test
    public void isa() throws Exception {
        Ellie ellie = new Ellie( element );

        assert HasID.class.isInstance( ellie );
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
    public void getOptionalIntegerAttributeOrZeroEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals(TestHelpers.accessInteger(ellie, "emptyZero"), (Integer) 0);
    }

    @Test
    public void getOptionalIntegerAttributeOrZeroUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger(ellie, "unsetZero"), (Integer) 0 );
    }

    @Test
    public void getOptionalIntegerAttributeOrZeroUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "usedZero" ), (Integer) 17 );
    }


    @Test
    public void getOptionalIntegerAttributeOrNullEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertNull(TestHelpers.accessInteger(ellie, "emptyNull") );
    }

    @Test
    public void getOptionalIntegerAttributeOrNullUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertNull( TestHelpers.accessInteger(ellie, "unsetNull") );
    }

    @Test
    public void getOptionalIntegerAttributeOrNullUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessInteger( ellie, "usedNull" ), (Integer) 17 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroEmpty() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceEmptyZero"), (Double)0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroUnset() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUnsetZero"), (Double)0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroZero() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceZeroZero"), (Double)0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrZeroUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUsedZero"), (Double)4.32 );
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

        assertEquals( TestHelpers.accessDouble(ellie, "twiceZeroNull"), (Double)0.0 );
    }

    @Test
    public void getOptionalDoubleAttributeOrNullUsed() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "twiceUsedNull"), (Double)4.32 );
    }

    @Test
    public void getDoubleAttribute() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "doubleValue"), (Double)12.3 );
    }

    @Test
    public void getPositiveDoubleAttributePositive() throws Exception {
        Ellie ellie = new Ellie( element );

        assertEquals( TestHelpers.accessDouble(ellie, "positiveDoubleValue"), (Double)45.6 );
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
            expectedExceptionsMessageRegExp = "Ellie instance is missing required 'positiveIntegerValue' attribute.")
    public void noPositiveIntegerAttribute() throws Exception {
        element.removeAttribute( "positiveIntegerValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie \\(sally\\) is missing required 'positiveIntegerValue' attribute.")
    public void noPositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "positiveIntegerValue" );
        new Ellie( element );
    }


    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie instance is missing required 'doubleValue' attribute.")
    public void noDoubleAttribute() throws Exception {
        element.removeAttribute( "doubleValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie \\(sally\\) is missing required 'doubleValue' attribute.")
    public void noDoubleAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "doubleValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie instance is missing required 'positiveDoubleValue' attribute.")
    public void noPositiveDoubleAttribute() throws Exception {
        element.removeAttribute( "positiveDoubleValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Ellie \\(sally\\) is missing required 'positiveDoubleValue' attribute.")
    public void noPositiveDoubleAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.removeAttribute( "positiveDoubleValue" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "ElliePositiveDoubleNegative instance value for 'negativeDoubleValue' attribute should not be negative.")
    public void getPositiveDoubleAttributeNegative() throws Exception {
        new ElliePositiveDoubleNegative( elementPositiveDoubleNegative );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "ElliePositiveDoubleNegative \\(sally\\) value for 'negativeDoubleValue' attribute should not be negative.")
    public void getPositiveDoubleAttributeNegativeWithID() throws Exception {
        elementPositiveDoubleNegative.setAttribute( "id", "sally" );
        new ElliePositiveDoubleNegative( elementPositiveDoubleNegative );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "ElliePositiveDoubleZero instance value for 'zeroDoubleValue' attribute should not be zero.")
    public void getPositiveDoubleAttributeZero() throws Exception {
        new ElliePositiveDoubleZero( elementPositiveDoubleZero );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "ElliePositiveDoubleZero \\(sally\\) value for 'zeroDoubleValue' attribute should not be zero.")
    public void getPositiveDoubleAttributeZeroWithID() throws Exception {
        elementPositiveDoubleZero.setAttribute( "id", "sally" );
        new ElliePositiveDoubleZero( elementPositiveDoubleZero );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Ellie instance value for 'positiveIntegerValue' attribute should not be negative.")
//                    "Gearlie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
    public void negativePositiveIntegerAttribute() throws Exception {
        element.setAttribute( "positiveIntegerValue","-1" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Ellie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
//                    "Gearlie \\(sally\\) value for 'positiveIntegerValue' attribute should not be negative.")
    public void negativePositiveIntegerAttributeWithID() throws Exception {
        element.setAttribute( "id", "sally" );
        element.setAttribute( "positiveIntegerValue","-1" );
        new Ellie( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Ellie instance value for 'positiveIntegerValue' attribute should not be zero.")
    public void zeroPositiveIntegerAttribute() throws Exception {
        element.setAttribute( "positiveIntegerValue","0" );
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

    @Test
    public void getParentElement() throws Exception {
        elementary.appendChild( element );
        Elemental ellie = new Ellie( element );
        Element parent = ellie.getParentElement( element );
        assertNotNull( parent );
        assertEquals( parent.getTagName(), parentTag );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Ellie \\(sally\\) does not have a parent.")
    public void getParentElementBogus() throws Exception {
        element.setAttribute( "id", "sally" );
        Elemental ellie = new Ellie( element );
        ellie.getParentElement( element );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        elementary = new IIOMetadataNode( parentTag );

        element = new IIOMetadataNode( "elemental" );
        element.setAttribute( "stringValue", "6x9" );
        element.setAttribute( "integerValue", "609" );
        element.setAttribute( "positiveIntegerValue", "1");
        element.setAttribute( "positiveDoubleValue", "17.3");
        element.setAttribute( "used", "17" );
        element.setAttribute( "empty", "" );
        element.setAttribute( "twiceUsed", "4.32" );
        element.setAttribute( "twiceEmpty", "" );
        element.setAttribute( "twiceZero", "0.0" );
        element.setAttribute( "stringNotEmpty", "Not Empty" );
        element.setAttribute( "stringNotNull", "Not Null" );
        element.setAttribute( "doubleValue", "12.3" );
        element.setAttribute( "positiveDoubleValue", "45.6" );

        elementPositiveDoubleNegative = new IIOMetadataNode( "elemental" );
        elementPositiveDoubleNegative.setAttribute( "negativeDoubleValue", "-78.9" );

        elementPositiveDoubleZero = new IIOMetadataNode( "elemental" );
        elementPositiveDoubleZero.setAttribute( "zeroDoubleValue", "0.0" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}