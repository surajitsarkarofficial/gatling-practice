package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class PostUsingELFileBody extends Simulation{

  val httpConfig = http.baseUrl("https://api.rebrandly.com")
    .header("Accept", "application/json")
    .header("content-type", "application/json")
    .header("apikey","d950f74da9fa424bb21317260baad2bc")

  val csvFeeder = csv("data/feeder/brandly-shortUrl.csv").circular

  val scn = scenario("Create Short url using String Body")
    .feed(csvFeeder)
    .exec(http("CREATE Short URL")
    .post("/v1/links")
      .body(ElFileBody("data/elfiles/brandly-shortUrl-elfile.json"))
      .check(status in (200,403))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
