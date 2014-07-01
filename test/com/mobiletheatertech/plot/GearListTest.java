package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 4/12/14.
 *
 * @author dhs
 * @since 0.0.24
 */
public class GearListTest {

    String item = "Test string";

    Element element = null;

    public GearListTest() {
    }

    @Test
    public void storesItem() throws Exception {

        assertEquals( GearList.Check( item ), (Integer) 0 );

//        new Gearlie( item );
        GearList.Add( item );

        assertEquals(GearList.Check(item), (Integer) 1);
    }

    @Test
    public void storesItemTwice() throws Exception {

        assertEquals( GearList.Check( item ), (Integer) 0 );

        GearList.Add( item );
        GearList.Add( item );

        assertEquals(GearList.Check(item), (Integer) 2);
    }

    @Test
    public void getList() throws Exception {
        GearList.Add("Jim");
        GearList.Add("Jim");
        GearList.Add("Jim");
        GearList.Add("Jane");

        Object[][] data = GearList.Report();

        assertEquals( data[0][0], "Jim");
        assertEquals(data[0][1], (Integer) 3);
        assertEquals(data[1][0], "Jane");
        assertEquals(data[1][1], (Integer) 1);
    }

    @Test
    public void getListMore() throws Exception {
        GearList.Add("Jim");
        GearList.Add("Jim");
        GearList.Add("Jim");
        GearList.Add("Jane");
        GearList.Add("Jo");
        GearList.Add("Joe");

        Object[][] data = GearList.Report();

        assertEquals( GearList.Size(), 4 );

        assertEquals( data[0][0], "Jim");
        assertEquals(data[0][1], (Integer) 3);
        assertEquals(data[1][0], "Jane");
        assertEquals(data[1][1], (Integer) 1);
        assertEquals(data[2][0], "Jo");
        assertEquals(data[2][1], (Integer) 1);
        assertEquals(data[3][0], "Joe");
        assertEquals(data[3][1], (Integer) 1);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        GearList.Reset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}