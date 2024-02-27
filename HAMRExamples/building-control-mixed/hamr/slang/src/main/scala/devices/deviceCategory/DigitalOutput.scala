// #Sireum
package devices.deviceCategory

import org.sireum._

@sig trait DigitalOutput {
  def on(): Unit
  def off(): Unit
}
