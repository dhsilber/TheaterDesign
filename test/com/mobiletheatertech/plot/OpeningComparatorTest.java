package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.Comparator;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/30/13 Time: 9:21 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.12
 */
public class OpeningComparatorTest {
    OpeningComparator openingComparator = null;

    Element element = null;
    Element element2 = null;

    Integer height = 23;
    Integer width = 25;
    Integer start = 2;

    @Test
    public void isComparator() throws Exception {
        assert Comparator.class.isInstance( openingComparator );
    }

    @Test
    public void compareSame() throws Exception {
        Opening opening = new Opening( element );

        assertEquals( openingComparator.compare( opening, opening ), 0 );
    }

    @Test
    public void compareSmallerStart() throws Exception {
        element2.setAttribute( "start", "1" );

        Opening opening = new Opening( element );
        Opening opening2 = new Opening( element2 );

        assertEquals( openingComparator.compare( opening2, opening ), -1 );
    }

    @Test
    public void compareLargerStart() throws Exception {
        element2.setAttribute( "start", "3" );

        Opening opening = new Opening( element );
        Opening opening2 = new Opening( element2 );

        assertEquals( openingComparator.compare( opening2, opening ), 1 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        openingComparator = new OpeningComparator();

        element = new IIOMetadataNode( "opening" );
        element.setAttribute( "height", height.toString() );
        element.setAttribute( "width", width.toString() );
        element.setAttribute( "start", start.toString() );

        element2 = new IIOMetadataNode( "opening" );
        element2.setAttribute( "height", height.toString() );
        element2.setAttribute( "width", width.toString() );
        element2.setAttribute( "start", start.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
