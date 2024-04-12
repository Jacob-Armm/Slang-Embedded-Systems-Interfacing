// #Sireum

package isolette.Regulate

import org.sireum._
import isolette.RandomLib

// Do not edit this file as it will be overwritten if HAMR codegen is rerun

// Profile for initialise entrypoint
@record class Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_Profile (
  val name: String,
  val numTests: Z //number of tests to generate
)

// Profile with generators for incoming ports
@record class Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_Profile_P(
  val name: String,
  val numTests: Z, // number of tests to generate
  var numTestVectorGenRetries: Z, // number of test vector generation retries
  var api_current_tempWstatus: RandomLib,
  var api_lower_desired_temp: RandomLib,
  var api_regulator_mode: RandomLib,
  var api_upper_desired_temp: RandomLib)

// Profile with generators for state variables and incoming ports
@record class Manage_Heat_Source_impl_thermostat_regulate_temperature_manage_heat_source_Profile_PS(
  val name: String,
  val numTests: Z, // number of tests to generate
  var numTestVectorGenRetries: Z, // number of test vector generation retries
  var In_lastCmd: RandomLib,
  var api_current_tempWstatus: RandomLib,
  var api_lower_desired_temp: RandomLib,
  var api_regulator_mode: RandomLib,
  var api_upper_desired_temp: RandomLib)
