// #Sireum

package bc.BuildingControl

import org.sireum._
import bc.BuildingControl.device.DeviceBridge
import bc._
import devices._
import utils.DeviceBehavior


// This file will not be overwritten so is safe to edit
object Fan_i_tcp_fan {

  def initialise(api: Fan_i_Initialization_Api): Unit = {

  }

  def handle_fanCmd(api: Fan_i_Operational_Api, value: BuildingControl.FanCmd.Type): Unit = {
    api.logInfo(s"received fanCmd $value")

    DeviceBridge.Fan.setState(value)
    val ack = DeviceBridge.Fan.state

    api.put_fanAck(ack)

    api.logInfo(s"Actuation result: ${ack}")
  }

  def activate(api: Fan_i_Operational_Api): Unit = {}

  def deactivate(api: Fan_i_Operational_Api): Unit = {}

  def finalise(api: Fan_i_Operational_Api): Unit = {}

  def recover(api: Fan_i_Operational_Api): Unit = {}
}