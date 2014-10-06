package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotSame;

public class SolidTest
{
	@Test
	public void hasHeight()
	{
        double height = 11.4;
		Solid solid = new Solid( 12.9, 12.4, height );
		
		assertEquals( height, solid.getHeight(), 0.0 );
	}

	@Test
	public void hasWidth()
	{
        double width = 11.4f;
		Solid solid = new Solid( width, 12.4, 12.9 );
		
		assertEquals( width, solid.getWidth(), 0.0 );
	}

	@Test
	public void hasDepth()
	{
        double depth = 11.4f;
		Solid solid = new Solid( 12.4, depth, 12.9 );
		
		assertEquals( depth, solid.getDepth(), 0.0 );
	}
}
