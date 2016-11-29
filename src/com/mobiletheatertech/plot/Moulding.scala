package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by DHS on 9/24/16.
  */
class Moulding( element: Element ) extends Elemental( element ) {
  val depth = getDoubleAttribute( "depth" )
  val width = getDoubleAttribute( "width" )
  val side = getStringAttribute( "side" )

  if ( "both" != side ) throw new AttributeInvalidException(
    this.getClass.getSimpleName, new InstanceId( "" ), "side" )
}

object Moulding {
  final val Tag = "moulding"
}
