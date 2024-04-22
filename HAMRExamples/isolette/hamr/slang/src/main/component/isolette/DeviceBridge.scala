// #Sireum
package isolette

import org.sireum._
import devices.{HeatingPad, LED, Pin, Potentiometer}
import platform.{DeviceSet, LPConn}
import platform.impl.PlatformImpl
import utils.PinModeUtil.PinMode
import utils.Converter
import Isolette_Data_Model._


//TODO: Reroute the ALARM
object DeviceBridge {

  var heatPad: HeatingPad = HeatingPad.createDevice(Pin("", PinMode.OUTPUT))
  var ledAlarm: LED = LED.createDevice(Pin("", PinMode.OUTPUT))
  var pot: Potentiometer = Potentiometer.createDevice(Pin("", PinMode.ANALOG))

  def setup(): Unit = {
    val heaterPin: Pin = Pin("heater", PinMode.OUTPUT)
    val alarmPin: Pin = Pin("alarm", PinMode.OUTPUT)
    val tempSensorPin: Pin = Pin("tempSensor", PinMode.ANALOG)

    val pinMapF1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      tempSensorPin.pinAlias ~> 54,
      alarmPin.pinAlias ~> 12
    )
    val deviceSetFirmata1: DeviceSet = DeviceSet("F1", implGetter.getImpl("Firmata", pinMapF1), None())

    val pinMapF2: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      heaterPin.pinAlias ~> 11
    )
    val deviceSetFirmata2: DeviceSet = DeviceSet("F2", implGetter.getImpl("Firmata", pinMapF2), None())

    LPConn.init(ISZ(deviceSetFirmata1, deviceSetFirmata2), ISZ(heaterPin, alarmPin, tempSensorPin))

    heatPad = HeatingPad(heaterPin)
    ledAlarm = LED(alarmPin)
    pot = Potentiometer(tempSensorPin)
  }

  object Heater {
    var state: On_Off.Type = On_Off.Off

    def setState(cmd: On_Off.Type): Unit = {
      cmd match {
        case On_Off.Onn =>
          heatPad.on()
        case On_Off.Off =>
          heatPad.off()
      }
    }
  }
  object Alarm {
    var state: On_Off.Type = On_Off.Off

    def setState(cmd: On_Off.Type): Unit = {
      cmd match {
        case On_Off.Onn =>
          ledAlarm.on()
        case On_Off.Off =>
          ledAlarm.off()
      }
    }
  }

  object TempSensor {
    def getCurrentTemp:Temp_impl = {
      val tempScaled = Converter.ZtoF(map(pot.getPotValue, 0, 1023, 90, 105))
      return Temp_impl(tempScaled)
    }

    def map(x: Z, in_min: Z, in_max: Z, out_min: Z, out_max: Z): Z = {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }
  }
}

@ext object implGetter {
  def getImpl(imp: String, pinMap: Map[String, Z]): PlatformImpl = $
}
