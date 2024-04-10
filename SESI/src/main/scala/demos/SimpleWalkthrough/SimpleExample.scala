// #Sireum
package demos.SimpleWalkthrough

import org.sireum._
import devices._
import utils._
import platform.impl._
import platform._
import utils.PinModeUtil.PinMode

object SimpleExample extends App {
  override def main(args: ISZ[String]): Z = {
    // First, you define all the proxy pins that will be used in order to define
    // the connection between the physical and abtract devices

    val SMn1: Pin = Pin("n1", PinMode.OUTPUT)
    val SMn2: Pin = Pin("n2", PinMode.OUTPUT)
    val SMn3: Pin = Pin("n3", PinMode.OUTPUT)
    val SMn4: Pin = Pin("n4", PinMode.OUTPUT)

    // In this case we defined 4 pins that are set to digital output

    // We will now define a pinMap to define which proxy will represent which pin for a specific platform

    val pmap1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      "n1" ~> 11,
      "n2" ~> 10,
      "n3" ~> 9,
      "n4" ~> 8
    )

    // In this case the pinMap will match these pins to one deviceSet which symbolizes all the connections
    // to a specific platform. In essence this device set matches these four pins to one firmata instance.

    val dset: DeviceSet = DeviceSet("dset1", implGetter.getImpl(pmap1), None())

    // To initialize the abstract to physical communication layer you call LPConn.init. This method initializes all
    // platforms and creates a universal pin map which acts similarly to the normal pinMap. The difference is that
    // the universal pinMap, maps pin alias to both platform and pin number instead of just a pin number. This is done
    // to direct the flow of pin commands to their respective platform when calling read and write.

    LPConn.init(ISZ(dset), ISZ(SMn1, SMn2, SMn3, SMn4))

    // Now that the architecture and all platforms have been instantiated, we can now define the abstract devices
    // which act as proxies for the real devices by defining how certain actions will translated to pin commands
    // that will be handled by the underlying proxy pins

    val SM: StepperMotor = StepperMotor(60, 4, SMn1, SMn2, SMn3, SMn4)
    // 60 is the default rpm
    // 4 is the number of steps per full rotation

    // Now that the entire architecture has been defined we can either write the behavior for the system
    // or connect to a preexisting architecture that defines behavior such as the output from HAMR

    if(LPConn.ready) { //we need to ensure that all platforms are read before trying to make calls to the proxies
      while(T) {
        SM.step(2) //step make the stepper motor take n number of steps
        SM.setRPM(3) //setRPM changes the RPM of the stepper motor
        SM.step(-2) //when steps is called with a negative number, the stepper motor rotates in the opisite direction
        SM.setRPM(SM.defaultRPM)
      }
    }

    assert(F, "Board was not ready")

    return 0
  }
}

// This object exist as a way to retrieve a platform from the scala world since most platforms are written outside
// of a slang context. PlatformImpl is a way in which we can pass around non-Slang platform in a slang context since the
// trait is written in slang.
@ext object implGetter {
  def getImpl(pinMap: Map[String, Z]): PlatformImpl = $
}
