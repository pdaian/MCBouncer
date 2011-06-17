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
    
    public void testIsInteger() {  
        assertTrue(MCBValidators.isInteger("5"));
        assertTrue(MCBValidators.isInteger("0"));
        assertFalse(MCBValidators.isInteger("5.5"));
        assertFalse(MCBValidators.isInteger(""));
        assertFalse(MCBValidators.isInteger("text"));
    }
    
    public void testIsNumeric() {       
        assertTrue(MCBValidators.isNumeric("5"));
        assertTrue(MCBValidators.isNumeric("0"));
        assertTrue(MCBValidators.isNumeric("5.5"));
        assertFalse(MCBValidators.isNumeric(""));
        assertFalse(MCBValidators.isNumeric("text"));
    }
    
    public void testInArray() {  
        assertTrue( MCBValidators.inArray("check", new String[] { "check", "this" } ) );
        assertFalse( MCBValidators.inArray("check", new String[] { "chieck", "this" } ) );
        assertTrue( MCBValidators.inArray("", new String[] { "", "this" } ) );
    }
}
