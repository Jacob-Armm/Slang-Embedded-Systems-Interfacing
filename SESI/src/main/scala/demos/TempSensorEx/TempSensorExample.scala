// #Sireum
package demos.TempSensorEx

import org.sireum._
import devices.Pin
import platform.{DeviceSet, LPConn}
import platform.impl.PlatformImpl
import utils.PinModeUtil.PinMode
import devices._
import utils.Time

object TempSensorExample extends App {

  def main(args: ISZ[String]): Z = {
    val n1: Pin = Pin("n1", PinMode.OUTPUT)

    val pmap1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      n1.pinAlias ~> 11
    )

    val dset: DeviceSet = DeviceSet("dset1", implGetter.getImpl(pmap1), None())

    LPConn.init(ISZ(dset), ISZ(n1))
    val startTime: Z = Time.currentMicros

    val ts: DHTTempSensor = DHTTempSensor(n1, startTime)

    if(LPConn.ready) {
      while(T) {
        print(ts.read())
      }
    }

    assert(F, "Board was not ready")

    return 0
  }
}

@ext object implGetter {
  def getImpl(pinMap: Map[String, Z]): PlatformImpl = $
}
