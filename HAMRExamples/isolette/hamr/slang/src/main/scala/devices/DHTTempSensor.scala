// #Sireum
package devices

import org.sireum._
import org.sireum.U8._
import org.sireum.U32._
import utils.PinModeUtil.PinMode
import utils.Wait
import utils.Time

@record class DHTTempSensor(pin: Pin, startTimeMS: Z) {
  def read(): (Z, Z) = {
    // BUFFER TO RECEIVE
    var bits: MSZ[U8] = MSZ.create(5, u8"0")
    var cnt: U8 = u8"7"
    var idx: U8 = u8"0"

    // EMPTY BUFFER
    for (i <- 0 until 5) {
      bits(i) = u8"0"
    }

    // REQUEST SAMPLE
    pin.PinModeSet(PinMode.OUTPUT)
    pin.write(0);
    Wait.waitInMS(18);
    pin.write(1);
    Wait.waitInMicroS(40);
    pin.PinModeSet(PinMode.INPUT);

    // ACKNOWLEDGE or TIMEOUT
    var loopCnt: U32 = u32"10000"
    while (pin.read == 0) {
      if (loopCnt == u32"0") {
        assert(F, "DHTLIB_ERROR_TIMEOUT")
      }
      loopCnt = loopCnt - u32"1"
    }

    loopCnt = u32"10000"
    while (pin.read == 1) {
      if (loopCnt == u32"0") {
        assert(F, "DHTLIB_ERROR_TIMEOUT")
      }
      loopCnt = loopCnt - u32"1"
    }


    // READ OUTPUT - 40 BITS => 5 BYTES or TIMEOUT
    for (i <- 0 until 40) {
      loopCnt = u32"100000000";
      while (pin.read == 0) {
        if (loopCnt == u32"0") {
          print()
          assert(F, "DHTLIB_ERROR_TIMEOUT")
        }
        loopCnt = loopCnt - u32"1"
      }

      val t: Z = Time.currentMicros - startTimeMS

      loopCnt = u32"10000";
      while (pin.read == 1) {
        if (loopCnt == u32"0") {
          assert(F, "DHTLIB_ERROR_TIMEOUT")
        }
        loopCnt = loopCnt - u32"1"
      }


      if (((Time.currentMicros - startTimeMS) - t) > 40) {
        bits(idx.toZ) = bits(idx.toZ) | (u8"1" << cnt)
      }

      if (cnt == u8"0") {
        cnt = u8"7"
        idx = idx + u8"1"
      } else {
        cnt = cnt - u8"1"
      }
    }

    // WRITE TO RIGHT VARS
    // as bits[1] and bits[3] are allways zero they are omitted in formulas.
    val humidity = bits(0);
    val temperature = bits(2);


    val sum: U8 = bits(0) + bits(2);

    assert(bits(4) != sum, "DHTLIB_ERROR_CHECKSUM")
    return (humidity.toZ, temperature.toZ)
  }
}
