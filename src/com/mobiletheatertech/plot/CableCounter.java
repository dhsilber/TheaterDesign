package com.mobiletheatertech.plot;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by dhs on 1/3/15.
 */
public class CableCounter {

    ArrayList<LinkedList<CableRun>> cablesIn = new ArrayList<>( Direction.Left.ordinal() + 1 );

    public CableCounter() {
        for ( Direction way : Direction.values() ) {
             cablesIn.add( way.ordinal(), new LinkedList<CableRun>());
        }
    }

    public void add( Direction direction, CableRun run ) {
        cablesIn.get( direction.ordinal() ).add( run );
    }

    public int count( Direction direction ) {
        return cablesIn.get( direction.ordinal() ).size();
    }

    public int index( Direction direction, CableRun run ) {
        return cablesIn.get( direction.ordinal() ).indexOf( run );
    }

    public PagePoint cableIntersectPosition( Solid solid, PagePoint pagePoint, CableRun cableRun )
            throws CorruptedInternalInformationException
    {
        Direction way = findApproach( cableRun );

        Double span;
        Double offsetToEdge;
        switch (way) {
            case UP:
            case Down:
                span = solid.width();
                offsetToEdge = solid.height() / 2;
                break;
            default:
                span = solid.height();
                offsetToEdge = solid.width() / 2;
                break;
        }

        int count = count( way );
        int index = index( way, cableRun );
        Double distance = span / (count + 1) * (index + 1);

        PagePoint entryPoint = new PagePoint( 0.0, 0.0 );
        switch (way) {
            case UP:
                entryPoint = new PagePoint( pagePoint.x() - span / 2 + distance, pagePoint.y() - offsetToEdge );
                break;
            case Down:
                entryPoint = new PagePoint( pagePoint.x() - span / 2 + distance, pagePoint.y() + offsetToEdge );
                break;
            case Right:
                entryPoint = new PagePoint( pagePoint.x() + offsetToEdge, pagePoint.y() + span / 2 - distance );
                break;
            case Left:
                entryPoint = new PagePoint( pagePoint.x() - offsetToEdge, pagePoint.y() + span / 2 - distance );
                break;
        }

        return entryPoint;
    }

    private Direction findApproach( CableRun cableRun ) throws CorruptedInternalInformationException {
        for ( Direction direction : Direction.values() ) {
            if ( cablesIn.get( direction.ordinal() ).contains( cableRun ) ) {
                return direction;
            }
        }
        throw new CorruptedInternalInformationException(
                "CableRun should have been registered with CableCounter." );
    }
}
