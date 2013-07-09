package com.mobiletheatertech.plot;

import java.awt.Graphics2D;
import mockit.Expectations;
import mockit.Mocked;
import static org.testng.Assert.fail;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
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
    public void noHome() throws Exception {
        fail("Must throw exception if user's home is not available.");
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