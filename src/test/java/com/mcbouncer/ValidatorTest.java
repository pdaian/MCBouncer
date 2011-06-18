package com.mcbouncer;

import com.mcbouncer.util.MCBValidators;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValidatorTest extends TestCase {

    public static Test suite() {
        return new TestSuite(ValidatorTest.class);
    }
    public void testInArray() {  
        assertTrue( MCBValidators.inArray("check", new String[] { "check", "this" } ) );
        assertFalse( MCBValidators.inArray("check", new String[] { "chieck", "this" } ) );
        assertTrue( MCBValidators.inArray("", new String[] { "", "this" } ) );
    }
}
