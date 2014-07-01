package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

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
public class Elemental {
    /**
     * If this is set, Exceptions thrown from the various {@code Elemental} methods can name the
     * specific XML element which has a problem, so child classes are encouraged to both use this in
     * preference to a local variable and to parse the appropriate attribute so as to set this at
     * the earliest opportunity.
     */
    public String id = null;

    public Elemental( Element element) throws InvalidXMLException{

        if (null == element) {
            throw new InvalidXMLException( this.getClass().getSimpleName() + " element unexpectedly null!" );
        }

    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} and convert it to an
     * {@code Integer}.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return Integer value of attribute
     * @throws AttributeMissingException
     */
    protected Integer getIntegerAttribute( Element element, String name )
            throws AttributeMissingException
    {
        String value = element.getAttribute( name );
        if (value.isEmpty()) {
            throw new AttributeMissingException(
                    this.getClass().getSimpleName(), id, name );
        }

        return new Integer( value );
    }

    /**
     *
     * @param element
     * @param name
     * @return
     * @throws AttributeMissingException
     * @throws InvalidXMLException
     * @since 0.0.24
     */
    protected Integer getPositiveIntegerAttribute(Element element, String name)
            throws AttributeMissingException, InvalidXMLException {
        Integer value = getIntegerAttribute(element, name);
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

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} if it is present and
     * convert it to an {@code Integer}.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return Integer value of attribute - zero if attribute is not set
     */
    protected Integer getOptionalIntegerAttribute( Element element, String name ) {
        String value = element.getAttribute( name );
        if (value.isEmpty()) {
            value = "0";
        }

        return new Integer( value );
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} if it is present and
     * convert it to an {@code Integer}.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return Integer value of attribute - zero if attribute is not set
     */
    protected Double getOptionalDoubleAttribute( Element element, String name ) {
        String value = element.getAttribute( name );
        if (value.isEmpty()) {
            value = "0";
        }

        return new Double( value );
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} and return that
     * {@code String}.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return String value of attribute
     * @throws AttributeMissingException
     */
    protected String getStringAttribute( Element element, String name )
            throws AttributeMissingException
    {
        String value = element.getAttribute( name );
        if (value.isEmpty()) {
            throw new AttributeMissingException(
                    this.getClass().getSimpleName(), id, name );
        }

        return value;
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} if it is present.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return String value of attribute - empty {@code String} if attribute not set
     */
    protected String getOptionalStringAttribute( Element element, String name ) {
        String value = element.getAttribute( name );

        return value;
    }

}
