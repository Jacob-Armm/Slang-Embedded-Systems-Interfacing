// #Sireum

package bc.runtimemonitor

import org.sireum._
import bc._

// Do not edit this file as it will be overwritten if HAMR codegen is rerun

object GumboXDispatcher {
  def checkContract(observationKind: ObservationKind.Type, preContainer: Option[art.DataContent], postContainer: Option[art.DataContent]): B = {
    observationKind match {
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempSensor_postInit =>
        // checking the post-state values of tempSensor's initialise entrypoint is not required
        return T
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempSensor_preCompute =>
        // checking the pre-state values of tempSensor's compute entrypoint is not required
        return T
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempSensor_postCompute =>
        // checking the post-state values of tempSensor's compute entrypoint is not required
        return T

      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempControl_postInit =>
        // checking the post-state values of tempControl's initialise entrypoint is not required
        return T
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempControl_preCompute =>
        // checking the pre-state values of tempControl's compute entrypoint is not required
        return T
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempControl_postCompute =>
        // checking the post-state values of tempControl's compute entrypoint is not required
        return T

      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_fan_postInit =>
        // checking the post-state values of fan's initialise entrypoint is not required
        return T
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_fan_preCompute =>
        // checking the pre-state values of fan's compute entrypoint is not required
        return T
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_fan_postCompute =>
        // checking the post-state values of fan's compute entrypoint is not required
        return T

      case _ => halt("Infeasible")
    }
  }

  def genTestSuite(testCases: ISZ[(Z, ISZ[ST])]): Unit = {
    val tq = "\"\"\""

    val testRoot = Os.path(".") / "src" / "test" / "bridge"

    val TempSensor_i_tcp_tempSensor_id = Arch.BuildingControlDemo_i_Instance_tcp_tempSensor.id
    val TempControl_i_tcp_tempControl_id = Arch.BuildingControlDemo_i_Instance_tcp_tempControl.id
    val Fan_i_tcp_fan_id = Arch.BuildingControlDemo_i_Instance_tcp_fan.id

    def genUniqueSuiteName(path: Os.Path, prefix: String): String = {
      var i = 0
      while(true) {
        val cand = path / s"${prefix}_${i}.scala"
        if (!cand.exists) {
          return s"${prefix}_${i}"
        }
        i = i + 1
      }
      halt("Infeasible")
    }

    for (p <- testCases) {
      art.Art.BridgeId.fromZ(p._1) match {
        case TempSensor_i_tcp_tempSensor_id =>
          val prefix = "TempSensor_i_tcp_tempSensor_RM_TestSuite"
          val path = testRoot /+ ISZ("bc","BuildingControl")
          val suiteName = genUniqueSuiteName(path, prefix)

          val testSuite =
            st"""package bc.BuildingControl
                |
                |import org.sireum._
                |import bc.BuildingControl._
                |
                |class ${suiteName} extends TempSensor_i_tcp_tempSensor_GumboX_TestHarness_ScalaTest {
                |  val verbose: B = true
                |
                |  var i = 0 // ensures generated test case names are unique
                |  def incrementI: Int = {
                |    i += 1
                |    return i
                |  }
                |
                |  ${(p._2, "\nincrementI\n\n")}
                |}"""
          val filename = path / s"${suiteName}.scala"
          filename.writeOver(testSuite.render)
          println(s"Wrote: ${filename.toUri}")
        case TempControl_i_tcp_tempControl_id =>
          val prefix = "TempControl_i_tcp_tempControl_RM_TestSuite"
          val path = testRoot /+ ISZ("bc","BuildingControl")
          val suiteName = genUniqueSuiteName(path, prefix)

          val testSuite =
            st"""package bc.BuildingControl
                |
                |import org.sireum._
                |import bc.BuildingControl._
                |
                |class ${suiteName} extends TempControl_i_tcp_tempControl_ScalaTest {
                |  val verbose: B = true
                |
                |  var i = 0 // ensures generated test case names are unique
                |  def incrementI: Int = {
                |    i += 1
                |    return i
                |  }
                |
                |  ${(p._2, "\nincrementI\n\n")}
                |}"""
          val filename = path / s"${suiteName}.scala"
          filename.writeOver(testSuite.render)
          println(s"Wrote: ${filename.toUri}")
        case Fan_i_tcp_fan_id =>
          val prefix = "Fan_i_tcp_fan_RM_TestSuite"
          val path = testRoot /+ ISZ("bc","BuildingControl")
          val suiteName = genUniqueSuiteName(path, prefix)

          val testSuite =
            st"""package bc.BuildingControl
                |
                |import org.sireum._
                |import bc.BuildingControl._
                |
                |class ${suiteName} extends Fan_i_tcp_fan_ScalaTest {
                |  val verbose: B = true
                |
                |  var i = 0 // ensures generated test case names are unique
                |  def incrementI: Int = {
                |    i += 1
                |    return i
                |  }
                |
                |  ${(p._2, "\nincrementI\n\n")}
                |}"""
          val filename = path / s"${suiteName}.scala"
          filename.writeOver(testSuite.render)
          println(s"Wrote: ${filename.toUri}")
        case x => halt(s"Infeasible bridge id: $x")
      }
    }
  }

