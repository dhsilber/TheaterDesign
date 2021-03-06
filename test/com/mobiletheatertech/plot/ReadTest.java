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

    static String boguspath = "/boguspath/";
    String pathname = "./files/";
    static String basename = "minimal";
    static String emptyFile = "empty";

//    Does not apply, as Configuration cannot be initialized with no arguments.
//    @Test(expectedExceptions = FileNotFoundException.class,
//            expectedExceptionsMessageRegExp = "No input file specified.")
//    public void noArgument() throws Exception {
//        String[] arguments = new String[] {  };
//        Configuration.Initialize( arguments );wri
//        new Read();
//    }

    @Test(expectedExceptions = FileNotFoundException.class,
            expectedExceptionsMessageRegExp = "/boguspath/minimal.xml \\(No such file or directory\\)")
    public void unfoundArgumentArgument() throws Exception {
        String[] arguments = new String[] { boguspath + basename };
        Configuration.Initialize( arguments );
        new Read();
    }

    @Test
    public void readsEmpty() throws Exception {
        String[] arguments = new String[] { pathname + emptyFile };
        Configuration.Initialize( arguments );
        assertEquals( ElementalLister.List().size(), 0 );

        new Read();

        assertEquals( ElementalLister.List().size(), 0 );
    }

    @Test
    public void reads() throws Exception {
        String[] arguments = new String[] { pathname + basename };
        Configuration.Initialize( arguments );
        assertEquals( ElementalLister.List().size(), 0 );

        new Read();

        assertEquals( ElementalLister.List().size(), 2 );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ElementalListerReset();
        Venue.Reset();
        Proscenium.Reset();
        TestResets.MinderDomReset();
        UniqueId.Reset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}