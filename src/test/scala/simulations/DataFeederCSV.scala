package simulations


import io.gatling.core.Predef._
import io.gatling.http.Predef._

class DataFeederCSV extends Simulation {
  val httpConfig = http.baseUrl("https://reqres.in")
    .header("content-type", "application/json")
    .header("accept", "application/json")

  val csvFeeder = csv("data/feeder/reqres-get-user.csv").circular

  val scn = scenario("Test Data Feeder")
    .feed(csvFeeder)
    .exec(http("GET Request to fetch the user details")
    .get("/api/users/${userId}")
    .check(status is 200)
    .check(jsonPath("$.data.id") is (1).toString)
  )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
