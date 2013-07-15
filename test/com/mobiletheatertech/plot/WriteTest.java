package com.mobiletheatertech.plot;

import mockit.Expectations;
import org.testng.annotations.*;

import java.awt.*;

import static org.testng.Assert.fail;

/**
 * Test {@code Write}.
 *
 * @author dhs
 * @since 0.0.1
 */
public class WriteTest {

    public WriteTest() {
    }

    @Test
    public void drawAndCreate() throws Exception {

        new Expectations() {
            Draw draw;

            {
                draw = new Draw();
                Graphics2D canvas = draw.canvas();
                Minder.DrawAllPlan( canvas );
                draw.getRoot();
                Minder.DomAllPlan( draw );
                draw.create( "/Users/dhs/Plot/out/Fiddle-Faddle.svg" );
            }
        };
        String filename = "Fiddle-Faddle";
        new Write( filename );
    }

    @Test
//        ( expectedExceptions=SystemDataMissingException.class,
//        expectedExceptionsMessageRegExp = "User has no home directory")
    public void noHome() throws Exception
    {
        fail( "Must throw exception if user's home is not available." );
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