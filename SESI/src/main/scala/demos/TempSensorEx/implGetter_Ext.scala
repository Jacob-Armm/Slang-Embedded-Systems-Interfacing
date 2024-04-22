package demos.TempSensorEx

import org.sireum._
import platform.impl.PlatformImpl
import platform.impl.builtin.FirmataImpl

object implGetter_Ext {
  def getImpl(pinMap: Map[String, Z]): PlatformImpl = {
    return FirmataImpl(pinMap)
  }
}
