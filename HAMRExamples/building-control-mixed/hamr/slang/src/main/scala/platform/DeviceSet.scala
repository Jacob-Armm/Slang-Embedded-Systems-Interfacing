// #Sireum
package platform

import org.sireum._
import platform.impl._

@datatype class DeviceSet(id: String, platform: PlatformImpl, port: Option[String])
