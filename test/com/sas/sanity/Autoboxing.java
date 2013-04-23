package com.sas.sanity;

import org.junit.Assert;
import org.junit.Test;

public class Autoboxing {

	@Test
	public void autoboxing() {
		final Integer i1 = 132;
		final Integer i2 = 132;
		final Integer i3 = Integer.valueOf(132);
		final Integer i4 = Integer.valueOf(132);

		Assert.assertTrue(i1 == i2);
		Assert.assertTrue(i3 == i4);
		Assert.assertTrue(i1 == i3);
	}
}
