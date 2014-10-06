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
        HashMap<String, Layer> thing = new HashMap<>();
        thing.put( tag, new Layer(tag, name) );

        String result = HTML.Checkboxes( thing );

        assertEquals( result,
                "<input type=\"checkbox\" onclick=\"parent.selectLayer" + tag +
                        "();\" name=\"layer\"" + " id=\"" + tag +
                        "layer\" checked=\"checked\" value=\"" + tag + "\" />" + name +
                        "<br />\n" );
    }

    @Test
    public void twoCheckboxes() throws Exception {
        String name = "name_of_first_thing";
        String tag = "tag_for_first_thing";
        String name2 = "name_of_second_thing";
        String tag2 = "tag_for_second_thing";
        HashMap<String, Layer> thing = new HashMap<>();
        thing.put( tag, new Layer(tag, name) );
        thing.put( tag2, new Layer(tag2, name2) );

        String result = HTML.Checkboxes( thing );

        assertEquals( result,
                "<input type=\"checkbox\" onclick=\"parent.selectLayer" + tag2 +
                        "();\" name=\"layer\"" + " id=\"" + tag2 +
                        "layer\" checked=\"checked\" value=\"" + tag2 + "\" />" + name2 +
                        "<br />\n"
                        +
                        "<input type=\"checkbox\" onclick=\"parent.selectLayer" + tag +
                        "();\" name=\"layer\"" + " id=\"" + tag +
                        "layer\" checked=\"checked\" value=\"" + tag + "\" />" + name +
                        "<br />\n"
        );
    }

    @Test
    public void selectFunction() throws Exception {
        String name = "name_of_thing";
        String tag = "tag_for_thing";
        HashMap<String, Layer> thing = new HashMap<>();
        thing.put( tag, new Layer(tag, name) );

        String result = HTML.SelectFunctions( thing );

        assertEquals( result,
                "function selectLayer"+tag+"()\n" +
                        "{\n" +
                        "  if( document.getElementById('"+tag+"layer').checked)\n" +
                        "    show( \""+tag+"\" );\n" +
                        "  else\n" +
                        "    hide( \""+tag+"\" );\n" +
                        "}\n"
        );
    }

    @Test
    public void twoSelectFunctions() throws Exception {
        String name = "name_of_first_thing";
        String tag = "tag_for_first_thing";
        String name2 = "name_of_second_thing";
        String tag2 = "tag_for_second_thing";
        HashMap<String, Layer> thing = new HashMap<>();
        thing.put( tag, new Layer(tag, name) );
        thing.put( tag2, new Layer(tag2, name2) );

        String result = HTML.SelectFunctions( thing );

        assertEquals( result,
                "function selectLayer"+tag2+"()\n" +
                        "{\n" +
                        "  if( document.getElementById('"+tag2+"layer').checked)\n" +
                        "    show( \""+tag2+"\" );\n" +
                        "  else\n" +
                        "    hide( \""+tag2+"\" );\n" +
                        "}\n"
                        +
                        "function selectLayer"+tag+"()\n" +
                        "{\n" +
                        "  if( document.getElementById('"+tag+"layer').checked)\n" +
                        "    show( \""+tag+"\" );\n" +
                        "  else\n" +
                        "    hide( \""+tag+"\" );\n" +
                        "}\n"
        );
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