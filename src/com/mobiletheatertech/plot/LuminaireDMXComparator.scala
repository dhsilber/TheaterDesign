package com.mobiletheatertech.plot

import java.util.Comparator

class LuminaireDMXComparator extends Comparator[Luminaire] {

  def compare(o1: Luminaire, o2: Luminaire): Int = {
    val dmx1 = o1.dimmer()
    val dmx2 = o2.dimmer()

    // ascending order (descending order would be: name2.compareTo(name1))
    dmx1.compareTo(dmx2)
  }
}
