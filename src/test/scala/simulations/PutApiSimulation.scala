package simulations

import io.gatling.core.scenario.Simulation;
import io.gatling.core.Predef._;
import io.gatling.http.Predef._;


class PutApiSimulation extends Simulation {


  val httpConfig = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json");

  val scn = scenario("Test PUT Api")

    .exec(
      http("PUT REQUEST")
        .put("/api/users/2")
        .body(RawFileBody("payload/update-user.json")).asJson
        .check(status.in(200 to 201))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
