package com.mobiletheatertech.plot

abstract class ValidatedValue( validFlag: Boolean ) {

  def valid: Boolean = {
    validFlag
  }

}
