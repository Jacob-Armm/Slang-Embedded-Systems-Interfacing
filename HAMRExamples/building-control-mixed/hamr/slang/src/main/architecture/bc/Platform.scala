// #Sireum

package bc

import org.sireum._
import bc.BuildingControl.{Fan_i_tcp_fan, TempSensor_i_tcp_tempSensor}
import platform.LPConn
import devices._
import platform.impl.PlatformImpl
import utils.Config
import utils.PinModeUtil._

// This file will not be overwritten so is safe to edit

object Platform {

  def setup(): Unit = {
    // BEGIN MARKER PLATFORM SETUP
    {
      // Contributions from GumboX Plugin
      bc.runtimemonitor.RuntimeMonitor.init(bc.runtimemonitor.ModelInfo.modelInfo)
    }

    val fanPin: Pin = Pin("fan", PinMode.OUTPUT)
    val tempSensorPin: Pin = Pin("tempSensor", PinMode.ANALOG)

    val pinMap: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      fanPin.pinAlias ~> 13,
      tempSensorPin.pinAlias ~> 14
    )

    val conf: Config = Config(pinMap, implGetter.getImpl(pinMap), None())

    LPConn.init(conf, ISZ(fanPin, tempSensorPin))

    val fan: LED = LED(fanPin)
    val tempSensor: Potentiometer = Potentiometer(tempSensorPin)

    Fan_i_tcp_fan.device = MSome(fan)
    TempSensor_i_tcp_tempSensor.device = MSome(tempSensor)
    // END MARKER PLATFORM SETUP
  }

  def tearDown(): Unit = {
    // BEGIN MARKER PLATFORM TEARDOWN
    {
      // Contributions from GumboX Plugin
      bc.runtimemonitor.RuntimeMonitor.finalise()
    }
    // END MARKER PLATFORM TEARDOWN
  }
}

@ext object implGetter {
  def getImpl(pinMap: Map[String, Z]): PlatformImpl = $
}