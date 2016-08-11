package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by DHS on 8/2/16.
  */
class Event ( element: Element ) extends UniqueId ( element )
  with Populate
{
  Event.Only = this

  tagCallback( PipeBase.Tag, processPipeBase )
  tagCallback( Flat.Tag, processFlat )
  populate( element )

  def processPipeBase( element: Element ): Unit = {
    new PipeBase( element )
  }

  def processFlat( element: Element ): Unit = {
    new Flat( element )
  }

//  if ( null == Venue.)

  def dom( draw: Draw, mode: View ): Unit = {

    val title = new StringBuilder( id )

    mode match {
      case View.PLAN => title.append( " - Plan view" )
      //    case View.TRUSS => return
      //    case View.SECTION => title.append( "Section view" )
      //    case View.FRONT => title.append( "Front view" )
      case _ => ;
    }

    draw.setDocumentTitle( title.toString() )
  }

  override def verify(): Unit = {
  }
}

object Event {
  var Only: Event = null
  def Name: String = {
    if ( null == Only ) {
      throw new InvalidXMLException( "Event is not defined." )
    }
  return Only.id
  }

  def Reset: Unit = {
    Only = null
  }
}
