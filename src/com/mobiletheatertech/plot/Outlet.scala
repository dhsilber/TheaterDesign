package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by dhs on 3/1/17.
  */
class Outlet( element: Element ) extends MinderDom( element ) {
  override def verify(): Unit = {}

  override def dom(draw: Draw, mode: View): Unit = {}
}

object Outlet {
  final val Tag = "outlet"
}
