// #Sireum

package isolette

import org.sireum._

// This file will not be overwritten so is safe to edit

object Platform {

  def setup(): Unit = {
    // BEGIN MARKER PLATFORM SETUP
    // END MARKER PLATFORM SETUP
    DeviceBridge.setup()
  }

  def tearDown(): Unit = {
    // BEGIN MARKER PLATFORM TEARDOWN
    // END MARKER PLATFORM TEARDOWN
  }
}