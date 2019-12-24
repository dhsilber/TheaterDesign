package com.mobiletheatertech.plot

class ValidatedChar( validFlag: Boolean, letter: Char )
  extends ValidatedValue( validFlag ) {

  def value: Char = {
    letter
  }
}
