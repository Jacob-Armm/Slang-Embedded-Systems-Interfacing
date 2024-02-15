package bc.BuildingControl.device

import org.sireum._
import org.sireum.String.escape
import platform.impl.PlatformImpl
import platform.impl.builtin._

object implGetter_Ext {
  def getImpl(imp: String, pinMap: Map[String, Z]): PlatformImpl = {

    return imp.value match {
      case "Firmata" => FirmataImpl(pinMap)
    }
  }
}
