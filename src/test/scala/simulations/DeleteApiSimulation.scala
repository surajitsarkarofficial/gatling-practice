package simulations

import io.gatling.core.scenario.Simulation;
import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class DeleteApiSimulation extends Simulation {

  val httpConfig = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json");

  val scn = scenario("Test DELETE Api")

    .exec(
      http("DELETE REQUEST")
        .put("/api/users/2")
        .check(status.in(200 to 204))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
