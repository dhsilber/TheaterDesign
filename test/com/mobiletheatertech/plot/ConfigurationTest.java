package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 7/19/15.
 */
public class ConfigurationTest {

    String home = "";


    @Test(expectedExceptions = ArgumentException.class,
            expectedExceptionsMessageRegExp = "Missing required plotfile name")
    public void emptyArgument() throws Exception {
        String[] args = new String[]{ "" };
        Configuration.Initialize(args);
    }

    @Test
    public void hasDefaultSourceDirectory() throws Exception {
        Configuration.Initialize( new String[]{"foo"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                home + "/Dropbox/Plot/plotfiles/" );
    }

    @Test
    public void hasDefaultSinkDirectory() throws Exception {
        Configuration.Initialize( new String[]{"foo"} );

        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                home + "/Dropbox/Plot/out/foo/" );
    }

    @Test(expectedExceptions = ArgumentException.class,
            expectedExceptionsMessageRegExp = "Not enough arguments")
    public void noArgument() throws Exception {
        String[] args = new String[0];
        Configuration.Initialize(args);
    }

    @Test
    public void simpleArgumentOK() throws Exception {
        Configuration.Initialize( new String[]{"foo"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                home + "/Dropbox/Plot/plotfiles/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                home + "/Dropbox/Plot/out/foo/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "foo" );
    }

    @Test
    public void relativeSourcePathnameArgumentOK() throws Exception {
        Configuration.Initialize( new String[]{"~/foo/bar"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                home + "/foo/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                home + "/Dropbox/Plot/out/bar/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "bar" );
    }

    @Test
    public void fullSourcePathnameArgumentOK() throws Exception {
        Configuration.Initialize( new String[]{"/foo/bar"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                "/foo/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                home + "/Dropbox/Plot/out/bar/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "bar" );
    }

    @Test
    public void relativeSinkPathnameArgumentOK() throws Exception {
        Configuration.Initialize( new String[]{ "/foo/bar", "~/baz/"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                "/foo/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                home + "/baz/bar/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "bar" );
    }

    @Test
    public void relativeSinkPathnameArgumentSloppy() throws Exception {
        Configuration.Initialize( new String[]{ "/foo/bar", "~/baz"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                "/foo/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                home + "/baz/bar/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "bar" );
    }

    @Test
    public void fullSinkPathnameArgumentOK() throws Exception {
        Configuration.Initialize( new String[]{ "/foo/bar", "/baz/"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                "/foo/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                "/baz/bar/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "bar" );
    }

    @Test
    public void fullSinkPathnameArgumentSloppy() throws Exception {
        Configuration.Initialize( new String[]{ "/foo/bar", "/baz"} );

        assertEquals(
                Configuration$.MODULE$.SourceDirectory(),
                "/foo/" );
        assertEquals(
                Configuration$.MODULE$.SinkDirectory(),
                "/baz/bar/" );
        assertEquals(
                Configuration$.MODULE$.BaseName(),
                "bar" );
    }

    @Test(expectedExceptions = ArgumentException.class,
            expectedExceptionsMessageRegExp = "Too many arguments")
    public void tooManyArguments() throws Exception {
        String[] args = new String[]{ "Fiddle", "Faddle", "flail" };
        Configuration.Initialize(args);
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        home = System.getProperty("user.home");
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
