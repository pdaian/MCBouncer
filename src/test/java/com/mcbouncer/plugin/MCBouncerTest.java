package com.mcbouncer.plugin;

import java.util.HashMap;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class MCBouncerTest extends TestCase {
    
    public MCBouncerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testOnEnable() {
        MCBouncer mockPlugin = mock(MCBouncer.class);
        
        /*doNothing().when(mockPlugin).setupConfiguration();
        doNothing().when(mockPlugin).setupPermissions();
        doNothing().when(mockPlugin).setupCommands();
        doNothing().when(mockPlugin).setupListeners();
        
        mockPlugin.onEnable();
        
        verify(mockPlugin).setupConfiguration();
        verify(mockPlugin).setupPermissions();
        verify(mockPlugin).setupCommands();
        verify(mockPlugin).setupListeners();*/
    }

    /**
     * Test of onCommand method, of class MCBouncer.
     */
    public void testOnCommand() {
        /*System.out.println("onCommand");
        CommandSender sender = null;
        Command command = null;
        String commandLabel = "";
        String[] args = null;
        MCBouncer instance = new MCBouncer();
        boolean expResult = false;
        boolean result = instance.onCommand(sender, command, commandLabel, args);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of hasPermission method, of class MCBouncer.
     */
    public void testHasPermission() {
        /*System.out.println("hasPermission");
        Player player = null;
        String permission = "";
        MCBouncer instance = new MCBouncer();
        boolean expResult = false;
        boolean result = instance.hasPermission(player, permission);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of messageMods method, of class MCBouncer.
     */
    public void testMessageMods() {
        /*System.out.println("messageMods");
        String message = "";
        MCBouncer instance = new MCBouncer();
        instance.messageMods(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of onDisable method, of class MCBouncer.
     */
    public void testOnDisable() {
        /*System.out.println("onDisable");
        MCBouncer instance = new MCBouncer();
        instance.onDisable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of setupPermissions method, of class MCBouncer.
     */
    public void testSetupPermissions() {
        /*System.out.println("setupPermissions");
        MCBouncer instance = new MCBouncer();
        instance.setupPermissions();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of setupListeners method, of class MCBouncer.
     */
    public void testSetupListeners() {
        /*System.out.println("setupListeners");
        MCBouncer instance = new MCBouncer();
        instance.setupListeners();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of setupCommands method, of class MCBouncer.
     */
    public void testSetupCommands() {
    }

    /**
     * Test of setupConfiguration method, of class MCBouncer.
     */
    public void testSetupConfiguration() {
        /*System.out.println("setupConfiguration");
        MCBouncer instance = new MCBouncer();
        instance.setupConfiguration();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }
}
