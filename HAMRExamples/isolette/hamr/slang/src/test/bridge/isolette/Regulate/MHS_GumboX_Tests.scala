/*
package isolette.Regulate

import org.sireum._
import isolette.{Container, RandomLib}
import isolette.GumboXUtil.GumboXResult
import isolette.Regulate.GumboXUtils.freshRandomLib
import isolette.util.{MutableBase, UnitTestConfigurationBatch}

class MHS_GumboX_Tests extends
  Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_GumboX_TestHarness_ScalaTest {

  val verbose: B = F
  val failOnUnsatPreconditions: B = F

  def custProfile(lb: F32, ub: F32): Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_Profile_P_Trait = {
    return new Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_Profile_P_Trait with MutableBase {
      override def next: Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_PreState_Container_P = {
        val low = api_lower_desired_temp.set_Config_F32(
          api_lower_desired_temp.get_Config_F32(low = Some(lb), high = Some(ub - 0.01f)))
          .nextIsolette_Data_ModelTemp_impl()
        val high = api_upper_desired_temp.set_Config_F32(
            api_upper_desired_temp.get_Config_F32(low = Some(low.value + 0.01f), high = Some(ub)))
          .nextIsolette_Data_ModelTemp_impl()
        return (Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_PreState_Container_P(
          api_lower_desired_temp = low,
          api_upper_desired_temp = high,
          api_current_tempWstatus = api_current_tempWstatus.nextIsolette_Data_ModelTempWstatus_impl(),
          api_regulator_mode = api_regulator_mode.nextIsolette_Data_ModelRegulator_ModeType()))
      }
      override def api_lower_desired_temp: RandomLib = freshRandomLib
      override def api_upper_desired_temp: RandomLib = freshRandomLib
      override def api_current_tempWstatus: RandomLib = freshRandomLib
      override def api_regulator_mode: RandomLib = freshRandomLib
    }
  }

  def configs: MSZ[UnitTestConfigurationBatch] = {
    val leftPartitionConfig = GumboXUtils.defaultComputeConfig(verbose = verbose, failOnUnsatPreconditions = failOnUnsatPreconditions,
      name = "Left Partition Config",
      description = "Only generate low/high temperatures between 70.0 and 70.5",
      profile = custProfile(70.0f, 70.5f))

    val rightPartitionConfig = GumboXUtils.defaultComputeConfig(verbose = verbose, failOnUnsatPreconditions = failOnUnsatPreconditions,
      name = "Right Partition Config",
      description = "Only generate low/high temperatures between 80.0 and 80.5",
      profile = custProfile(80.0f, 80.5f))

    return MSZ(
      GumboXUtils.defaultInitConfig(verbose = verbose, failOnUnsatPreconditions = failOnUnsatPreconditions),
      GumboXUtils.defaultComputeConfig(verbose = verbose, failOnUnsatPreconditions = failOnUnsatPreconditions),
      leftPartitionConfig, rightPartitionConfig
    )
  }

  val p = Os.path(implicitly[sourcecode.File].value)
  val out = p.up / s".${p.name}.json"
  out.writeOver(configsToJson)

  def configsToJson: String = {
    return st"[ ${(for (c <- configs) yield s"\"${c.name}|${c.description}\"", ", ")} ]".render
  }

  for (c <- configs) {
    def next: Option[Container] = {
      try {
        return Some(c.profile.next)
      } catch {
        case e: AssertionError => // SlangCheck was unable to satisfy a datatype's filter
          return None()
      }
    }

    for (i <- 0 until c.numTests) {
      val testName = s"\"${c.name}\"_$i"

      this.registerTest(testName) {
        var retry: B = T

        var j: Z = 0
        while (j < c.numTestVectorGenRetries && retry) {
          next match {
            case Some(o) =>

              if (verbose && j > 1) {
                println(s"Retry $j:")
              }

              val results = c.test(o)

              if (verbose) {
                c.genReplay(o, results) match {
                  case Some(s) =>
                    val escapedTestName = s"\\\"${c.name}\\\"_$i"
                    println(
                      st"""Replay Unit Test:
                          |  test("Replay: $escapedTestName") {
                          |    $s
                          |  }""".render)
                  case _ =>
                }
              }

              results match {
                case GumboXResult.Pre_Condition_Unsat =>
                case GumboXResult.Post_Condition_Fail =>
                  fail("Post condition did not hold")
                  retry = F
                case GumboXResult.Post_Condition_Pass =>
                  if (verbose) {
                    println("Success!")
                  }
                  retry = F
              }
            case _ =>

          }
          j = j + 1
        }

        if (retry) {
          if (c.failOnUnsatPreconditions) {
            fail("Unable to satisfy precondition")
          } else if (verbose) {
            cprintln(T, "Unable to satisfy precondition")
          }
        }
      }
    }
  }
}
*/
