// #Sireum

package bc.BuildingControl.device

import org.sireum._
import devices.{LED, Pin, Potentiometer}
import bc.BuildingControl.{FanAck, FanCmd, TempUnit, Temperature_i, Util}
import platform.{DeviceSet, LPConn}
import platform.impl.PlatformImpl
import utils.PinModeUtil.PinMode
import utils.{Converter, DeviceBehavior}


object DeviceBridge {

  var led: LED = LED.createDevice(Pin("", PinMode.OUTPUT))
  var pot: Potentiometer = Potentiometer.createDevice(Pin("", PinMode.ANALOG))

  def setup(): Unit = {
    val fanPin: Pin = Pin("fan", PinMode.OUTPUT)
    val tempSensorPin: Pin = Pin("tempSensor", PinMode.ANALOG)


    val pinMapG1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(fanPin.pinAlias ~> 1)
    val deviceSetGUI1: DeviceSet = DeviceSet("G1", implGetter.getImpl("GUI", pinMapG1), None())

    val pinMapF1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(fanPin.pinAlias ~> 13)
    val deviceSetFirmata1: DeviceSet = DeviceSet("F1", implGetter.getImpl("Firmata", pinMapF1), None())

    val pinMapF2: Map[String, Z] = Map.empty[String, Z] ++ ISZ(tempSensorPin.pinAlias ~> 14)
    val deviceSetFirmata2: DeviceSet = DeviceSet("F2", implGetter.getImpl("Firmata", pinMapF2), None())

    val setMap: Map[String, ISZ[DeviceSet]] = Map.empty[String, ISZ[DeviceSet]] ++ ISZ(
      "GUIFirmataHybrid" ~> ISZ(deviceSetGUI1, deviceSetFirmata2),
      "MultipleFirmata" ~> ISZ(deviceSetFirmata1, deviceSetFirmata2)
    )

    LPConn.init(setMap.get("MultipleFirmata").get, ISZ(fanPin, tempSensorPin))

    led = LED(fanPin)
    pot = Potentiometer(tempSensorPin)

    fetchTemp.init(pot)
    fanSet.init(led, 0)
  }

  object Fan {
    var state: FanAck.Type = FanAck.Ok

    def setState(cmd: FanCmd.Type): Unit = {
      cmd match {
        case FanCmd.On =>
          fanSet.setState(1)
        case FanCmd.Off =>
          fanSet.setState(0)
      }
      state = FanAck.Ok
    }
  }

  object TempSensor {
    def getCurrentTemp():Temperature_i = {
      val minTempZ: Z = Converter.FtoZ(Util.minTemp)
      val maxTempZ: Z = Converter.FtoZ(Util.maxTemp)
      val tempScaled = Converter.ZtoF(map(fetchTemp.getState(), 0, 1023, minTempZ, maxTempZ))
      return Temperature_i(tempScaled, TempUnit.Fahrenheit)
    }

    def map(x: Z, in_min: Z, in_max: Z, out_min: Z, out_max: Z): Z = {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }
  }
}

@ext object fetchTemp {
  def init(device: Potentiometer): Unit = $
  def getState(): Z = $
}

@ext object fanSet {
  def init(device: LED, initVal: Z): Unit = $

  def setState(v: Z): Unit = $
}

@ext object implGetter {
  def getImpl(imp: String, pinMap: Map[String, Z]): PlatformImpl = $
}
