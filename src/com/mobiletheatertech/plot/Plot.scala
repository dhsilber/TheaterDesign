package com.mobiletheatertech.plot

import org.w3c.dom.Element

class Plot( element: Element )
  extends MinderDom( element: Element )
 with Populate
{
  tagCallback( LuminaireDefinition.Tag, processLuminaireDefinition )

  populate( element )

  def processLuminaireDefinition( element: Element ): Unit = {
    new LuminaireDefinition( element )
  }


  override def dom( draw: Draw, mode: View ): Unit = {
  }

  override def verify(): Unit = {}

}
