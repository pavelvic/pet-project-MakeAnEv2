package com.mycompany.makeanev2.Exceptions;

import org.junit.Assert;
import org.junit.Test;

public class EventExceptionTest {

    @Test
    public void toStringTest() {
        EventException ex = new EventException("test");
        Assert.assertEquals("test", ex.toString());
    }
}
