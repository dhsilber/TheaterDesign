package com.mobiletheatertech.plot


import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting


/**
  * Created by DHS on 7/27/16.
  */
trait LinearSupportsClamp {

  val IsClampList = new ArrayBuffer[ Luminaire ]
  var sortedClampList: Array[ Luminaire ] = new Array[Luminaire]( 0 )

  var name: String

  var based: Boolean = _ // true implies vertical
  var positioned: Boolean = false // true implies horizontal
  var suspended: Boolean = false
  val hasVertex: Boolean = false
  var start: Point = null


  @throws[MountingException]
  def hang( luminaire: Luminaire, location: Location ): Unit = {
    if( null == luminaire )
      throw new DataException( "mounted element unexpectedly null!" )

    if( ! hasVertex && location.vertex.valid ) {
      throw new NumberFormatException()
    }

    val distance = location.distance

    if( ! distance.valid  ) {
      throw new MountingException( "Location specified does not contain a valid distance." )
    }

    if ( distance.value < minLocation || maxLocation < distance.value ) {
      println( "minLocation: " + minLocation.toString )
      println( "location: " + location.toString )
      println( "maxLocation: " + maxLocation.toString )
      throw new MountingException( name + " does not include invalid location " + location.toString + "." )
    }

    IsClampList += luminaire

//    luminaire.position( new Point( 1.2, 3.4, 5.6 ) )
  }

  def loads: Array[ IsClamp ] = {
    return IsClampList.toArray
  }

  def contains( item: Luminaire ): Boolean = {
    IsClampList contains item
  }

  def origin(): Point = {
    new Point( start.x + SvgElement.OffsetX(), start.y + SvgElement.OffsetY(), start.z )
  }

  def orientation(): Double = ???

  def minLocation: Double = ???

  def maxLocation: Double = ???

  /*
   The Pipe & Truss versions of this are the same except that
   - Pipe supports a 90-degree rotation of a positioned pipe
   - Pipe uses support1 where Truss uses point1
  */
  def rotatedLocation( location: Location ): Place = ???

//  def weights( id: String ): String = {
//    val text: StringBuilder = new StringBuilder
//    text.append("Weights for ")
//    text.append(id)
//    text.append("\n\n")
////    text.append(suspensionPoints)
////    text.append("\n\n")
//    var totalWeight: Double = 0.0
//    for (lumi <- loads) {
////      text.append(lumi.unit)
////      text.append(": ")
////      text.append(lumi.`type`)
////      text.append(" at ")
////      text.append(lumi.locationValue)
////      text.append(" weighs ")
////      val weight: Double = lumi.weight
////      totalWeight += weight
////      text.append(weight.toString)
//      text.append(" pounds.")
//      text.append("\n")
//    }
//    text.append("\nTotal: ")
//    text.append(totalWeight.toString)
//    text.append(" pounds")
//    text.append("\n")
//    return text.toString
//  }

  def numberLuminaires(): Unit = {
    sortedClampList = IsClampList.toArray
    Sorting.quickSort(sortedClampList)(LocationOrdering)
    //    val sort = new Sort(
    //    val sortedLocations = locationOrdering IsClampList )
    var unit = 0
//    println()
    for (luminaire <- sortedClampList) {
      unit += 1
      luminaire.unit(unit)
//      println( "on: " + luminaire.on +
//        ", Luminaire: " + luminaire.unit() +
//        ", type: " + luminaire.`type`() +
//        ", location: " + luminaire.location() )
    }
  }
}

object LinearSupportsClamp {

  @throws[MountingException]
  def Select(id: String): LinearSupportsClamp = {
    for ( selection <- ElementalLister.List() ) {
      if ( null != selection.id )
        if ( selection.id.equals( id ) )
          if ( selection.isInstanceOf[LinearSupportsClamp] )
            return selection.asInstanceOf[LinearSupportsClamp]
    }
    null
  }

}