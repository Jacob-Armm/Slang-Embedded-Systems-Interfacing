// #Sireum

package bc.BuildingControl.device

import org.sireum._
import devices.{LED, Pin, Potentiometer}
import bc.BuildingControl.{FanAck, FanCmd, Fan_i_tcp_fan, TempSensor_i_tcp_tempSensor, TempUnit, Temperature_i, Util}
import platform.LPConn
import platform.impl.PlatformImpl
import utils.PinModeUtil.PinMode
import utils.{Config, Converter, DeviceBehavior}


object DeviceBridge {

  var led: LED = LED(Pin("", PinMode.OUTPUT))
  var pot: Potentiometer = Potentiometer(Pin("", PinMode.OUTPUT))

  def setup(): Unit = {
    val fanPin: Pin = Pin("fan", PinMode.OUTPUT)
    val tempSensorPin: Pin = Pin("tempSensor", PinMode.ANALOG)

    val pinMap: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      fanPin.pinAlias ~> 13,
      tempSensorPin.pinAlias ~> 14
    )

    val conf: Config = Config(pinMap, implGetter.getImpl("Firmata", pinMap), None())

    LPConn.init(conf, ISZ(fanPin, tempSensorPin))

    led = LED(fanPin)
    pot = Potentiometer(tempSensorPin)
  }

  object Fan {
    var state: FanAck.Type = FanAck.Ok
    var behavior: Option[DeviceBehavior.Type] = None()

    def start(b: DeviceBehavior.Type): Unit = {
      behavior = Some(b)
    }

    def setState(cmd: FanCmd.Type): Unit = {
      behavior match {
        case Some(t) =>
          t match {
            case DeviceBehavior.Stateful => {
              cmd match {
                case FanCmd.On =>
                  led.on()
                case FanCmd.Off =>
                  led.off()
              }
              state = FanAck.Ok
            }
            case DeviceBehavior.Asynchronous => {
              cmd match {
                case FanCmd.On =>
                  led.on()
                case FanCmd.Off =>
                  led.off()
              }
              state = FanAck.Ok
            }
          }
      }
    }
  }

  object TempSensor {
    var state: Z = 0
    var behavior: Option[DeviceBehavior.Type] = None()

    def start(b: DeviceBehavior.Type): Unit = {
      behavior = Some(b)
      updateState()
    }

    def updateState(): Unit = {
      behavior match {
        case Some(t) =>
          t match {
            case DeviceBehavior.Stateful => state = pot.getPotValue
            case DeviceBehavior.Continuous => while(T) {state = pot.getPotValue} //TODO: Open A Thread
            case DeviceBehavior.Asynchronous => state = pot.getPotValue //TODO: Open A Thread
          }
        case None() => assert(F)
      }
    }

    def getCurrentTemp():Temperature_i = {
      if(behavior.get != DeviceBehavior.Continuous) {updateState()}
      val minTempZ: Z = Converter.FtoZ(Util.minTemp)
      val maxTempZ: Z = Converter.FtoZ(Util.maxTemp)
      val tempScaled = Converter.ZtoF(map(state, 0, 1023, minTempZ, maxTempZ))
      return Temperature_i(tempScaled, TempUnit.Fahrenheit)
    }

    def map(x: Z, in_min: Z, in_max: Z, out_min: Z, out_max: Z): Z = {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }
  }
}

@ext object implGetter {
  def getImpl(imp: String, pinMap: Map[String, Z]): PlatformImpl = $
}
