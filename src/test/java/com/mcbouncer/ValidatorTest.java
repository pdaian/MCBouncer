package com.mcbouncer;

import com.mcbouncer.util.MCBValidators;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValidatorTest extends TestCase {

    public static Test suite() {
        return new TestSuite(ValidatorTest.class);
    }

    public void testUser() { 
        assertFalse(MCBValidators.UserValidator( new String[] {} ) );
        assertTrue(MCBValidators.UserValidator( new String[] {"foo"} ) );
        assertFalse(MCBValidators.UserValidator( new String[] {"foo", "bar"} ) );
    }
    
    public void testUserAndReason() {   
        assertFalse(MCBValidators.UserAndReasonValidator( new String[] {} ) );
        assertTrue(MCBValidators.UserAndReasonValidator( new String[] {"foo"} ) );
        assertTrue(MCBValidators.UserAndReasonValidator( new String[] {"foo", "bar"} ) );
        assertTrue(MCBValidators.UserAndReasonValidator( new String[] {"foo", "bar", "baz"} ) );
    }
    
}
