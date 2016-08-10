package com.mobiletheatertech.plot

import org.testng.Assert._
import org.testng.annotations._

/**
  * Created by DHS on 8/7/16.
  */
class LineTest
{
  val origin = new Point( 0.0, 0.0, 0.0 )

//  @Test
//  @throws[Exception]
//  def stores {
//    val instance = new Line( new Point( 1.1, 2.2, 3.3 ), new Point( 4.4, 5.5, 6.6 ) )
//
//    assertEquals( instance.x1, 1.1 )
//    assertEquals( instance.y1, 2.2 )
//    assertEquals( instance.z1, 3.3 )
//    assertEquals( instance.x2, 4.4 )
//    assertEquals( instance.y2, 5.5 )
//    assertEquals( instance.z2, 6.6 )
//  }

  @Test
  def lengthOfXLine: Unit = {
    val end = new Point( 10.0, 0.0, 0.0 )
    val line = new Line( origin, end )

    val len: Double = line.length()
    assertEquals( len, 10.0 )

    assertEquals( origin.distance( end ), 10.0 )
  }

  @Test
  def lengthOfYLine: Unit = {
    val end = new Point( 0.0, 12.0, 0.0 )
    val line = new Line( origin, end )

    val len: Double = line.length()
    assertEquals( len, 12.0 )

    assertEquals( origin.distance( end ), 12.0 )
  }

  @Test
  def lengthOfZLine: Unit = {
    val end = new Point( 0.0, 0.0, 17.0 )
    val line = new Line( origin, end )

    val len: Double = line.length()
    assertEquals( len, 17.0 )

    assertEquals( origin.distance( end ), 17.0 )
  }

  @Test
  def lengthOfXYLine: Unit = {
    val end = new Point( 10.0, 10.0, 0.0 )
    val line = new Line( origin, end )

    val len: Double = line.length()
    assertEquals( len, Math.sqrt( 200 ) )

    assertEquals( origin.distance( end ), Math.sqrt( 200 ) )
  }

  @Test
  def lengthOfYZLine: Unit = {
    val end = new Point( 0.0, 12.0, 12.0 )
    val line = new Line( origin, end )

    val len: Double = line.length()
    assertEquals( len, Math.sqrt( 288 ) )

    assertEquals( origin.distance( end ), Math.sqrt( 288 ) )
  }

  @Test
  def lengthOfXYZLine: Unit = {
    val end = new Point( 5.0, 10.0, 12.0 )
    val line = new Line( origin, end )

    val len: Double = line.length()
    assertEquals( len, Math.sqrt( 269 ) )

    assertEquals( origin.distance( end ), Math.sqrt( 269 ) )
  }

  @Test
  def distanceAlongXLine: Unit = {
    val end = new Point( 10.0, 0.0, 0.0 )
    val line = new Line( origin, end )

    val point: Point = line.pointAtDistanceFromP1( 4.5 )
    assertEquals( point, new Point( 4.5, 0.0, 0.0 ) )

    assertEquals( Point.OnALine( origin, end, 4.5 ), new Point( 4.5, 0.0, 0.0 ) )
  }

  @Test
  def distanceAlongYLine: Unit = {
    val end = new Point( 0.0, 12.0, 0.0 )
    val line = new Line( origin, end )

    val point: Point = line.pointAtDistanceFromP1( 7.5 )
    assertEquals( point, new Point( 0.0, 7.5, 0.0 ) )

    assertEquals( Point.OnALine( origin, end, 7.5 ), new Point( 0.0, 7.5, 0.0 ) )
  }

  @Test
  def distanceAlongZLine: Unit = {
    val end = new Point( 0.0, 0.0, 17.0 )
    val line = new Line( origin, end )

    val point: Point = line.pointAtDistanceFromP1( 8.3 )
    assertEquals( point, new Point( 0.0, 0.0, 8.3 ) )

    assertEquals( Point.OnALine( origin, end, 8.3 ), new Point( 0.0, 0.0, 8.3 ) )
  }

  @Test
  def distanceAlongXYLine: Unit = {
    val end = new Point( 3.0, 4.0, 0.0 )
    val line = new Line( origin, end )

    val point: Point = line.pointAtDistanceFromP1( 2.5 )
    assertEquals( point, new Point( 1.5, 2.0, 0.0 ) )

    assertEquals( Point.OnALine( origin, end, 2.5 ), new Point( 1.5, 2.0, 0.0 ) )
  }

  @Test
  def distanceAlongYZLine: Unit = {
    val end = new Point( 0.0, 30.0, 40.0 )
    val line = new Line( origin, end )

    val point: Point = line.pointAtDistanceFromP1( 5.0 )
    assertEquals( point, new Point( 0.0, 3.0, 4.0 ) )

    assertEquals( Point.OnALine( origin, end, 5.0 ), new Point( 0.0, 3.0, 4.0 ) )
  }

  @Test
  def distanceAlongYZLineReversed: Unit = {
    val end = new Point( 0.0, 30.0, 40.0 )
    val line = new Line( end, origin )

    val point: Point = line.pointAtDistanceFromP1( 5.0 )
    assertEquals( point, new Point( 0.0, 27.0, 36.0 ) )

    assertEquals( Point.OnALine( origin, end, 5.0 ), new Point( 0.0, 3.0, 4.0 ) )
  }

  @Test
  def distanceAlongXYZLine: Unit = {
    val end = new Point( 10.0, 10.0, 10.0 )
    val line = new Line( origin, end )

    val point: Point = line.pointAtDistanceFromP1( 12.0 )
    assertEquals( point.x, point.y )
    assertEquals( point.x, point.z )

    val point2 = Point.OnALine( origin, end, 12.0 )
    assertEquals( point2.x, point2.y )
    assertEquals( point2.x, point2.z )

    assertEquals( point, point2 )
  }

  @BeforeMethod
  @throws[Exception]
  def setUpMethod: Unit = {
    TestResets.ElementalListerReset()
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod: Unit = {
  }

}

object LineTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass {
  }
}