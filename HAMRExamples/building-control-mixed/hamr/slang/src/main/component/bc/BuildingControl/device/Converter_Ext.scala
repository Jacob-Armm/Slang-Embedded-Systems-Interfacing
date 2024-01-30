package bc.BuildingControl.device
import org.sireum._
object Converter_Ext {
  def FtoZ(value: F32): Z = Z(value.value.toInt)
  def ZtoF(value: Z): F32 = F32(value.toInt * 1.0f)
}
