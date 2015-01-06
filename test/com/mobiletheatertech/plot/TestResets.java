package com.mobiletheatertech.plot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/18/13 Time: 7:49 AM To change this template use
 * File | Settings | File Templates.
 */
public class TestResets {

    public static void CableDiversionReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = CableDiversion.class.getDeclaredField( "DIVERSIONLIST" );
        field.setAccessible( true );
        ArrayList<CableDiversion> cableDiversionArrayList =
                (ArrayList<CableDiversion>) field.get( CableDiversion.class );
        cableDiversionArrayList.clear();
    }

    public static void CableRunReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = CableRun.class.getDeclaredField( "RunList" );
        field.setAccessible( true );
        ArrayList<CableRun> runList = (ArrayList<CableRun>) field.get( CableRun.class );
        runList.clear();
    }

    public static void CableTypeReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = CableType.class.getDeclaredField( "CABLETYPELIST" );
        field.setAccessible( true );
        ArrayList<CableType> list = (ArrayList<CableType>) field.get( CableType.class );
        list.clear();
    }

//    public static void CategoryReset()
//            throws NoSuchFieldException, IllegalAccessException
//    {
//        Field field = Category.class.getDeclaredField( "CATEGORYLIST" );
//        field.setAccessible( true );
//        HashMap<String, Class> categoryList =
//                (HashMap<String, Class>) field.get( Category.class );
//        categoryList.clear();
//    }

    public static void ChairBlockReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChairBlock.class.getDeclaredField( "SYMBOLGENERATED" );
        field.setAccessible( true );
        boolean generated = field.getBoolean( ChairBlock.class );
        field.set(generated, false);
    }

    public static void ChairReset() throws NoSuchFieldException, IllegalAccessException {
        Field generatedField = Chair.class.getDeclaredField( "SYMBOLGENERATED" );
        generatedField.setAccessible( true );
        boolean generated = generatedField.getBoolean( Chair.class );
        generatedField.set( generated, false );

        Field registeredField = Chair.class.getDeclaredField( "LEGENDREGISTERED" );
        registeredField.setAccessible( true );
        boolean regisitered = registeredField.getBoolean( Chair.class );
        registeredField.set( regisitered, false );

        Field countField = Chair.class.getDeclaredField( "COUNT" );
        countField.setAccessible( true );
        Integer count = (Integer) countField.get( Chair.class );
        countField.set(count, 0);
    }

    public static void DeviceReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = Device.class.getDeclaredField( "DEVICELIST" );
        field.setAccessible( true );
        ArrayList<Device> deviceList = (ArrayList<Device>) field.get( Device.class );
        deviceList.clear();
    }

    public static void DeviceTemplateReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = DeviceTemplate.class.getDeclaredField( "DEVICELIST" );
        field.setAccessible( true );
        ArrayList<DeviceTemplate> deviceTemplateList =
                (ArrayList<DeviceTemplate>) field.get( DeviceTemplate.class );
        deviceTemplateList.clear();
    }

    public static void EventReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Event.class.getDeclaredField( "Only" );
        field.setAccessible( true );
        Event singleton = (Event) field.get( Event.class );
        field.set(singleton, null);
    }

    /**
     * Reset the LEGENDLIST maintained by {@link Legend} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code LEGENDLIST} field isn't there.
     * @throws IllegalAccessException if the {@code LEGENDLIST} field cannot be accessed.
     */
    public static void LayerReset()
            throws NoSuchFieldException, IllegalAccessException {
        Field field = Layer.class.getDeclaredField("LIST");
        field.setAccessible(true);
        HashMap<String, String> list =
                (HashMap<String, String>) field.get(Legend.class);
        list.clear();
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
        TreeMap<Integer, Legendable> legendList =
                (TreeMap<Integer, Legendable>) field.get( Legend.class );
        legendList.clear();
    }

    public static void LightingStandReset() throws Exception {
        Field generatedField = LightingStand.class.getDeclaredField( "SYMBOLGENERATED" );
        generatedField.setAccessible( true );
        boolean generated = generatedField.getBoolean( LightingStand.class );
        generatedField.set(generated, false);

        Field countField = LightingStand.class.getDeclaredField( "Count" );
        countField.setAccessible(true);
        Integer count = (Integer) countField.get( LightingStand.class );
        countField.set(count, 0);
    }

    public static void LuminaireReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = Luminaire.class.getDeclaredField( "LUMINAIRELIST" );
        field.setAccessible( true );
        ArrayList<Luminaire> luminaireList = (ArrayList<Luminaire>) field.get( Luminaire.class );
        luminaireList.clear();
    }

    /**
     * Reset the LUMINAIRELIST maintained by {@link LuminaireDefinition} to its initial empty
     * state.
     *
     * @throws NoSuchFieldException   if the {@code LUMINAIRELIST} field isn't there.
     * @throws IllegalAccessException if the {@code LUMINAIRELIST} field cannot be accessed.
     */
    // TODO Keep this until I resolve how Luminaire knows what type it is.
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
     * Reset the GEARS array maintained by {@link GearList} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code LIST} field isn't there.
     * @throws IllegalAccessException if the {@code LIST} field cannot be accessed.
     */
    public static void GearListReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = GearList.class.getDeclaredField( "GEARS" );
        field.setAccessible( true );
        ArrayList<String> list = (ArrayList<String>) field.get( GearList.class );
        list.clear();
    }

    /**
     * Reset the LIST maintained by {@link ElementalLister} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code LIST} field isn't there.
     * @throws IllegalAccessException if the {@code LIST} field cannot be accessed.
     */
    public static void ElementalListerReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = ElementalLister.class.getDeclaredField( "LIST" );
        field.setAccessible( true );
        ArrayList<ElementalLister> list = (ArrayList<ElementalLister>) field.get( ElementalLister.class );
        list.clear();
    }

    /**
     * Reset the LIST maintained by {@link ElementalLister} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code LIST} field isn't there.
     * @throws IllegalAccessException if the {@code LIST} field cannot be accessed.
     */
    public static void MinderDomReset() throws NoSuchFieldException, IllegalAccessException {
        ElementalListerReset();
    }

    /**
     * Reset the MOUNTABLELIST maintained by {@link Mountable} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code MOUNTABLELIST} field isn't there.
     * @throws IllegalAccessException if the {@code MOUNTABLELIST} field cannot be accessed.
     */
    public static void MountableReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Mountable.class.getDeclaredField( "MOUNTABLELIST" );
        field.setAccessible( true );
        ArrayList<Mountable> mountableList = (ArrayList<Mountable>) field.get( Mountable.class );
        mountableList.clear();
    }

    /**
     * Reset the STACKABLELIST maintained by {@link Stackable} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code STACKABLELIST} field isn't there.
     * @throws IllegalAccessException if the {@code STACKABLELIST} field cannot be accessed.
     */
    public static void SchematicReset() throws Exception {
        ArrayList<Schematicable> list = (ArrayList)
                TestHelpers.accessStaticObject(
                        "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
        list.clear();

        Schematic.LastX = 0.0;
        Schematic.LastY = Schematic.Increment;
        Schematic.LastWidth = Schematic.Spacer;
        Schematic.TotalWidth = 0.0;
    }

    /**
     * Reset the STACKABLELIST maintained by {@link Stackable} to its initial empty state.
     *
     * @throws NoSuchFieldException   if the {@code STACKABLELIST} field isn't there.
     * @throws IllegalAccessException if the {@code STACKABLELIST} field cannot be accessed.
     */
    public static void StackableReset() throws NoSuchFieldException, IllegalAccessException {
        Field field = Stackable.class.getDeclaredField( "STACKABLELIST" );
        field.setAccessible( true );
        ArrayList<Stackable> stackableList = (ArrayList<Stackable>) field.get( Stackable.class );
        stackableList.clear();
    }

//    /**
//     * Reset the PIPELIST maintained by {@link Pipe} to its initial empty state.
//     *
//     * @throws NoSuchFieldException   if the {@code PIPELIST} field isn't there.
//     * @throws IllegalAccessException if the {@code PIPELIST} field cannot be accessed.
//     */
//    public static void PipeReset() throws NoSuchFieldException, IllegalAccessException {
//        Field field = Pipe.class.getDeclaredField( "PIPELIST" );
//        field.setAccessible( true );
//        ArrayList<Pipe> pipelist = (ArrayList<Pipe>) field.get( Pipe.class );
//        pipelist.clear();
//    }

    /**
     * Reset {@link Point} to its initial state.
     * <p/>
     * Zeros out each of the extreme values maintained by {@link Point}.
     *
     * @throws NoSuchFieldException   if one of the fields isn't there.
     * @throws IllegalAccessException if one of the fields cannot be accessed.
     */
    public static void PointReset() throws NoSuchFieldException, IllegalAccessException {
        TestResets.ZeroDouble(Point.class, "SmallX");
        TestResets.ZeroDouble(Point.class, "SmallY");
        TestResets.ZeroDouble(Point.class, "SmallZ");
        TestResets.ZeroDouble(Point.class, "LargeX");
        TestResets.ZeroDouble(Point.class, "LargeY");
        TestResets.ZeroDouble(Point.class, "LargeZ");
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
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible( true );
        field.setInt(clazz, 0);
    }

    private static void ZeroDouble( Class clazz, String fieldName )
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = clazz.getDeclaredField( fieldName );
        field.setAccessible( true );
        field.set( clazz, new Double( 0.0 ) );
    }

    public static void WallReset()
            throws NoSuchFieldException, IllegalAccessException
    {
        Field field = Wall.class.getDeclaredField( "WallList" );
        field.setAccessible( true );
        ArrayList<Wall> wallList = (ArrayList<Wall>) field.get( Wall.class );
        wallList.clear();
    }

}
