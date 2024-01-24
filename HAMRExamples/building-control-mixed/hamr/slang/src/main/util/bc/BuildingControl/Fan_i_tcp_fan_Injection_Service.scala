// #Sireum
package bc.BuildingControl

import org.sireum._

// Do not edit this file as it will be overwritten if HAMR codegen is rerun

@msig trait Fan_i_tcp_fan_Injection_Provider {
  def pre_receiveInput(): Unit
}

object Fan_i_tcp_fan_Injection_Service {

  var providers: MSZ[Fan_i_tcp_fan_Injection_Provider] = MSZ()

  def register(provider: Fan_i_tcp_fan_Injection_Provider): Unit = {
    providers = providers :+ provider
  }

  def pre_receiveInput(): Unit = {
    for (provider <- providers) {
      provider.pre_receiveInput()
    }
  }
}