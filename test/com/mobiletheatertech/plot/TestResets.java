package com.mobiletheatertech.plot;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/18/13 Time: 7:49 AM To change this template use
 * File | Settings | File Templates.
 */
public class TestResets {

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
        TestResets.ZeroInt( Point.class, "SmallX" );
        TestResets.ZeroInt( Point.class, "SmallY" );
        TestResets.ZeroInt( Point.class, "SmallZ" );
        TestResets.ZeroInt( Point.class, "LargeX" );
        TestResets.ZeroInt( Point.class, "LargeY" );
        TestResets.ZeroInt( Point.class, "LargeZ" );
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

/*
    public static void ProsceniumActivate() throws NoSuchFieldException, IllegalAccessException {
        Field field = Proscenium.class.getDeclaredField( "ACTIVE" );
        field.setAccessible( true );
        boolean active = field.getBoolean( Proscenium.class );
        field.set( active, true );
    }
*/

    public static void ChairBlockReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChairBlock.class.getDeclaredField( "SYMBOLGENERATED" );
        field.setAccessible( true );
        boolean generated = field.getBoolean( ChairBlock.class );
        field.set( generated, false );
    }

/*
    public static void ProsceniumDeactivate() throws NoSuchFieldException, IllegalAccessException {
        Field field = Proscenium.class.getDeclaredField( "ACTIVE" );
        field.setAccessible( true );
        boolean active = field.getBoolean( Proscenium.class );
        field.set( active, false );
    }
*/

    /**
     * Reset the Accumulator maintained by {@link Setup} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code Accumulator} field isn't there.
     * @throws IllegalAccessException if the {@code Accumulator} field cannot be accessed.
     */
    public static void SetupReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Setup.class.getDeclaredField( "Accumulator" );
        field.setAccessible( true );
        StringBuilder accumulator = (StringBuilder) field.get( Setup.class );
        field.set( accumulator, new StringBuilder() );
    }

    /**
     * Reset the StaticVenue maintained by {@link Venue} to its initial null state.
     *
     * @throws NoSuchFieldException   if the {@code StaticVenue} field isn't there.
     * @throws IllegalAccessException if the {@code StaticVenue} field cannot be accessed.
     */
    public static void VenueReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Venue.class.getDeclaredField( "StaticVenue" );
        field.setAccessible( true );
        Venue staticVenue = (Venue) field.get( Venue.class );
        field.set( staticVenue, null );
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


}
