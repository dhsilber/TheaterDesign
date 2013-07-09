package com.mobiletheatertech.plot;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author dhs
 * @since 0.0.2
 */
public class ExceptionTest {
    
    public ExceptionTest() {
    }

    /*
    For problems in command-line arguments.
     */
    @Test( expectedExceptions = ArgumentException.class,
        expectedExceptionsMessageRegExp = "Message here" )
    public void argumentException() throws Exception {
        throw new ArgumentException( "Message here" );
    }

    /*
    For when a required attribute is missing.
     */
    @Test( expectedExceptions = AttributeMissingException.class,
        expectedExceptionsMessageRegExp =
        "Foo is missing required 'thingy' attribute" )
    public void attributeMissingException() throws Exception {
        throw new AttributeMissingException( "Foo", "thingy" );
    }

    /*
    For badly formed XML.
     */
    @Test( expectedExceptions = InvalidXMLException.class,
        expectedExceptionsMessageRegExp = "grubby" )
    public void invalidXMLException() throws Exception {
        throw new InvalidXMLException( "grubby" );
    }

    /*
    For locations that cannot be.
     */
    @Test( expectedExceptions = LocationException.class,
        expectedExceptionsMessageRegExp = "message" )
    public void LocationException() throws Exception {
        throw new LocationException( "message" );
    }

    /*
    For wrong types of items
     */
    @Test( expectedExceptions = KindException.class,
            expectedExceptionsMessageRegExp = "Foo of size 42 not supported. Try 12 or 20." )
    public void kindException() throws Exception {
        throw new KindException( "Foo", 42 );
    }

    /*
    For bad references to plot items
     */
    @Test( expectedExceptions = ReferenceException.class,
            expectedExceptionsMessageRegExp = "Foo message." )
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