// #Sireum
package utils

import org.sireum._

object PinModeUtil {
  @enum object PinMode {
    "INPUT" //Digital pin in input mode
    "OUTPUT" // Digital pin in output mode
    "ANALOG" // Analog pin in analog input mode
    "PWM" // Digital pin in PWM output mode
    "SERVO"
    "SHIFT"
    "I2C"
    "ONEWIRE"
    "STEPPER"
    "ENCODER"
    "SERIAL"
    "PULLUP"
  }
}
