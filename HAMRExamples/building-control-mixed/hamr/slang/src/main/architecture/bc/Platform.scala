// #Sireum

package bc

import bc.BuildingControl.device.DeviceBridge
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

    DeviceBridge.setup()

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