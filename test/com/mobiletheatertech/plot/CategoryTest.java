package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;


/**
 * Created by dhs on 9/9/14.
 */
public class CategoryTest {

    String categoryName = "Name of category";

    @Test
    public void name() {

        String name = "Name of category";
        Category cat = new Category( name, CategoryTest.class );
        assertEquals( cat.name(), name );

    }

    @Test
    public void recallsNull() {
        assertNull(Category.Select("bogus"));
    }

    @Test
    public void recalls() throws Exception {
        Category category = new Category( categoryName, CategoryTest.class );
        assertSame(Category.Select(categoryName), CategoryTest.class  );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.CategoryReset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
