package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code LuminaireDefinition}.
 *
 * @author dhs
 * @since 0.0.7
 */
public class LuminaireDefinitionTest {

    Element element = null;

    public LuminaireDefinitionTest() {

    }

    @Test
    public void isMinder() throws Exception {
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        assert Minder.class.isInstance( luminaireDefinition );
    }

    @Test
    public void storesAttributes() throws Exception {
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        assertEquals( TestHelpers.accessString( luminaireDefinition, "id" ), "6x9" );
    }

    @Test
    public void storesSelf() throws Exception {
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        ArrayList<Minder> thing = Drawable.List();

        assert thing.contains( luminaireDefinition );
    }

    @Test
    public void storesSVG() throws Exception {
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        Object svg = TestHelpers.accessObject( luminaireDefinition, "svg" );
        assertNotNull( svg );
    }

    @Test
    public void recallsNull() {
        assertNull( LuminaireDefinition.Select( "bogus" ) );
    }

    @Test
    public void recalls() throws Exception {
        LuminaireDefinition definition = new LuminaireDefinition( element );
        assertSame( LuminaireDefinition.Select( "6x9" ), definition );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new LuminaireDefinition( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Luminaire definition instance is missing required 'name' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "name" );
        new LuminaireDefinition( element );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.LuminaireDefinitionReset();

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "luminaire-definition" );
        element.setAttribute( "name", "6x9" );

        Element svg = new IIOMetadataNode( "svg" );
        element.appendChild( svg );

        Element defs = new IIOMetadataNode( "defs" );
        svg.appendChild( defs );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
