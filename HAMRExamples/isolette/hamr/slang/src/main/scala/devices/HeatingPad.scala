// #Sireum
package devices

import org.sireum._
import platform.LPConn
import utils.PinModeUtil.PinMode

@record class HeatingPad(pin: Pin) {
  def on(): Unit = {
    pin.write(1)
  }

  def off(): Unit = {
    pin.write(0)
  }
}

object HeatingPad {
  def createDevice(pin: Pin): HeatingPad = {
    assert(pin.mode == PinMode.OUTPUT, "Invalid pinMode for LED")
    return HeatingPad(pin)
  }
}

