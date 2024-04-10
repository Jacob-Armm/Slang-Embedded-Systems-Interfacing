// #Sireum
package demos.StepperMotorExample

import org.sireum._
import devices.Pin
import platform.{DeviceSet, LPConn}
import platform.impl.PlatformImpl
import utils.PinModeUtil.PinMode
import devices._

object StepperMotorExample extends App {

  def main(args: ISZ[String]): Z = {
    val SMn1: Pin = Pin("n1", PinMode.OUTPUT)
    val SMn2: Pin = Pin("n2", PinMode.OUTPUT)
    val SMn3: Pin = Pin("n3", PinMode.OUTPUT)
    val SMn4: Pin = Pin("n4", PinMode.OUTPUT)

    val pmap1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      "n1" ~> 11,
      "n2" ~> 10,
      "n3" ~> 9,
      "n4" ~> 8
    )

    val dset: DeviceSet = DeviceSet("dset1", implGetter.getImpl(pmap1), None())

    LPConn.init(ISZ(dset), ISZ(SMn1, SMn2, SMn3, SMn4))

    val SM: StepperMotor = StepperMotor(60, 4, SMn1, SMn2, SMn3, SMn4)

    if(LPConn.ready) {
      while(T) {
        SM.step(2)
        SM.setRPM(3)
        SM.step(-2)
        SM.setRPM(SM.defaultRPM)
      }
    }

    assert(F, "Board was not ready")

    return 0
  }
}

@ext object implGetter {
  def getImpl(pinMap: Map[String, Z]): PlatformImpl = $
}
