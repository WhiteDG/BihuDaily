package com.white.bihudaily;

import com.white.bihudaily.utils.CommonUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetYesterday() throws Exception {
        CommonUtil.getYesterday("20160816", 1);
    }

    @Test
    public void testGetShowDate() {
        String showDate = CommonUtil.getShowDate("20160816");
        System.out.print(showDate);
    }

    @Test
    public void testFormatDate() {
        String formatDate = CommonUtil.timestamp2Date(1471502974);
        System.out.print(formatDate);
    }
}