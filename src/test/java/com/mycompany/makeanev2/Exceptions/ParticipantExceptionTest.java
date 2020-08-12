package com.mycompany.makeanev2.Exceptions;

import org.junit.Assert;
import org.junit.Test;

public class ParticipantExceptionTest {

    @Test
    public void toStringTest() {
        ParticipantException pex = new ParticipantException("test");
        Assert.assertEquals("test", pex.toString());
    }
}
