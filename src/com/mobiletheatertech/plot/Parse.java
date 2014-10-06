package com.mobiletheatertech.plot;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    Element root = null;

    /**
     * Parse the provided XML stream, expecting a description of a lighting plot.
     *
     * @param stream lighting plot XML stream
     * @throws AttributeMissingException if a required attribute is missing
     * @throws FeatureException          if some unimplemented feature is invoked
     * @throws InvalidXMLException       if the XML plot description is not valid
     * @throws KindException             if an attribute specifies a flavor of {@literal Plot} item
     *                                   that is not supported (e.g. a {@code Truss} with a 'size'
     *                                   of '1')
     * @throws LocationException         if any {@literal Plot} item is outside the {@code Venue}
     * @throws ReferenceException        if a reference is made to a feature that does not exist
     * @throws SizeException             if an attribute specifies a dimension that doesn't make
     *                                   sense (e.g. a {@code Pipe} with a length of zero)
     */
    public Parse( InputStream stream )
            throws AttributeMissingException, DataException, FeatureException, InvalidXMLException,
            KindException, LocationException, MountingException,
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

        root = xml.getDocumentElement();
        if (null == root || !"plot".equals( root.getTagName() )) {
            throw new InvalidXMLException( "Top level element must be 'plot'." );
        }

        parseXML( Drawing.class, "drawing" );

//        NodeList layer = root.getElementsByTagName( "layer" );
        parseXML( UserLayer.class, "layer" );
//        UserLayer.ParseXML( layer );

        parseXML( Grid.class, "grid" );

        parseXML( CableDiversion.class, "cable-diversion" );

        parseXML( LuminaireDefinition.class, "luminaire-definition");
//        NodeList luminaireDefinition = root.getElementsByTagName( "luminaire-definition" );
//        LuminaireDefinition.ParseXML( luminaireDefinition );

        parseXML( DeviceTemplate.class, "device-template");
//        NodeList deviceTemplate = root.getElementsByTagName( "device-template" );
//        DeviceTemplate.ParseXML( deviceTemplate );

        parseXML( Venue.class, "venue");
//        NodeList venue = root.getElementsByTagName( "venue" );
//        Venue.ParseXML( venue );

        parseXML( Wall.class, "wall");
//        NodeList wall = root.getElementsByTagName( "wall" );
//        Wall.ParseXML( wall );

        parseXML( Balcony.class, "balcony");
//        NodeList balcony = root.getElementsByTagName( "balcony" );
//        Balcony.ParseXML( balcony );

        parseXML( Proscenium.class, "proscenium");
//        NodeList proscenium = root.getElementsByTagName( "proscenium" );
//        Proscenium.ParseXML( proscenium );

        parseXML(Airwall.class, "airwall");
//        NodeList airwall = root.getElementsByTagName( "airwall" );
//        Airwall.ParseXML( airwall );

        parseXML( Stage.class, "stage" );
//        NodeList stage = root.getElementsByTagName( "stage" );
//        Stage.ParseXML( stage );

        parseXML( Table.class, "table" );
//        NodeList tables = root.getElementsByTagName("table");
//        Table.ParseXML(tables);

        parseXML( ChairBlock.class, "chairblock" );
//        NodeList chairs = root.getElementsByTagNamJe( "chairblock" );
//        ChairBlock.ParseXML( chairs );

        parseXML( HangPoint.class, "hangpoint" );
//        NodeList hangpoints = root.getElementsByTagName( "hangpoint" );
//        HangPoint.ParseXML( hangpoints );

        parseXML( Event.class, "event" );
//        NodeList events = root.getElementsByTagName( "event" );
//        Event.ParseXML( events );

        parseXML( Truss.class, "truss" );
//        NodeList truss = root.getElementsByTagName( "truss" );
//        Truss.ParseXML( truss );

        parseXML( Suspend.class, "suspend" );
//        NodeList suspend = root.getElementsByTagName( "suspend" );
//        Suspend.ParseXML( suspend );

//        parseXML( Base.class, "base" );
////        NodeList base = root.getElementsByTagName( "base" );
////        Base.ParseXML( base );

        parseXML( Pipe.class, "pipe" );
//        NodeList pipe = root.getElementsByTagName( "pipe" );
//        Pipe.ParseXML( pipe );

        parseXML( Zone.class, "zone" );
//        NodeList zone = root.getElementsByTagName( "zone" );
//        Zone.ParseXML( zone );

        parseXML( Luminaire.class, "luminaire" );
//        NodeList luminaire = root.getElementsByTagName( "luminaire" );
//        if (luminaire.getLength() > 0) {
//            Luminaire.ParseXML( luminaire );
//        }

        parseXML( Device.class, "device" );
//        NodeList devices = root.getElementsByTagName( "device" );
//        Device.ParseXML( devices );

        parseXML( CableRun.class, "cable-run" );
//        NodeList cableRuns = root.getElementsByTagName( "cable-run" );
//        CableRun.ParseXML( cableRuns );
//
        MinderDom.VerifyAll();

        NodeList setup = root.getElementsByTagName( "setup" );
        Setup.ParseXML( setup );
    }

    /**
     * Get the elements with a particular tag from the document and
     * create the specified type of objects from them.
     *
     * @param clazz Class of object to create.
     * @param tag XML tag to process
     */
    void parseXML( Class clazz, String tag ) {
        NodeList list = root.getElementsByTagName( tag );

        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                try {
                    Constructor ctor = clazz.getDeclaredConstructor( Element.class );
                    ctor.newInstance( element );
                }
                catch ( NoSuchMethodException |
                        InstantiationException |
                        IllegalAccessException |
                        InvocationTargetException e ) {
                    e.printStackTrace();
                }
            }
        }

    }
}
