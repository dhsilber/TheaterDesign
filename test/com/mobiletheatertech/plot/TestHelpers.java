package com.mobiletheatertech.plot;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Encapsulations of various useful test code.
 *
 * @author dhs
 * @since 0.0.2
 */
public class TestHelpers {

    public TestHelpers() {
    }

    /**
     * Reset the LEGENDLIST maintained by {@link Legend} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code LEGENDLIST} field isn't there.
     * @throws IllegalAccessException if the {@code LEGENDLIST} field cannot be accessed.
     */
    public static void LegendReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = Legend.class.getDeclaredField( "LEGENDLIST" );
        field.setAccessible( true );
        ArrayList<Legendable> LegendList =
                (ArrayList<Legendable>) field.get( Legend.class );
        LegendList.clear();
    }

    /**
     * Reset the LUMINAIRELIST maintained by {@link LuminaireDefinition} to its initial empty
     * state.
     *
     * @throws NoSuchFieldException   if the {@code LUMINAIRELIST} field isn't there.
     * @throws IllegalAccessException if the {@code LUMINAIRELIST} field cannot be accessed.
     */
    public static void LuminaireDefinitionReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = LuminaireDefinition.class.getDeclaredField( "LUMINAIRELIST" );
        field.setAccessible( true );
        ArrayList<LuminaireDefinition> LuminaireDefinitionlist =
                (ArrayList<LuminaireDefinition>) field.get( LuminaireDefinition.class );
        LuminaireDefinitionlist.clear();
    }

    /**
     * Reset the LIST maintained by {@link Minder} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code LIST} field isn't there.
     * @throws IllegalAccessException if the {@code LIST} field cannot be accessed.
     */
    public static void MinderReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Minder.class.getDeclaredField( "LIST" );
        field.setAccessible( true );
        ArrayList<Minder> list = (ArrayList<Minder>) field.get( Minder.class );
        list.clear();
    }

    /**
     * Reset the PIPELIST maintained by {@link Pipe} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code PIPELIST} field isn't there.
     * @throws IllegalAccessException if the {@code PIPELIST} field cannot be accessed.
     */
    public static void PipeReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Pipe.class.getDeclaredField( "PIPELIST" );
        field.setAccessible( true );
        ArrayList<Pipe> pipelist = (ArrayList<Pipe>) field.get( Pipe.class );
        pipelist.clear();
    }

    /**
     * Reset {@link Point} to its initial state.
     * <p/>
     * Zeros out each of the extreme values maintained by {@link Point}.
     *
     * @throws NoSuchFieldException   if one of the fields isn't there.
     * @throws IllegalAccessException if one of the fields cannot be accessed.
     */
    public static void PointReset() throws NoSuchFieldException, IllegalAccessException {
        TestHelpers.ZeroInt( Point.class, "SmallX" );
        TestHelpers.ZeroInt( Point.class, "SmallY" );
        TestHelpers.ZeroInt( Point.class, "SmallZ" );
        TestHelpers.ZeroInt( Point.class, "LargeX" );
        TestHelpers.ZeroInt( Point.class, "LargeY" );
        TestHelpers.ZeroInt( Point.class, "LargeZ" );
    }

    public static void ProsceniumReset() throws NoSuchFieldException, IllegalAccessException {
        Field activeField = Proscenium.class.getDeclaredField( "ACTIVE" );
        activeField.setAccessible( true );
        boolean active = activeField.getBoolean( Proscenium.class );
        activeField.set( active, false );

        Field originField = Proscenium.class.getDeclaredField( "ORIGIN" );
        originField.setAccessible( true );
        Point origin = (Point) originField.get( Proscenium.class );
        originField.set( origin, null );
    }

    public static void ProsceniumActivate() throws NoSuchFieldException, IllegalAccessException {
        Field field = Proscenium.class.getDeclaredField( "ACTIVE" );
        field.setAccessible( true );
        boolean active = field.getBoolean( Proscenium.class );
        field.set( active, true );
    }

    /**
     * Reach into the specified class and zero out the specified static int.
     *
     * @param clazz     {@link Class} whose static field should be cleared.
     * @param fieldName name of static field to clear.
     * @throws NoSuchFieldException   if the specified field isn't there.
     * @throws IllegalAccessException if the specified field cannot be accessed.
     */
    private static void ZeroInt( Class clazz, String fieldName )
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = clazz.getDeclaredField( fieldName );
        field.setAccessible( true );
        field.setInt( clazz, 0 );
    }

    protected static Field accessField( Object thingy, String name ) throws Exception {
        Field field;
        try {
            field = thingy.getClass().getDeclaredField( name );
        }
        catch ( NoSuchFieldException a ) {
            field = thingy.getClass().getField( name );
        }

        field.setAccessible( true );

        return field;
    }

    public static Object accessStaticObject( String classname, String name ) throws Exception {
        Field field = Class.forName( classname ).getDeclaredField( name );
        field.setAccessible( true );
        return field.get( Class.forName( classname ) );
    }

    public static void setStaticObject( String classname, String name, Object value )
            throws Exception
    {
        Field field = Class.forName( classname ).getDeclaredField( name );
        field.setAccessible( true );
//        Object thing = field.get( Class.forName( classname ) );
        field.set( null, value );
    }

    public static Object accessObject( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return field.get( thingy );
    }

    public static int accessInteger( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return (Integer) field.get( thingy );
    }

    public static String accessString( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return (String) field.get( thingy );
    }

    public static Point accessPoint( Object thingy, String name ) throws Exception {
        Field field = accessField( thingy, name );
        return (Point) field.get( thingy );
    }
}
