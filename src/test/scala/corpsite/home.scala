package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class CorpSiteSimulation extends Simulation {

  //val testUrl = "https://www-staging.take2games.com"
  val testUrl = "http://t2-corp-staging.d2dragon.com"
  //val testUrl = "https://corp-bmafbx6dz8-x.d2dragon.com"
  // val testUrl = "http://sandbox1.bmafbx6dz8.us-west-2.elasticbeanstalk.com"

  val httpProtocol = http
    .baseUrl(testUrl) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("t2gp corp site") // A scenario is a chain of requests and pauses
    .during(60 minutes) {
      exec(http("home").get("/")).pause(5).exec(http("games").get("/games")).pause(5);
    };

  // setUp(scn.inject(rampUsers(2000) during (60 seconds))
  //   .throttle(reachRps(400) in (180 seconds), holdFor(5 minutes)).protocols(httpProtocol))
    
  setUp(scn.inject(rampUsers(4000) during (60 seconds))
    .throttle(reachRps(1000) in (1800 seconds), holdFor(5 minutes)).protocols(httpProtocol))
  //setUp(scn.inject(atOnceUsers(200)).maxDuration(5 minutes).protocols(httpProtocol))
}
