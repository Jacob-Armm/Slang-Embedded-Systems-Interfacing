// #Sireum

package demos.HeatinPad

import org.sireum._
import devices.{LEDPWM, Pin, Potentiometer}
import platform.{DeviceSet, LPConn}
import platform.impl.PlatformImpl
import utils.PinModeUtil.PinMode

object HeatingPadExample extends App {
  override def main(args: ISZ[String]): Z = {
    val p: Pin = Pin("p", PinMode.PWM)

    val pmap1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      p.pinAlias ~> 11
    )

    val dset: DeviceSet = DeviceSet("dset1", implGetter.getImpl(pmap1), None())

    LPConn.init(ISZ(dset), ISZ(p))

    val d: LEDPWM = LEDPWM(p)

    while(T) {
      d.setValue(200)
    }
    return 0
  }
}

@ext object implGetter {
  def getImpl(pinMap: Map[String, Z]): PlatformImpl = $
}

