/*
 * #%L
 * GatlingCql
 * %%
 * Copyright (C) 2014 Mikhail Stepura
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.sillycat.gatling.cassandra

import java.nio.ByteBuffer

import scala.concurrent.duration.DurationInt

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ConsistencyLevel

import io.gatling.core.Predef._
import io.github.gatling.cql.Predef._;

import io.gatling.core.scenario.Simulation

class CassandraSimulation extends Simulation {
  val keyspace = "attributes"
  val table_name = "attributes"
  val session = Cluster.builder().addContactPoints("ubuntu-dev1","ubuntu-dev2")
                  .build().connect(s"$keyspace") //Your C* session
  val cqlConfig = cql.session(session) //Initialize Gatling DSL with your session

  //Setup
  //session.execute(s"""CREATE KEYSPACE IF NOT EXISTS $keyspace
  //                    WITH replication = { 'class' : 'SimpleStrategy',
  //                                        'replication_factor': '2'}""")

//  session.execute(s"""CREATE TABLE IF NOT EXISTS $table_name (
//          brandcode ascii,
//          deviceid ascii,
//          unixtime bigint,
//          attrs blob,
//          PRIMARY KEY ((brandcode, deviceid), unixtime)
//        ) WITH COMPACT STORAGE AND
//          CLUSTERING ORDER BY (unixtime DESC) AND
//          bloom_filter_fp_chance=0.100000 AND
//          caching='KEYS_ONLY' AND
//          comment='' AND
//          dclocal_read_repair_chance=0.000000 AND
//          gc_grace_seconds=864000 AND
//          read_repair_chance=0.100000 AND
//          replicate_on_write='true' AND
//          populate_io_cache_on_flush='false' AND
//          compaction={'sstable_size_in_mb': '10', 'class': 'LeveledCompactionStrategy'} AND
//          compression={};
//      """)
  //It's generally not advisable to use secondary indexes in you schema
  //session.execute(f"""CREATE INDEX IF NOT EXISTS $table_name%s_num_idx
  //                    ON $table_name%s (num)""")

  //Prepare your statement, we want to be effective, right?
  val prepared = session.prepare(s"""INSERT INTO $table_name 
                                      (brandcode, deviceid, unixtime, attrs)
                                      VALUES 
                                      (?, ?, ?, ?)""")
  val preparedQuery = session.prepare(
    s"""select
          brandcode,
          deviceid,
          attrs
        from
          $table_name
        where
          brandcode = ? and
          deviceid = ?
     """)

  val random = new util.Random
  val feeder = Iterator.continually( 
      // this feader will "feed" random data into our Sessions
      Map(
          "randomString" -> ByteBuffer.wrap(random.nextString(20).getBytes),
          "randomNum" -> random.nextInt(1000000),
          "now" -> System.currentTimeMillis/1000L,
          "randomLmit" -> Math.max(random.nextInt(200),1)
          ))

  val scn = scenario("Huge Inserting").repeat(1) { //Name your scenario
    feed(feeder)
    //.exec(cql("simple SELECT")
         // 'execute' can accept a string 
         // and understands Gatling expression language (EL), i.e. ${randomNum}
    //    .execute("SELECT * FROM test_table WHERE num = ${randomNum}"))
    .exec(cql("prepared INSERT")
         // alternatively 'execute' accepts a prepared statement
        .execute(prepared)
         // you need to provide parameters for that (EL is supported as well)
        .withParams("spark", "ios1-${randomNum}", "${now}" , "${randomString}")
        // and set a ConsistencyLevel optionally
        .consistencyLevel(ConsistencyLevel.ANY))
    .exec(cql("prepared SELECT")
        .execute(preparedQuery)
        .withParams("spark","ios1-${randomNum}"))
    .exec(cql("prepared LOAD")
       .execute("SELECT * FROM attributes limit ${randomLmit}"))
  }

  setUp(scn.inject(rampUsersPerSec(10) to 50 during (60 minutes)))
    .protocols(cqlConfig)
}
