// #Sireum
package devices

import org.sireum._
import utils.Wait

@record class StepperMotor(defaultRPM: Z, sPR: Z,
                            n1: Pin,
                            n2: Pin,
                            n3: Pin,
                            n4: Pin)
{
  var rpm: Z = defaultRPM
  val stepCorrection: Z = 2048/sPR

  def setRPM(v: Z): Unit = {
    rpm = v
  }

  def step(steps: Z): Unit = {
    var s: Z = steps
    var d: B = T

    if(steps < 0){
      s = -1 * s
      d = F
    }

    for(i <- 0 until s) {
      singleStep(d)
      Wait.waitInMS((60000 / (sPR * rpm)) - (2 * stepCorrection))
    }
  }

  def singleStep(direction: B): Unit = {
    var stepCount: Z = 0

    for(i <- 0 until stepCorrection) {
      stepCount match {
        case 0 =>
          n1.write(1)
          n2.write(0)
          n3.write(0)
          n4.write(0)
        case 1 =>
          n1.write(0)
          n2.write(1)
          n3.write(0)
          n4.write(0)
        case 2 =>
          n1.write(0)
          n2.write(0)
          n3.write(1)
          n4.write(0)
        case 3 =>
          n1.write(0)
          n2.write(0)
          n3.write(0)
          n4.write(1)
        case _ =>
      }

      if(direction) {
        stepCount = (stepCount + 1) % 4
      } else {
        stepCount = stepCount - 1
        if(stepCount < 0){
          stepCount = 3
        }
      }

      Wait.waitInMS(2)
    }
  }
}


