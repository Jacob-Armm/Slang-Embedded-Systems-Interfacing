package utils

import org.sireum._
import java.util.concurrent.TimeUnit

object Time_Ext {
  def currentMicros: Z = Z(System.nanoTime() / 1000)
}
