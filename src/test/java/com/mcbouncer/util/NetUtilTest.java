package com.mcbouncer.util;

import com.mcbouncer.util.NetUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NetUtilTest extends TestCase {

    public static Test suite() {
        return new TestSuite(NetUtilTest.class);
    }

    public void testip2long() {
        assertTrue( NetUtil.long2ip(NetUtil.ip2long("127.0.0.1" ) ).equals("127.0.0.1") );
        
        assertFalse( NetUtil.long2ip(NetUtil.ip2long("257.0.0.1" ) ).equals("257.0.0.1") );
        
        try {
            NetUtil.long2ip(NetUtil.ip2long("127.0.0" ) ).equals("127.0.0");
        }
        catch( Exception e ) {
            assertTrue(true);
        }
        
        try {
            NetUtil.long2ip(NetUtil.ip2long("127.0.0.kl" ) ).equals("127.0.0.kl");
        }
        catch( Exception e ) {
            assertTrue(true);
        }
        
    }
}
