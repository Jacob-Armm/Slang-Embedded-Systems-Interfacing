// #Sireum
package platform

import org.sireum._
import utils.PinModeUtil.PinMode
import devices.Pin

@ext object LPConn {

  def init(deviceSets: ISZ[DeviceSet], logicalPins: ISZ[Pin]): Unit = $

  def ready: B = $

  def read(pin: String, mode: PinMode.Type): Z = $

  def write(pin: String, mode: PinMode.Type, value: Z): Unit = $
}
