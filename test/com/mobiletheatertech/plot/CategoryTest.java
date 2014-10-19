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
    String layerName = "Name of layer";

    @Test
    public void name() {
        String name = "Name of category";
        Category cat = new Category( name, CategoryTest.class );
        assertEquals( cat.name(), name );
    }

    @Test
    public void layer() {
        String name = "Name of category";
        Category cat = new Category( name, CategoryTest.class, layerName );
        assertEquals( cat.layer(), layerName );
    }

    @Test
    public void recallsNull() {
        assertNull(Category.Select("bogus"));
    }

    @Test
    public void recalls() throws Exception {
        Category category = new Category( categoryName, CategoryTest.class );
        assertSame(Category.Select(categoryName), category );
    }

    @Test
    public void recallsName() throws Exception {
        new Category( categoryName, CategoryTest.class );

        Category category = Category.Select( categoryName );
        assertSame( category.name(), categoryName );
    }

    @Test
    public void recallsClass() throws Exception {
        new Category( categoryName, CategoryTest.class );

        Category category = Category.Select( categoryName );
        assertSame( category.clazz(), CategoryTest.class );
    }

    @Test
    public void recallsLayer() throws Exception {
        new Category( categoryName, CategoryTest.class );

        Category category = Category.Select( categoryName );
        assertNull( category.layer() );
    }

    @Test
    public void recallsNameLayer() throws Exception {
        new Category( categoryName, CategoryTest.class, layerName );

        Category category = Category.Select( categoryName );
        assertSame( category.name(), categoryName );
    }

    @Test
    public void recallsClassLayer() throws Exception {
        new Category( categoryName, CategoryTest.class, layerName );

        Category category = Category.Select( categoryName );
        assertSame( category.clazz(), CategoryTest.class );
    }

    @Test
    public void recallsLayerLayer() throws Exception {
        new Category( categoryName, CategoryTest.class, layerName );

        Category category = Category.Select( categoryName );
        assertSame( category.layer(), layerName );
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
