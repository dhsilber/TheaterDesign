package com.mobiletheatertech.plot;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parse an XML stream, which should contain a Plot specification.
 * <p/>
 * XML tag of the root node must be 'plot'. Children are 'venue' (exactly one required), 'stage',
 * and 'truss'. There are no attributes.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Parse {

    /**
     * Parse the provided XML stream, expecting a description of a lighting plot.
     *
     * @param stream lighting plot XML stream
     * @throws AttributeMissingException If a required attribute is missing.
     * @throws InvalidXMLException       If the XML plot description is not valid.
     * @throws KindException             If an attribute specifies a flavor of {@literal Plot} item
     *                                   that is not supported (e.g. a {@code Truss} with a 'size'
     *                                   of '1').
     * @throws LocationException         If any {@literal Plot} item is outside the {@code Venue}.
     * @throws ReferenceException        If a reference is made to a feature that does not exist.
     * @throws SizeException             If an attribute specifies a dimension that doesn't make
     *                                   sense (e.g. a {@code Pipe} with a length of zero).
     */
    public Parse( InputStream stream )
            throws AttributeMissingException, InvalidXMLException,
            KindException, LocationException,
            ReferenceException, SizeException
    {
        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        }
        catch ( ParserConfigurationException e ) {
            e.printStackTrace();
            System.exit( 1 );
        }

        Document xml;
        try {
            xml = builder.parse( stream );
        }
        catch ( IOException | SAXException ex ) {
            Logger.getLogger( Parse.class.getName() ).log( Level.SEVERE, null, ex );
            throw new InvalidXMLException( "Error in parsing Plot XML description." );
        }

        Element root = xml.getDocumentElement();
        if (null == root || !"plot".equals( root.getTagName() )) {
            throw new InvalidXMLException( "Top level element must be 'plot'." );
        }

        NodeList luminaireDefinition = root.getElementsByTagName( "luminaire-definition" );
        LuminaireDefinition.ParseXML( luminaireDefinition );

        NodeList venue = root.getElementsByTagName( "venue" );
        Venue.ParseXML( venue );

        NodeList proscenium = root.getElementsByTagName( "proscenium" );
        Proscenium.ParseXML( proscenium );

        NodeList airwall = root.getElementsByTagName( "airwall" );
        Airwall.ParseXML( airwall );

        NodeList stage = root.getElementsByTagName( "stage" );
        Stage.ParseXML( stage );

        NodeList hangpoints = root.getElementsByTagName( "hangpoint" );
        HangPoint.ParseXML( hangpoints );

        NodeList truss = root.getElementsByTagName( "truss" );
        Truss.ParseXML( truss );

        NodeList suspend = root.getElementsByTagName( "suspend" );
        Suspend.ParseXML( suspend );

//        NodeList base = root.getElementsByTagName( "base" );
//        Base.ParseXML( base );

        NodeList pipe = root.getElementsByTagName( "pipe" );
        Pipe.ParseXML( pipe );

        NodeList luminaire = root.getElementsByTagName( "luminaire" );
        Luminaire.ParseXML( luminaire );

        Truss.VerifyAll();

    }
}
