// #Sireum

package bc.BuildingControl.device

import org.sireum._
import devices.{LED, Potentiometer}
import bc.BuildingControl.{FanAck, FanCmd, TempUnit, Temperature_i, Util}

object DeviceBridge {
  def getCurrentTemp(device: Potentiometer):Temperature_i = {
    val minTempZ: Z = Converter.FtoZ(Util.minTemp)
    val maxTempZ: Z = Converter.FtoZ(Util.maxTemp)
    val tempScaled = Converter.ZtoF(map(device.getPotValue, 0, 1023, minTempZ, maxTempZ))
    return Temperature_i(tempScaled, TempUnit.Fahrenheit)
  }

  def sendFanCmd(device: LED, cmd: FanCmd.Type): FanAck.Type = {

    cmd match {
      case FanCmd.On =>
        device.on()
      case FanCmd.Off =>
        device.off()
    }

    return FanAck.Ok
  }

  //TODO: Put in make all analog input take upper and lower range as inputs to read
  def map(x: Z, in_min: Z, in_max: Z, out_min: Z, out_max: Z): Z = {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
  }
}

@ext object Converter {
  def FtoZ(value: F32): Z = $
  def ZtoF(value: Z): F32 = $
}
