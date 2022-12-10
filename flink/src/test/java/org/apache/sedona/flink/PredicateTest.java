/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sedona.flink;

import org.apache.flink.table.api.Table;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PredicateTest extends TestBase{
    @BeforeClass
    public static void onceExecutedBeforeAll() {
        initialize();
    }

    @Test
    public void testIntersects() {
        Table pointTable = createPointTable(testDataSize);
        String polygon = createPolygonWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_Intersects(ST_GeomFromWkt('" + polygon + "'), geom_point)";
        Table result = pointTable.filter(expr);
        assertEquals(1, count(result));
    }

    @Test
    public void testDisjoint() {
        Table pointTable = createPointTable(testDataSize);
        String polygon = createPolygonWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_Disjoint(ST_GeomFromWkt('" + polygon + "'), geom_point)";
        Table result = pointTable.filter(expr);
        assertEquals(999, count(result));
    }

    @Test
    public void testContains() {
        Table pointTable = createPointTable(testDataSize);
        String polygon = createPolygonWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_Contains(ST_GeomFromWkt('" + polygon + "'), geom_point)";
        Table result = pointTable.filter(expr);
        assertEquals(1, count(result));
    }

    @Test
    public void testWithin() {
        Table pointTable = createPointTable(testDataSize);
        String polygon = createPolygonWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_Within(geom_point, ST_GeomFromWkt('" + polygon + "'))";
        Table result = pointTable.filter(expr);
        assertEquals(1, count(result));
    }

    @Test
    public void testCovers() {
        Table pointTable = createPointTable(testDataSize);
        String polygon = createPolygonWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_Covers(ST_GeomFromWkt('" + polygon + "'), geom_point)";
        Table result = pointTable.filter(expr);
        assertEquals(1, count(result));
    }

    @Test
    public void testCoveredBy() {
        Table pointTable = createPointTable(testDataSize);
        String polygon = createPolygonWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_CoveredBy(geom_point, ST_GeomFromWkt('" + polygon + "'))";
        Table result = pointTable.filter(expr);
        assertEquals(1, count(result));
    }

    @Test
    public void testOrderingEquals() {
        Table lineStringTable = createLineStringTable(testDataSize);
        String lineString = createLineStringWKT(testDataSize).get(0).getField(0).toString();
        String expr = "ST_OrderingEquals(ST_GeomFromWkt('" + lineString + "'), geom_linestring)";
        Table result = lineStringTable.filter(expr);
        assertEquals(1, count(result));
    }
}
