package simulations;

import io.gatling.core.scenario.Simulation;
import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class GetApiSimulation extends Simulation{
  //http config
  val httpConf = http.baseUrl("https://reqres.in/")
    .header("Accept","application/json")
    .header("content-type","application/json")

  //scenario
  val scn = scenario("Get single user")
    .exec(http("Get User Request")
      .get("/api/users/2")
      .check(status is 200)
    )

  //setup
  setUp(scn.inject(atOnceUsers(1000))).protocols(httpConf)
}