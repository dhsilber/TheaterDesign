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
    @Test( expectedExceptions = ArgumentException.class,
           expectedExceptionsMessageRegExp = "Message here" )
    public void argumentException() throws Exception {
        throw new ArgumentException( "Message here" );
    }

    /**
     * For when a required attribute is missing.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp =
                  "Foo is missing required 'thingy' attribute.")
    public void attributeMissingException() throws Exception {
        throw new AttributeMissingException( "Foo", "thingy" );
    }

    /**
     * For badly formed XML.
     *
     * @since 0.0.2
     */
    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "grubby")
    public void invalidXMLException() throws Exception {
        throw new InvalidXMLException( "grubby" );
    }

    /**
     * For locations that cannot be.
     *
     * @since 0.0.3
     */
    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp = "message")
    public void LocationException() throws Exception {
        throw new LocationException( "message" );
    }

    /**
     * For wrong types of items
     *
     * @since 0.0.5
     */
    @Test(expectedExceptions = KindException.class,
          expectedExceptionsMessageRegExp = "Foo of size 42 not supported. Try 12 or 20.")
    public void kindException() throws Exception {
        throw new KindException( "Foo", 42 );
    }

    /**
     * For invalid sizes
     *
     * @since 0.0.6
     */
    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp = "Foo should have a positive length.")
    public void sizeException() throws Exception {
        throw new SizeException( "Foo", "length" );
    }

    /**
     * For bad references to plot items
     *
     * @since 0.0.5
     */
    @Test(expectedExceptions = ReferenceException.class,
          expectedExceptionsMessageRegExp = "Foo message.")
    public void ReferenceException() throws Exception {
        throw new ReferenceException( "Foo message." );
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