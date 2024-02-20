// #Sireum
package platform.impl

import org.sireum._
import utils.PinModeUtil.PinMode


@sig trait PlatformImpl {
  def init(port: Option[String]): Unit

  def retievePinList: Map[Z, ISZ[PinMode.Type]]

  def retrievePinMap: Map[String, Z]

  def ready: B

  def read(pin: String, mode: PinMode.Type): Z

  def write(pin: String, mode: PinMode.Type, value: Z): Unit
}
