package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Test {@code Read}.
 *
 * @author dhs
 * @since 0.0.1
 */
public class ReadTest {

    public ReadTest() {
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void noArgument() throws Exception {
        new Read( "bogus file name" );
    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test
//    public void reads() throws Exception {
//        TestResets.MinderDomReset();
//
//        new Read( "tutorial" );
//        assertEquals( Drawable.List().size(), 18 );
//    }

    @Test
    public void test() {
        fail( "Test that read opens, parses, and closes specified file." );
    }

//        @Mocked System stringValue;
//        @Mocked FileInputStream bar;
//        @Mocked Parse baz;
//    @Test
////    public void opensDirectory( @Mocked System stringValue ) {
//    public void opensDirectory() throws FileNotFoundException {
//        
////        @Mocked(stubOutClassInitialization = true) final System; unused = null);
//        /*
//         * 
//         */
////        new Expectations() {
//        {
//            stringValue.getProperty( "user.home");
//            InputStream stream = new FileInputStream( "fajdflkj");
//            new Parse(stream);
//        }
////        fail("Test that Read gets the user's home directory from System.getProperty(\"user.home\"), then attempts to open the named file from <user.home>/Plot/plotfiles/");
//    }

    @Test
    public void opensDirectory() {
        /*
         * 
         */
//        new Expectations() {
//            System.getProperty( "stringValue");
//        }
        fail( "Test that Read gets the user's home directory from System.getProperty(\"user.home\"), then attempts to open the named file from <user.home>/Plot/plotfiles/" );
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