// #Sireum

package bc.BuildingControl

import org.sireum._
import bc.BuildingControl.device.DeviceBridge
import bc._
import devices._
import utils.DeviceBehavior

// This file will not be overwritten so is safe to edit
object TempSensor_i_tcp_tempSensor {

  def initialise(api: TempSensor_i_Initialization_Api): Unit = {
    api.put_currentTemp(BuildingControl.Temperature_i.example())
    api.put_tempChanged()
  }

  def timeTriggered(api: TempSensor_i_Operational_Api): Unit = {
    val temp = DeviceBridge.TempSensor.getCurrentTemp()
    api.put_currentTemp(temp)
    api.put_tempChanged()
    val degree = Util.toFahrenheit(temp).degrees
    api.logInfo(s"Sensed temperature: $degree F")
  }

  def activate(api: TempSensor_i_Operational_Api): Unit = {}

  def deactivate(api: TempSensor_i_Operational_Api): Unit = {}

  def finalise(api: TempSensor_i_Operational_Api): Unit = {}

  def recover(api: TempSensor_i_Operational_Api): Unit = {}
}