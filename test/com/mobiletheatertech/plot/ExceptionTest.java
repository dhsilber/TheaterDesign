package com.mobiletheatertech.plot;

import org.testng.annotations.*;

/**
 * Test the various exception classes.
 *
 * @author dhs
 * @since 0.0.2
 */
public class ExceptionTest {

    public ExceptionTest() {
    }

    /**
     * For problems in command-line arguments.
     */
    @Test(expectedExceptions = ArgumentException.class,
            expectedExceptionsMessageRegExp = "Message here")
    public void argumentException() throws Exception {
        throw new ArgumentException("Message here");
    }

    /**
     * For when a required attribute is missing and an ID is set.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Foo \\(sam\\) is missing required 'thingy' attribute.")
    public void attributeMissingExceptionWithID() throws Exception {
        throw new AttributeMissingException("Foo", "sam", "thingy");
    }

    /**
     * For when a required attribute is missing and no ID is known.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Foo instance is missing required 'thingy' attribute.")
    public void attributeMissingExceptionWithoutID() throws Exception {
        throw new AttributeMissingException("Foo", "", "thingy");
    }

    /**
     * For when a required attribute is missing and ID is not set.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Foo instance is missing required 'thingy' attribute.")
    public void attributeMissingExceptionNullID() throws Exception {
        throw new AttributeMissingException("Foo", null, "thingy");
    }

    /**
     * For bad data not directly from XML.
     *
     * @since 0.0.21
     */
    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "grubby")
    public void dataException() throws Exception {
        throw new DataException("grubby");
    }

    /**
     * For features not yet implemented in Plot.
     *
     * @since 0.0.12
     */
    @Test(expectedExceptions = FeatureException.class,
            expectedExceptionsMessageRegExp = "grubby")
    public void featureException() throws Exception {
        throw new FeatureException("grubby");
    }

    /**
     * For badly formed XML.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "grubby")
    public void invalidXMLException() throws Exception {
        throw new InvalidXMLException("grubby");
    }

    /**
     * For when a required attribute is missing and an ID is set.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Foo \\(sam\\) is missing required 'thingy' attribute.")
    public void invalidXMLExceptionWithID() throws Exception {
        throw new InvalidXMLException("Foo", "sam", "is missing required 'thingy' attribute");
    }

    /**
     * For when a required attribute is missing and no ID is known.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Foo instance is missing required 'thingy' attribute.")
    public void invalidXMLExceptionWithoutID() throws Exception {
        throw new InvalidXMLException("Foo", "", "is missing required 'thingy' attribute");
    }

    /**
     * For when a required attribute is missing and ID is not set.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Foo instance is missing required 'thingy' attribute.")
    public void invalidXMLExceptionNullID() throws Exception {
        throw new InvalidXMLException("Foo", null, "is missing required 'thingy' attribute");
    }

    /**
     * For locations that cannot be.
     *
     * @since 0.0.3
     */
    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp = "message")
    public void LocationException() throws Exception {
        throw new LocationException("message");
    }

    /**
     * For wrong types of items
     *
     * @since 0.0.5
     */
    @Test(expectedExceptions = KindException.class,
            expectedExceptionsMessageRegExp = "Foo of size 42 not supported. Try 12 or 20.")
    public void kindException() throws Exception {
        throw new KindException("Foo", 42);
    }

    /**
     * For invalid sizes
     *
     * @since 0.0.6
     */
    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp = "Foo should have a positive length.")
    public void sizeException() throws Exception {
        throw new SizeException("Foo", "length");
    }

    /**
     * For bad references to plot items
     *
     * @since 0.0.5
     */
    @Test(expectedExceptions = ReferenceException.class,
            expectedExceptionsMessageRegExp = "Foo message.")
    public void ReferenceException() throws Exception {
        throw new ReferenceException("Foo message.");
    }

    /**
     * For bad references to plot items
     *
     * @since 0.0.7
     */
    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "Foo message.")
    public void MountingException() throws Exception {
        throw new MountingException("Foo message.");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}