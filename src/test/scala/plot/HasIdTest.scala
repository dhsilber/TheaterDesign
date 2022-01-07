package com.mobiletheatertech
package plot

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.equal
import org.scalatest._
import flatspec._
import matchers._

class HasIdTest extends AnyFlatSpec with should.Matchers {

  "A new HasId" should "have a null id" in {
        val foo = new HasId()
        foo.id should equal (null)
  }

  it should "store an id" in {
    val foo = new HasId()
    foo.id = "identifier"
    foo.id should equal ("identifier")
  }

}
