package com.mobiletheatertech.plot

/**
  * Created by DHS on 9/25/16.
  */
class AttributeInvalidException( tag: String, id: InstanceId, attribute: String )
  extends Exception( s"$tag ${id.id} has invalid '$attribute' attribute." ) {
}
