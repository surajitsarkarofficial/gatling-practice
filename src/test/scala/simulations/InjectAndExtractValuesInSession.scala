package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._;

class InjectAndExtractValuesInSession extends Simulation{
  //http config
  val httpConf = http.baseUrl("https://reqres.in/")
    .header("Accept","application/json")
    .header("content-type","application/json")

  //scenario
  val scn = scenario("Get single user")
    .exec(http("Get User Request")
      .get("/api/users/2")
      .check(status is 200)
      .check(jsonPath("$").saveAs("Response"))//injected in session
    )
    //printing the session
    .exec(session =>{
      println("Session  is " + session)
      val response = session("Response")
      println("Response body is \n"+response.as[String])//extracted from session
      session
    })

  //setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}