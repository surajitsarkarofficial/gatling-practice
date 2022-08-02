package simulations

import io.gatling.core.scenario.Simulation;
import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class PostApiSimulation extends Simulation {

  val httpConfig = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")
    .header("content-type","application/json")

  val scn = scenario("Post call example")
    .exec(http("Post user Request")
    .post("/api/users")
      .body(RawFileBody("./src/test/resources/payload/create-user.json")).asJson
      .check(status is 201)

    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)


}
