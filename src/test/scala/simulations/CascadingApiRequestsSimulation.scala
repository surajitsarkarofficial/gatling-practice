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
      .body(RawFileBody("payload/create-user.json")).asJson
      .check(status is 201)
    )
    .pause(2)

    .exec(http("GET USERS")
      .get("/api/users?page=2")
      .check(status is 200)
    )
    .pause(2,5) //Will pause between 2 to 5 seconds

    .exec(
      http("PUT REQUEST")
        .put("/api/users/2")
        .body(RawFileBody("payload/update-user.json")).asJson
        .check(status.in(200 to 201))
    )
    .pause(2)

    .exec(
      http("PATCH REQUEST")
        .patch("/api/users/2")
        .body(RawFileBody("payload/update-user.json")).asJson
        .check(status.in(200 to 201))
    )
    .pause(2)

    .exec(
      http("DELETE REQUEST")
        .put("/api/users/2")
        .check(status.in(200 to 204))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)




}
