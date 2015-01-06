package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Created by dhs on 1/6/15.
 */
public class CableTypeTest {

    Element element = null;
    String color = "Blurple";
    String id = "type";

    @Test
    public void isA() throws Exception {
        CableType instance = new CableType( element );

        assert Elemental.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        CableType instance = new CableType( element );

        assertEquals(TestHelpers.accessString(instance, "id"), id );
        assertEquals(TestHelpers.accessString(instance, "schematicColor"), color );
    }

    @Test
    public void color() throws Exception {
        CableType instance = new CableType( element );

        assertEquals( instance.color(), color );
    }

    @Test
    public void storesSelf() throws Exception {
        CableType instance = new CableType( element );

        assertSame( CableType.Select( id ), instance );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<cable-type id=\""+id+"\" schematic-color=\""+color+"\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<CableType> list = (ArrayList<CableType>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.CableType", "CABLETYPELIST");
        assertEquals( list.size(), 1 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.CableTypeReset();

        element = new IIOMetadataNode("cable-type");
        element.setAttribute("id", id);
        element.setAttribute("schematic-color", color );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
