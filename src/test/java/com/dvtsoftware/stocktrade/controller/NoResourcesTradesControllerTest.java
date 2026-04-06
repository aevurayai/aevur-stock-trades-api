package com.dvtsoftware.stocktrade.controller;

import com.dvtsoftware.stocktrade.contract.AbstractContractTest;
import com.dvtsoftware.stocktrade.utility.Order;
import com.dvtsoftware.stocktrade.utility.OrderedTestRunner;
import com.dvtsoftware.stocktrade.utility.ResultMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(OrderedTestRunner.class)
public class NoResourcesTradesControllerTest extends AbstractContractTest {

    @Test
    @Order(1)
    public void findAllTradesByNonExistingStockSymbolAndTradeTypeInDateRange() throws Exception {
        mockMvc.perform(get(path("/trades/stocks/A?type=buy&start=2016-12-28&end=2017-01-03"))).andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    public void findAllTrades() throws Exception {
        String res = "[]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));
    }

    @Test
    @Order(3)
    public void findAllTradesByNonExistingUserId() throws Exception {
        mockMvc.perform(get(path("/trades/users/1"))).andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void findTradeByNonExistingId() throws Exception {
        mockMvc.perform(get(path("/trades/1"))).andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void createTrade() throws Exception {
        String json = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    @Order(6)
    public void createTradeWithExistingId() throws Exception {
        String json = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
    }
}
