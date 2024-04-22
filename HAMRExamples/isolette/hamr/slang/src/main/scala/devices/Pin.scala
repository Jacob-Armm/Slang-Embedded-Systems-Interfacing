// #Sireum
package devices

import org.sireum._
import platform.LPConn
import utils.PinModeUtil.PinMode

@record class Pin(pinAlias: String, mode: PinMode.Type) {

  var pinMode: PinMode.Type = mode

  def read: Z = {
    return LPConn.read(pinAlias, pinMode)
  }

  def write(value: Z): Unit = {
    LPConn.write(pinAlias, pinMode, value)
  }

  def PinModeSet(pm: PinMode.Type): Unit = {
    pinMode = pm
  }
}
