package com.mobiletheatertech.plot;

import static org.junit.Assert.*;

import org.junit.Test;

public class SolidTest
{
	@Test
	public void hasHeight()
	{
		float height = 11.4f;
		Solid solid = new Solid( height, 12.9f, 12.4f );
		
		assertEquals( height, solid.getHeight(), 0.0 );
	}

	@Test
	public void hasWidth()
	{
		float width = 11.4f;
		Solid solid = new Solid( 12.9f, width, 12.4f );
		
		assertEquals( width, solid.getWidth(), 0.0 );
	}

	@Test
	public void hasDepth()
	{
		float depth = 11.4f;
		Solid solid = new Solid( 12.9f, 12.4f, depth );
		
		assertEquals( depth, solid.getDepth(), 0.0 );
	}
}
