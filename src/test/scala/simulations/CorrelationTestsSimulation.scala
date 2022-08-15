package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CorrelationTestsSimulation extends Simulation {

  val httpConfig = http.baseUrl("https://gorest.co.in")
    .header("Authorization","Bearer 1a81e40e93047d6227f5c961aa485aad07d06ddbf523ddddf73242fbc195da63")
    .header("content-type","application/json")
    .header("accept","application/json")

  val scn = scenario("Create User")
    .exec(http("POST Request to Create user")
      .post("/public/v2/users")
      .body(RawFileBody("payload/gorest-create-user.json"))
      .check(status is 201)
      .check(jsonPath("$.id").saveAs("userId"))
    )
    .exec(http("GET Request to fetch the user details")
      .get("/public/v2/users/${userId}")
      .check(status is 200)
      .check(jsonPath("$.name") is "Surajit Sarkar")
      .check(jsonPath("$.email") is "suro-0001@email.com")
      .check(jsonPath("$.status") is "active")
    )
    .exec(http("PUT Request to update the user details")
      .put("/public/v2/users/${userId}")
      .body(RawFileBody("payload/gorest-update-user.json"))
      .check(status in (200 to 204))
      .check(jsonPath("$.name") is "Suro Sarkar")
      .check(jsonPath("$.email") is "suro-update0001@email.com")
      .check(jsonPath("$.status") is "active")
    )
    .exec(http("PATCH Request to update the user details")
      .patch("/public/v2/users/${userId}")
      .body(RawFileBody("payload/gorest-patch-user.json"))
      .check(status in (200 to 204))
      .check(jsonPath("$.name") is "Suro Sarkar")
      .check(jsonPath("$.email") is "suro-update0001@email.com")
      .check(jsonPath("$.status") is "inactive")
    )
    .exec(http("DELETE Request to delete the user details")
      .delete("/public/v2/users/${userId}")
      .check(status in (200 to 204))
    )

    .exec(http("GET Request to verify if user is deleted")
      .get("/public/v2/users/${userId}")
      .check(status is 404)
      .check(jsonPath("$.message") is "Resource not found")
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)




}
