package com.dvtsoftware.stocktrade.controller;

import com.dvtsoftware.stocktrade.contract.AbstractContractTest;
import com.dvtsoftware.stocktrade.utility.Order;
import com.dvtsoftware.stocktrade.utility.OrderedTestRunner;
import com.dvtsoftware.stocktrade.utility.ResultMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(OrderedTestRunner.class)
public class StocksControllerTest extends AbstractContractTest {

    @Test
    @Order(1)
    public void findHighestAndLowestPriceByStockSymbolInDateRange() throws Exception {
        String res = "{\"symbol\": \"A\", \"highest\": 149.35, \"lowest\": 135.89}";
        assertTrue(ResultMatcher.matchJson(
                mockMvc.perform(get(path("/stocks/A/price?start=2016-12-29&end=2017-01-03"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));

        res = "{\"symbol\": \"MMS\", \"highest\": 183.45, \"lowest\": 152.93}";
        assertTrue(ResultMatcher.matchJson(
                mockMvc.perform(get(path("/stocks/MMS/price?start=2016-12-30&end=2017-01-03"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));

        res = "{\"symbol\": \"WEX\", \"highest\": 172.35, \"lowest\": 172.35}";
        assertTrue(ResultMatcher.matchJson(
                mockMvc.perform(get(path("/stocks/WEX/price?start=2016-12-30&end=2017-01-03"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));

        res = "{\"message\": \"There are no trades in the given date range\"}";
        assertTrue(ResultMatcher.matchJson(
                mockMvc.perform(get(path("/stocks/ZAYO/price?start=2017-01-05&end=2017-01-06"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));
    }

    @Test
    @Order(2)
    public void findHighestAndLowestPriceByNonExistingStockSymbolInDateRange() throws Exception {
        mockMvc.perform(get(path("/stocks/AB/price?start=2016-12-28&end=2016-12-29"))).andExpect(status().isNotFound());
    }
}
