// #Sireum
package demos.StepperMotorAssignment

import org.sireum._

object SMAssignment extends App {
  override def main(args: ISZ[String]): Z = {
    /*
      For this assignment you will need:
        . 2 arduinos or firmata supported devices
        . 1 breadboard
        . 1 stepper motor
        . 1 button
        . 1 led

      As a fun little exercise modify SimpleWalkthrough.SimpleExample

      1) Make it so when the button is pressed the stepper motor does one full rotation and once done
         the LED will light up until the button is pressed again

         a) Add new pins for the two new devices
         b) Modify the pinmap to account for the new devices
         c) add the pins to the list of pins that are passed as arguments to LPConn.init
         d) Define the proxy led and button
         e) modify the behavior or the states machine

      2) Make it so that the LED and Button are controlled by one arduino and the stepper motor is controlled by the
         other.

         a) Create a second pin map that only contains the pins being used for the LED and button
            and remove those pins from the original pinMap.
         b) Create a second device set that uses the new pinMap
         c) pass the new device set to the list of device sets that are passed into LPConn.init

    */

    return 0
  }
}
