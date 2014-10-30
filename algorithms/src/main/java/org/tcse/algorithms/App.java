package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    @Test
    public void test(){
    	StringBuilder stringBuilder = new StringBuilder();
    	StringBuffer stringBuffer = new StringBuffer();
    	assertNotNull(stringBuilder);
    	assertNotNull(stringBuffer);
    }
    
    @Test
    public void testLock(){
    	ReentrantLock lock  = new ReentrantLock();
    	lock.lock();
    }
    
}
