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

    Boolean fatalParseError = false;

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

        Plot plot = new Plot( root );

        parseXML( Drawing.class, "drawing" );
        parseXML( UserLayer.class, "layer" );
        parseXML( Grid.class, "grid" );
        parseXML( CableDiversion.class, "cable-diversion" );
//        parseXML( LuminaireDefinition.class, "luminaire-definition");
        parseXML( DeviceTemplate.class, "device-template");
        parseXML( CableType.class, "cable-type" );
        parseXML( Venue.class, "venue");
        parseXML( Wall.class, "wall");
//        parseXML( Balcony.class, "balcony");
//        parseXML( Proscenium.class, "proscenium");
        parseXML( Airwall.class, "airwall");
        parseXML( Stage.class, "stage" );
        parseXML( Table.class, "table" );
        parseXML( Chair.class, "chair" );
//        parseXML( ChairBlock.class, "chairblock" );
        parseXML( HangPoint.class, "hangpoint" );
        parseXML( Event.class, "event" );
        // Since all 'TrussBase' elements are direct children of a truss,
        // Truss will instantiate each TrussBase as it is discovered,
        // rather than make a list of TrussBase objects and then have to match them up with elements later.
//        parseXML( TrussBase.class, "base" );
        parseXML( Cheeseborough.class, "cheeseborough" );
        parseXML( Drape.class, "drape" );

        // Yokeable
//        parseXML( Truss.class, "truss" );
        parseXML( LightingStand.class, LightingStand.TAG );

        parseXML( PipeBase.class, PipeBase$.MODULE$.Tag() );
//        parseXML( Pipe.class, "pipe" );

        // Since all 'suspend' elements are direct children of a truss,
        // Truss will instantiate each Suspend as it is discovered,
        // rather than make a list of Suspend objects and then have to match them up with elements later.
//        parseXML( Suspend.class, "suspend" );
        parseXML( Zone.class, "zone" );
//        parseXML( Luminaire.class, "luminaire" );
        parseXML( Device.class, "device" );
        parseXML( DanceTile.class, "dancetile" );
//        parseXML( SetPlatform.class, "set-platform" );

//        parseXML( UserMulticable.class, "multicable" );
//        parseXML( UserCableRun.class, "cable-run" );

        if ( fatalParseError ) {
            System.err.println( "... unable to continue." );
            System.exit( 42 );
        }


        Verifier.VerifyAll();

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
//            System.out.println( "Node: " + node.getNodeName() + " Id: " + node.getAttributes());

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
                    System.err.println( e.getCause().getMessage() );

                    fatalParseError = true;

//                    e.printStackTrace();
                }
            }
        }

    }
}
