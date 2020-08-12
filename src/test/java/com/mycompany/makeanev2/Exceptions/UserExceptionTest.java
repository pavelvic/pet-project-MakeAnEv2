package com.mycompany.makeanev2.Exceptions;

import org.junit.Assert;
import org.junit.Test;

public class UserExceptionTest {

    @Test
    public void toStringTest() {
        UserException uex = new UserException("test");
        Assert.assertEquals("test", uex.toString());
    }
}
