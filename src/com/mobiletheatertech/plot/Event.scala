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
  tagCallback( TrussBase.Tag, processTrussBase )
  tagCallback( Truss.Tag, processTruss )
  tagCallback( Flat.Tag, processFlat )
  tagCallback( Luminaire.LAYERTAG, processLuminaire )
  println( "Event poplulating...")
  populate( element )
  println( "Event done poplulating.")

  def processPipeBase( element: Element ): Unit = {
    new PipeBase( element )
  }

  def processTrussBase( element: Element ): Unit = {
    println( "Event about to create TrussBase")
    new TrussBase( element )
  }

  def processTruss( element: Element ): Unit = {
    println( "Event about to create Truss")
    new Truss( element )
    println( "Event completed creating Truss")
  }

  def processFlat( element: Element ): Unit = {
    new Flat( element )
  }

  def processLuminaire( element: Element ): Unit = {
    new Luminaire( element )
  }

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
  final val Tag: String = "event"

  var Only: Event = null

  def Name: String = {
    if ( null == Only ) {
      throw new InvalidXMLException( "Event is not defined." )
    }
    Only.id
  }

  def Reset: Unit = {
    Only = null
  }
}
