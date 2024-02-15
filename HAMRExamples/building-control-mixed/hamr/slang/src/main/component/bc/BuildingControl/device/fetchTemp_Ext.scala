package bc.BuildingControl.device

import org.sireum._

import java.util.concurrent.atomic.AtomicLong

object fetchTemp_Ext {

  private val state: AtomicLong = new AtomicLong()
  def getState(): Z = Z(state.get())

  {}

}
