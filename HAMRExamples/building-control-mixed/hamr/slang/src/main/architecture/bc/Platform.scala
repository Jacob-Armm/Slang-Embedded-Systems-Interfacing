// #Sireum

package bc

import org.sireum._
import bc.BuildingControl.device.DeviceBridge

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