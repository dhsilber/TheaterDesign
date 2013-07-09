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
     * Reset {@link Point} to its initial state.
     *
     * Zeros out each of the extreme values maintained by {@link Point}.
     *
     * @throws NoSuchFieldException if one of the fields isn't there.
     * @throws IllegalAccessException if one of the fields cannot be accessed.
     */
    public static void PointReset() throws NoSuchFieldException, IllegalAccessException
    {
        TestHelpers.ZeroInt( Point.class, "SmallX" );
        TestHelpers.ZeroInt( Point.class, "SmallY" );
        TestHelpers.ZeroInt( Point.class, "SmallZ" );
        TestHelpers.ZeroInt( Point.class, "LargeX" );
        TestHelpers.ZeroInt( Point.class, "LargeY" );
        TestHelpers.ZeroInt( Point.class, "LargeZ" );
    }

    /**
     * Reach into the specified class and zero out the specified static int.
     *
     * @param clazz {@link Class} whose static field should be cleared.
     * @param fieldName name of static field to clear.
     * @throws NoSuchFieldException if the specified field isn't there.
     * @throws IllegalAccessException if the specified field cannot be accessed.
     */
    private static void ZeroInt( Class clazz, String fieldName )
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = clazz.getDeclaredField( fieldName );
        field.setAccessible( true );
        field.setInt( clazz, 0 );
    }

    /**
     * Reset the LIST maintained by {@link Minder} to its initial empty state.
     *
     * @throws NoSuchFieldException if the {@code LIST} field isn't there.
     * @throws IllegalAccessException if the {@code LIST} field cannot be accessed.
     */
    public static void MinderReset() throws NoSuchFieldException, IllegalAccessException
    {
        Field field = Minder.class.getDeclaredField( "LIST" );
        field.setAccessible( true );
        ArrayList<Minder> list = (ArrayList<Minder>) field.get( Minder.class );
        list.clear();
    }

    protected static Field accessField( Object thingy, String name ) throws Exception
    {
        Field field = thingy.getClass().getDeclaredField( name );
        field.setAccessible( true );

        return field;
    }

    public static int accessInteger( Object thingy, String name ) throws Exception
    {
        Field field = accessField( thingy, name );
        return (Integer) field.get( thingy );
    }

    public static String accessString( Object thingy, String name ) throws Exception
    {
        Field field = accessField( thingy, name );
        return (String) field.get( thingy );
    }
}
