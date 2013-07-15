package com.mobiletheatertech.plot;

import mockit.Expectations;
import org.testng.annotations.Test;

/**
 * @author dhs
 * @since 0.0.1
 */
public class MainTest {

    public MainTest() {
    }

    @Test(expectedExceptions = ArgumentException.class,
          expectedExceptionsMessageRegExp = "Not enough arguments")
    public void noArgument() throws Exception {
        String[] args = new String[0];
        Main.main( args );
    }

    @Test(expectedExceptions = ArgumentException.class,
          expectedExceptionsMessageRegExp = "Too many arguments")
    public void tooManyArguments() throws Exception {
        String[] args = new String[]{ "Fiddle", "Faddle" };
        Main.main( args );
    }

    @Test
    public void invokesReadAndWrite() throws Exception {
        new Expectations() {
            {
                new Read( "Fiddle-Faddle" );
                new Write( "Fiddle-Faddle" );
            }
        };
        String[] args = new String[]{ "Fiddle-Faddle" };
        Main.main( args );
    }

}