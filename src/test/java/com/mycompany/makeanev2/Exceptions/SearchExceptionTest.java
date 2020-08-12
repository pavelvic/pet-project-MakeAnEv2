package com.mycompany.makeanev2.Exceptions;

import org.junit.Assert;
import org.junit.Test;

public class SearchExceptionTest {

    @Test
    public void toStringTest() {
        SearchException sex = new SearchException("test");
        Assert.assertEquals("test", sex.toString());
    }
}
