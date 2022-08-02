package simulations

import io.gatling.core.scenario.Simulation;
import io.gatling.core.Predef._;
import io.gatling.http.Predef._;

class CascadingApiRequestsSimulation extends Simulation {

  val httpConfig = http.baseUrl("https://reqres.in")
    .header("content-type","application/json");

  val scn = scenario("Test Cascading requests")
    .exec(http("POST USER")
      .post("/api/users")
      .body(RawFileBody("./src/test/resources/payload/create-user.json")).asJson
      .check(status is 201)
    )
    .exec(http("GET USERS")
      .get("/api/users?page=2")
      .check(status is 200)
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)




}
