package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static java.lang.Double.valueOf;

/*

TODO: Upgrade notes...

Look in CableRun for the latest & greatest in error reporting. If anything
else uses something similar, that code should move to here.

 */

/**
 * Provides methods for parsing information from DOM elements.
 * <p/>
 * Maintains an <code>id</code> field for those subclasses as which can make use of it so that
 * exception messages can provide more specific information to the user.
 *
 * @author dhs
 * @since 0.0.7 ({@code getIntegerAttribute} and {@code getStringAttribute} refactored out of {@code
 *        Minder} when {@code Elemental} was created.)
 */
public class Elemental extends HasId {
    /**
     * If this is set, Exceptions thrown from the various {@code Elemental} methods can name the
     * specific XML element which has a problem, so child classes are encouraged to both use this in
     * preference to a local variable and to parse the appropriate attribute so as to set this at
     * the earliest opportunity.
     */
    Element internalElement = null;

    public Elemental( Element element ) throws InvalidXMLException {
//        System.err.println( "H" );

        if (null == element) {
            throw new InvalidXMLException( this.getClass().getSimpleName() + " element unexpectedly null!" );
        }
//        System.err.println( "I" );

        internalElement = element;
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} and convert it to an
     * {@code Integer}.
     *
     * @param name    name of attribute.
     * @return Integer value of attribute
     * @throws AttributeMissingException
     */
    protected Integer getIntegerAttribute( String name )
            throws AttributeMissingException
    {
        String value = internalElement.getAttribute( name );
        checkAttribute(name, value);

        return new Integer( value );
    }

    /**
     *
     * @param name
     * @return
     * @throws AttributeMissingException
     * @throws InvalidXMLException
     * @since 0.0.24
     */
    protected Integer getPositiveIntegerAttribute( String name)
            throws AttributeMissingException, InvalidXMLException {
        Integer value = getIntegerAttribute( name);
        if (0 > value) {
            throw new InvalidXMLException(this.getClass().getSimpleName(), id,
                    "value for '" + name + "' attribute should not be negative");
//            this.getClass().getSimpleName() + " \\(" + id + "\\) value for '" + name +
//                    "' attribute should not be negative.");
        }
        if (0 == value) {
            throw new InvalidXMLException(this.getClass().getSimpleName(), id,
                    "value for '" + name + "' attribute should not be zero");
        }
        return value;
    }

    protected Double getPositiveDoubleAttribute( String name)
            throws AttributeMissingException, InvalidXMLException {
        Double value = getDoubleAttribute( name );
        if (0 > value) {
            throw new InvalidXMLException( this.getClass().getSimpleName(), id,
                    "value for '" + name + "' attribute should not be negative");
//            this.getClass().getSimpleName() + " \\(" + id + "\\) value for '" + name +
//                    "' attribute should not be negative.");
        }
        if (0 == value) {
            throw new InvalidXMLException(this.getClass().getSimpleName(), id,
                    "value for '" + name + "' attribute should not be zero");
        }
        return value;
//        return null;
    }

    protected Integer getOptionalIntegerAttributeOrZero( String name ) {
        String value = internalElement.getAttribute( name );
        if (value.isEmpty()) {
            value = "0";
        }

        return new Integer( value );
    }

    @Deprecated
    protected Integer getOptionalIntegerAttributeOrNull( String name ) {
        String value = internalElement.getAttribute( name );
        if (value.isEmpty()) {
            return null;
        }

        return new Integer( value );
    }

//    protected Double getDoubleAttribute( String name )
//            throws AttributeMissingException
//    {
//        return getDoubleAttribute( internalElement, name );
//    }

    protected Double getDoubleAttribute( String name )
            throws AttributeMissingException
    {
        String value = internalElement.getAttribute( name );
        checkAttribute(name, value);

        return valueOf( value );
    }

    @Deprecated
    protected Double getOptionalDoubleAttributeOrNull( String name ) {
        String value = internalElement.getAttribute( name );
        if (value.isEmpty()) {
            return null;
        }

        return valueOf( value );
    }


    protected Double getOptionalDoubleAttributeOrZero( String name ) {
        String value = internalElement.getAttribute( name );
        if (value.isEmpty()) {
            return 0.0;
        }

        return valueOf( value );
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} and return that
     * {@code String}.
     *
     * @param name    name of attribute.
     * @return String value of attribute
     * @throws AttributeMissingException
     */
    protected String getStringAttribute( String name )
            throws AttributeMissingException
    {
        String value = internalElement.getAttribute( name );
        checkAttribute(name, value);

        return value;
    }

    protected void checkAttribute(String name, String value)
            throws AttributeMissingException {
        if (value.isEmpty()) {
            throw new AttributeMissingException(
                    this.getClass().getSimpleName(), id, name );
        }
    }

    protected void checkNotAttribute(String name, String value, String message ) throws InvalidXMLException {
        if ( ! value.isEmpty()) {
            throw new InvalidXMLException(
                    this.getClass().getSimpleName() + " " + message );
        }
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} if it is present.
     *
     * @param name    name of attribute.
     * @return String value of attribute - empty {@code String} if attribute not set
     */
    protected String getOptionalStringAttribute( String name ) {
        String value = internalElement.getAttribute( name );

        return value;
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} if it is present.
     * If it is not present, return a null.
     *
     * @param name    name of attribute.
     * @return String value of attribute - null {@code String} if attribute not set
     */
    @Deprecated
    protected String getOptionalStringAttributeOrNull( String name ) {
        String value = internalElement.getAttribute( name );

        if ( "".equals( value ) ) { value = null; }

        return value;
    }

    Element getParentElement( Element element ) throws InvalidXMLException {
        Node node = element.getParentNode();
        if ( (null == node) || ( Node.ELEMENT_NODE != node.getNodeType() ) ) {
            throw new InvalidXMLException(
                    this.getClass().getSimpleName(), id, "does not have a parent" );
        }

        Element result = (Element) node;
        return result;
    }

}
