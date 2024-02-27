package bc.BuildingControl.device

import org.sireum._
import devices.{LED, Potentiometer}
import org.sireum.Z

import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}

object fanSet_Ext {
  var device: LED = _

  private val state: AtomicInteger = new AtomicInteger()

  var thread: Thread = new Thread {
    override def run(): Unit = {
      while (true) {
        if(state.get() == 0) device.off()
        else device.on()
      }
    }
  }

  def init(device: LED, initVal: Z): Unit = {
    this.device = device
    state.set(initVal.toInt)
    thread.start()
  }

  def setState(v: Z): Unit = state.set(v.toInt)
}
