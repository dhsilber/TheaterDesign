package com.mobiletheatertech.plot

class ValidatedDouble(validFlag: Boolean, number: Double )
  extends ValidatedValue( validFlag ) {

  def value: Double = {
    number
  }
}
