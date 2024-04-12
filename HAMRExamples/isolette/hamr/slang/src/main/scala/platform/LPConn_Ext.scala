package platform

import platform.impl.PlatformImpl
import devices.Pin
import org.sireum._
import utils.PinModeUtil.PinMode

object LPConn_Ext {

  private var universalPinMap: Map[String, (Z, String)] = Map.empty[String, (Z, String)]

  private var deviceSets: ISZ[DeviceSet] = _

  def init(deviceSets: ISZ[DeviceSet], logicalPins: ISZ[Pin]): Unit = {
    this.deviceSets = deviceSets

    for(deviceSet <- deviceSets) {
      for(k <- deviceSet.platform.retrievePinMap.keys) {
        val x = (k, (deviceSet.platform.retrievePinMap.get(k).get, deviceSet.id))
        universalPinMap = universalPinMap ++ ISZ(x)
      }
    }

    for(deviceSet <- deviceSets) {
      deviceSet.platform.init(deviceSet.port)
    }

    /*
    val physicalPins = architectureImpl.retievePinList

    for(pin <- logicalPins){
      assert(ops.ISZOps(pinMap.keys).contains(pin.pinAlias), s"PinAlias ${pin.pinAlias} does not exist in pin map")
    }

    for(pin <- pinMap.keys){
      assert(ops.ISZOps(logicalPins).exists(p => p.pinAlias == pin), s"${pin} is not a logically defined pin")
    }

    for (pinNum <- pinMap.values) {
      assert(ops.ISZOps(physicalPins.keys).contains(pinNum), s"Pin $pinNum does not exist for this implementation")
    }

    for(pin <- logicalPins) {
      assert(ops.ISZOps(physicalPins.get(pinMap.get(pin.pinAlias).get).get).contains(pin.mode), s"\nPinMode Map Mismatch: pinMode ${pin.mode} is an invalid pinMode for ${pinMap.get(pin.pinAlias).get} which can only accept ${physicalPins.get(pinMap.get(pin.pinAlias).get).get}")
    }
    */


  }

  def ready: B = {
    var r = T
    for(deviceSet <- deviceSets) {
      if(!deviceSet.platform.ready) {
        r = F
      }
    }
    return r
  }

  def read(pin: String, mode: PinMode.Type): Z = {
    val p = universalPinMap.get(pin).get
    var platformImpl: PlatformImpl = null
    for(deviceSet <- deviceSets) {
      if(deviceSet.id == p._2) {platformImpl = deviceSet.platform}
    }

    platformImpl.read(pin, mode)
  }

  def write(pin: String, mode: PinMode.Type, value: Z): Unit = {
    val p = universalPinMap.get(pin).get
    var platformImpl: PlatformImpl = null
    for(deviceSet <- deviceSets) {
      if(deviceSet.id == p._2) {platformImpl = deviceSet.platform}
    }

    platformImpl.write(pin, mode, value)
  }

}
