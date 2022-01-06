package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by dhs on 7/15/15.
  */
class PipeBase(element: Element) extends MinderDom(element)
  with Gear
  with Populate
  with Legendable {
  val idForLocalUse = getStringAttribute("id")
  val x = getDoubleAttribute("x")
  val y = getDoubleAttribute("y")
  val z = getOptionalDoubleAttributeOrZero("z")

  tagCallback(Pipe.LayerTag, processPipe)
  populate(element)


  val processedMark = Mark.Generate()
  element.setAttribute("processedMark", processedMark)

  if (!PipeBase.LegendRegistered) {
    Legend.Register(this, 2.0, 2.0, LegendOrder.Structure)
    PipeBase.LegendRegistered = true
  }
  PipeBase.LegendCount += 1

  var drawPlace: Point = null


  def verify(): Unit = {
    drawPlace = Proscenium.LocateIfActive(new Point(x, y, z))
  }

  def mountPoint(): Point = {
    new Point(x, y, z + PipeBase.mountPointAdjusmentZ)
  }

  def processPipe(element: Element): Unit = {
//    println("Making new pipe for PipeBase " + idForLocalUse)
    new Pipe(element, this)
  }

  def dom(draw: Draw, mode: View): Unit = {
    mode match {
      case View.TRUSS =>
        return

      case View.PLAN =>
//        println(s"Drawing PipeBase for $idForLocalUse")
        val group = MinderDom.svgClassGroup(draw, PipeBase.Tag)
        draw.appendRootChild(group)

        val circle = group.circle(draw, drawPlace.x(), drawPlace.y(), 18.0, PipeBase.Color)
        circle.attribute("stroke-opacity", "0.5")
//        group.text(draw, idForLocalUse, drawPlace.x() - 18.0, drawPlace.y() + 18.0, PipeBase.Color)

      case default =>
      //        return
    }
  }

  // From Legendable. Is this really needed:
  override def legendCountReset(): Unit = ???

  override def domLegendItem(draw: Draw, start: PagePoint): PagePoint = {
    val group = MinderDom.svgClassGroup(draw, PipeBase.Tag)
    group.attribute("transform", "translate(" + start.x + "," + start.y + ")")
    draw.appendRootChild(group)

    group.circleAbsolute(draw, 5.0, 2.0, 12.0, PipeBase.Color)
    group.circleAbsolute(draw, 5.0, 2.0, 2.0, PipeBase.Color)
    group.textAbsolute(draw, PipeBase.Tag, Legend.TEXTOFFSET, 8.0, Legend.TEXTCOLOR)
    group.textAbsolute(draw, PipeBase.LegendCount.toString,
      Legend.QUANTITYOFFSET, 8.0, Legend.TEXTCOLOR)

    return new PagePoint(start.x(), start.y() + PipeBase.LegendHeight)
  }
}

object PipeBase {

  final val Tag: String = "pipebase"
  final val Color = "blue"
  final val LegendHeight = 2.0
  final val mountPointAdjusmentZ = 2.0

  final var LegendRegistered: Boolean = false
  final var LegendCount: Int = 0


  def Find(mark: String): PipeBase = {

    val list = ElementalLister.List()

    for (item <- 0 until list.size()) {
      val thingy = list.get(item)
      if (thingy.isInstanceOf[PipeBase]) {
        if (thingy.asInstanceOf[PipeBase].processedMark == mark) {
          return thingy.asInstanceOf[PipeBase]
        }
      }
    }


    // This didn't work at all:
    //    for( thingy <- ElementalLister.List() ) {
    // ... It seems like Scala needs one of its own collection types for this syntax to work.


    null
  }

  def Reset(): Unit = {
    LegendRegistered = false
    LegendCount = 0
  }
}
