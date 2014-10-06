package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 12:30 AM To change this template use
 * File | Settings | File Templates.
 */
public class Session extends Elemental {

    private ArrayList<String> requirements = new ArrayList<>();

    //    private String name;
    private String setup;

    public Session(Element element) throws AttributeMissingException, InvalidXMLException {
        super (element);

        id = getStringAttribute( element, "name" );
        setup = getOptionalStringAttribute( element, "setup" );
    }

    /**
     * Construct a Session with just a name, rather than an XML entity.
     *
     * @param name
     * @throws InvalidXMLException
     */
    public Session( String name  ) throws InvalidXMLException {
        super( new IIOMetadataNode() );

        id = name;
    }

    public void needs( String requirement ) {
        if( !requirement.isEmpty() && !requirements.contains( requirement )) {
            requirements.add( requirement );
        }
    }

    public List needs( ) {
        return requirements;
    }

    public String name() {
        return id;
    }

    public String setup() {
        return setup;
    }
}
