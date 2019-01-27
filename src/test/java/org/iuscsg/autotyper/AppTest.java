package org.iuscsg.autotyper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    // Find out some what to test this string of text
    // abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890 -= ,./;'[] <>?:"{} \ | !@#$%^&*()_+ ~`
    // not sure how to test the Robot class without spawning a window and typing text into it to save to a file and read
    // it to check if all the characters got sent.
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
