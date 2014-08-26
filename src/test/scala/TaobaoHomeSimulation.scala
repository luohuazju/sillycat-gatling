import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TaobaoHomeSimulation extends Simulation {
  // your code starts here
  val scn = scenario("My First Scenario")
    .exec(http("My Page")
    .get("http://www.taobao.com/index_global.php"))

  setUp(scn.inject(atOnceUsers(1)))
}
