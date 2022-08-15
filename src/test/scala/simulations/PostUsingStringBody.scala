package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class PostUsingStringBody extends Simulation{

  val httpConfig = http.baseUrl("https://api.rebrandly.com")
    .header("Accept", "application/json")
    .header("content-type", "application/json")
    .header("apikey","d950f74da9fa424bb21317260baad2bc")

  val scn = scenario("Create Short url using String Body")
    .exec(http("CREATE Short URL")
    .post("/v1/links")
      .body(StringBody("""{"destination": "https://www.youtube.com/channel/UCulLklJzPQhjOBWjGcBv4EA","slashtag": "surokiduniya","title": "youtubeChannel"}"""))
      .check(status in (200,403))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
