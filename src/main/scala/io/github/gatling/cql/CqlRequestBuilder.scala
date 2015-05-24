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
package io.github.gatling.cql

import com.datastax.driver.core.PreparedStatement
import com.datastax.driver.core.SimpleStatement
import com.datastax.driver.core.ConsistencyLevel

import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression


case class CqlRequestBuilderBase(tag: String) {
  def execute(statement: Expression[String]) = new CqlRequestBuilder(CqlAttributes(tag, SimpleCqlStatement(statement)))
  def execute(prepared: PreparedStatement) = new CqlRequestParamsBuilder(tag, prepared)
}  

case class CqlRequestParamsBuilder(tag: String, prepared: PreparedStatement) {
  def withParams(params: Expression[AnyRef]*) = new CqlRequestBuilder(CqlAttributes(tag, BoundCqlStatement(prepared, params:_*)))
}

case class CqlRequestBuilder(attr: CqlAttributes) {
    def consistencyLevel(level: ConsistencyLevel) = CqlRequestBuilder(attr.copy(cl= level))
    def build(): ActionBuilder = new CqlRequestActionBuilder(attr)
}