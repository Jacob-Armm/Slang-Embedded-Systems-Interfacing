package bc.BuildingControl.device

import bc.BuildingControl.device.fetchTemp_Ext.device
import devices.Potentiometer
import org.sireum._

import java.util.concurrent.atomic.AtomicLong

object fetchTemp_Ext {

  var device: Potentiometer = _

  private val state: AtomicLong = new AtomicLong()

  var thread: Thread = new Thread {
    override def run(): Unit = {
      while (true) {
        wait(50)
        state.set(device.getPotValue.toLong)
      }
    }
  }

  def init(device: Potentiometer) = {
    this.device = device
    state.set(device.getPotValue.toLong)
    thread.start()
  }

  def getState(): Z = Z(state.get())





}
