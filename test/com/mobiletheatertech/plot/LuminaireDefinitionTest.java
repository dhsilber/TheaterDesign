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
    Element svgElement = null;
    final String id = "6x9";
    Double width = 13.8;
    Double length = 27.2;
    Double weight = 19.4;

    @Test
    public void isA() throws Exception {
        LuminaireDefinition instance = new LuminaireDefinition( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );

        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        element.removeAttribute( "width" );
        element.removeAttribute( "length" );

        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        assertEquals( TestHelpers.accessString( luminaireDefinition, "id" ), id );
        assertEquals( TestHelpers.accessDouble(luminaireDefinition, "width"), 0.0 );
        assertEquals( TestHelpers.accessDouble( luminaireDefinition, "length"), 0.0 );
        assertEquals( TestHelpers.accessDouble( luminaireDefinition, "weight"), weight );
        assertEquals( TestHelpers.accessBoolean(luminaireDefinition, "complete"), Boolean.FALSE );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute("complete", "1");
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        assertEquals( TestHelpers.accessString( luminaireDefinition, "id" ), id );
        assertEquals( TestHelpers.accessDouble(luminaireDefinition, "width"), width );
        assertEquals( TestHelpers.accessDouble(luminaireDefinition, "length"), length );
        assertEquals( TestHelpers.accessDouble( luminaireDefinition, "weight"), weight );
        assertEquals( TestHelpers.accessBoolean(luminaireDefinition, "complete"), Boolean.TRUE );
    }

    @Test
    public void storesSelf() throws Exception {
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( luminaireDefinition );
    }

    @Test
    public void storesSVG() throws Exception {
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        Object svg = TestHelpers.accessObject(luminaireDefinition, "svg");
        assertNotNull(svg);
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "LuminaireDefinition \\("+id+"\\) svg element is required.")
    public void missingSVG() throws Exception {
        element.removeChild(svgElement);
        LuminaireDefinition luminaireDefinition = new LuminaireDefinition( element );

        Object svg = TestHelpers.accessObject( luminaireDefinition, "svg" );
        assertNotNull( svg );
    }

    // TODO Keep this until I resolve how Luminaire knows what type it is.
    @Test
    public void recallsNull() {
        assertNull(LuminaireDefinition.Select("bogus"));
    }

    // TODO Keep this until I resolve how Luminaire knows what type it is.
    @Test
    public void recalls() throws Exception {
        LuminaireDefinition definition = new LuminaireDefinition( element );
        assertSame(LuminaireDefinition.Select(id), definition);
    }

    @Test
    public void length() throws Exception {
        LuminaireDefinition instance = new LuminaireDefinition( element );

        assertEquals(instance.length(), length);
    }

    @Test
    public void noLength() throws Exception {
        element.removeAttribute( "length" );
        LuminaireDefinition instance = new LuminaireDefinition( element );

        assertEquals(instance.length(), 0.0 );
    }

    @Test
    public void width() throws Exception {
        LuminaireDefinition instance = new LuminaireDefinition( element );

        assertEquals( instance.width(), width );
    }

    @Test
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        LuminaireDefinition instance = new LuminaireDefinition( element );

        assertEquals( instance.width(), 0.0 );
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

    @Test
    public void weight() throws Exception {
        LuminaireDefinition instance = new LuminaireDefinition( element );

        assertEquals(instance.weight(), weight);
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "LuminaireDefinition \\("+id+"\\) is missing required 'weight' attribute." )
    public void noWeight() throws Exception {
        element.removeAttribute( "weight" );
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
        // TODO Keep this until I resolve how Luminaire knows what type it is.
        TestResets.LuminaireDefinitionReset();

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "luminaire-definition" );
        element.setAttribute( "name", id );
        element.setAttribute( "width", width.toString() );
        element.setAttribute( "length", length.toString() );
        element.setAttribute( "weight", weight.toString() );

        svgElement = new IIOMetadataNode( "svg" );
        element.appendChild( svgElement );

        Element defs = new IIOMetadataNode( "defs" );
        svgElement.appendChild( defs );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