  def genTestCase(observationKind: ObservationKind.Type, preContainer: Option[String], postContainer: Option[String], testNameSuffix: Option[String]): ST = {
    val tq = "\"\"\""
    val suffix: String =
      if (testNameSuffix.nonEmpty) testNameSuffix.get
      else ""

    observationKind match {



      case _ => return st"// TODO ${observationKind}"
    }
  }

  def getUpdates(bridge_id: art.Art.BridgeId, observationKind: ObservationKind.Type, container: art.DataContent): Map[String, String] = {
    observationKind match {
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempSensor_postInit =>
        var updates: Map[String, String] = Map.empty
        val postContainer = container.asInstanceOf[bc.BuildingControl.TempSensor_i_tcp_tempSensor_PostState_Container_PS]
        updates = updates + s"${bridge_id}_Out_currentTemp" ~> postContainer.api_currentTemp.string
        if (postContainer.api_tempChanged.nonEmpty) {
          updates = updates + s"${bridge_id}_Out_tempChanged" ~> postContainer.api_tempChanged.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempControl_postInit =>
        var updates: Map[String, String] = Map.empty
        val postContainer = container.asInstanceOf[bc.BuildingControl.TempControl_i_tcp_tempControl_PostState_Container_PS]
        if (postContainer.api_fanCmd.nonEmpty) {
          updates = updates + s"${bridge_id}_Out_fanCmd" ~> postContainer.api_fanCmd.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_fan_postInit =>
        var updates: Map[String, String] = Map.empty
        val postContainer = container.asInstanceOf[bc.BuildingControl.Fan_i_tcp_fan_PostState_Container_PS]
        if (postContainer.api_fanAck.nonEmpty) {
          updates = updates + s"${bridge_id}_Out_fanAck" ~> postContainer.api_fanAck.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempSensor_preCompute =>
        var updates: Map[String, String] = Map.empty
        val preContainer = container.asInstanceOf[bc.BuildingControl.TempSensor_i_tcp_tempSensor_PreState_Container_PS]
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempControl_preCompute =>
        var updates: Map[String, String] = Map.empty
        val preContainer = container.asInstanceOf[bc.BuildingControl.TempControl_i_tcp_tempControl_PreState_Container_PS]
        updates = updates + s"${bridge_id}_In_currentTemp" ~> preContainer.api_currentTemp.string
        if (preContainer.api_fanAck.nonEmpty) {
          updates = updates + s"${bridge_id}_In_fanAck" ~> preContainer.api_fanAck.get.string
        }
        if (preContainer.api_setPoint.nonEmpty) {
          updates = updates + s"${bridge_id}_In_setPoint" ~> preContainer.api_setPoint.get.string
        }
        if (preContainer.api_tempChanged.nonEmpty) {
          updates = updates + s"${bridge_id}_In_tempChanged" ~> preContainer.api_tempChanged.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_fan_preCompute =>
        var updates: Map[String, String] = Map.empty
        val preContainer = container.asInstanceOf[bc.BuildingControl.Fan_i_tcp_fan_PreState_Container_PS]
        if (preContainer.api_fanCmd.nonEmpty) {
          updates = updates + s"${bridge_id}_In_fanCmd" ~> preContainer.api_fanCmd.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempSensor_postCompute =>
        var updates: Map[String, String] = Map.empty
        val postContainer = container.asInstanceOf[bc.BuildingControl.TempSensor_i_tcp_tempSensor_PostState_Container_PS]
        updates = updates + s"${bridge_id}_Out_currentTemp" ~> postContainer.api_currentTemp.string
        if (postContainer.api_tempChanged.nonEmpty) {
          updates = updates + s"${bridge_id}_Out_tempChanged" ~> postContainer.api_tempChanged.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_tempControl_postCompute =>
        var updates: Map[String, String] = Map.empty
        val postContainer = container.asInstanceOf[bc.BuildingControl.TempControl_i_tcp_tempControl_PostState_Container_PS]
        if (postContainer.api_fanCmd.nonEmpty) {
          updates = updates + s"${bridge_id}_Out_fanCmd" ~> postContainer.api_fanCmd.get.string
        }
        return updates
      case bc.runtimemonitor.ObservationKind.BuildingControlDemo_i_Instance_tcp_fan_postCompute =>
        var updates: Map[String, String] = Map.empty
        val postContainer = container.asInstanceOf[bc.BuildingControl.Fan_i_tcp_fan_PostState_Container_PS]
        if (postContainer.api_fanAck.nonEmpty) {
          updates = updates + s"${bridge_id}_Out_fanAck" ~> postContainer.api_fanAck.get.string
        }
        return updates
      case _ => return Map.empty
    }
  }
}