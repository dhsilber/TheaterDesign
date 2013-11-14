package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 9:35 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.19
 */
public class HTMLTest {


    @Test
    public void checkbox() throws Exception {
        String name = "name_of_thing";
        String tag = "tag_for_thing";
        HashMap<String, String> thing = new HashMap<>();
        thing.put( name, tag );

        String result = HTML.Checkboxes( thing );

        assertEquals( result,
                      "<input type=\"checkbox\" onclick=\"parent.selectLayer" + tag +
                              "();\" name=\"layer\"" + " id=\"" + tag +
                              "layer\" checked=\"checked\" value=\"" + tag + "\" />" + name +
                              "<br />\n" );
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