package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoopTestSimulation extends Simulation{

  val httpConfig = http.baseUrl("https://gorest.co.in")
    .header("Authorization", "Bearer 1a81e40e93047d6227f5c961aa485aad07d06ddbf523ddddf73242fbc195da63")
    .header("content-type", "application/json")
    .header("accept", "application/json")

  def createUser() =
  {
    repeat(1)
    {
      exec(http("POST Request to Create user")
        .post("/public/v2/users")
        .body(RawFileBody("./src/test/resources/payload/gorest-create-user.json"))
        .check(status is 201)
        .check(jsonPath("$.id").saveAs("userId"))
      )
    }
  }
  def getUser()={
    repeat(2)
    {
      exec(http("GET Request to fetch the user details")
      .get("/public/v2/users/${userId}")
      .check(status in (200,304))
      .check(jsonPath("$.name") is "Surajit Sarkar")
      .check(jsonPath("$.email") is "suro-0001@email.com")
      .check(jsonPath("$.status") is "active")
    )
    }
  }

  def updateUser()={
    repeat(2)
    {
      exec(http("PATCH Request to update the user details")
        .patch("/public/v2/users/${userId}")
        .body(RawFileBody("./src/test/resources/payload/gorest-patch-user.json"))
        .check(status in (200 to 204))
        .check(jsonPath("$.name") is "Surajit Sarkar")
        .check(jsonPath("$.status") is "inactive")
      )
    }
  }

  def deleteUser()={
    repeat(1)
    {
      exec(http("DELETE Request to delete the user details")
        .delete("/public/v2/users/${userId}")
        .check(status in (200 to 204))
      )
    }
  }

  val scn = scenario("Test repeat api calls")
    .exec(createUser())
    .pause(2)
    .exec(getUser())
    .pause(2)
    .exec(updateUser())
    .pause(2)
    .exec(deleteUser())


  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
