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
public class TradesControllerTest extends AbstractContractTest {

    @Test
    @Order(1)
    public void createTrade() throws Exception {
        String json = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 2, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 19, \"price\": 153.57, \"timestamp\": \"2016-12-28 13:15:52\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 3, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"A\", \"shares\": 12, \"price\": 135.89, \"timestamp\": \"2016-12-28 13:18:18\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 4, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"MMS\", \"shares\": 15, \"price\": 183.45, \"timestamp\": \"2016-12-28 15:15:50\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 5, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"WEX\", \"shares\": 10, \"price\": 190.73, \"timestamp\": \"2016-12-29 09:05:23\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 6, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 30, \"price\": 137.86, \"timestamp\": \"2016-12-30 11:42:40\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 7, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"MMS\", \"shares\": 19, \"price\": 183.45, \"timestamp\": \"2016-12-30 12:35:21\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 8, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"WEX\", \"shares\": 11, \"price\": 172.35, \"timestamp\": \"2016-12-30 13:07:19\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 9, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"ZAYO\", \"shares\": 25, \"price\": 154.77, \"timestamp\": \"2016-12-30 13:36:20\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 10, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 28, \"price\": 152.93, \"timestamp\": \"2016-12-30 14:48:14\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 11, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"ZAYO\", \"shares\": 30, \"price\": 154.77, \"timestamp\": \"2016-12-31 09:59:16\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 12, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"WEX\", \"shares\": 18, \"price\": 172.35, \"timestamp\": \"2016-12-31 12:01:56\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 13, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"A\", \"shares\": 22, \"price\": 135.89, \"timestamp\": \"2016-12-31 13:27:40\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 14, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 30, \"price\": 136.68, \"timestamp\": \"2016-12-31 15:29:15\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 15, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 29, \"price\": 168.67, \"timestamp\": \"2016-12-31 15:47:40\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 16, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 25, \"price\": 149.35, \"timestamp\": \"2017-01-03 11:59:32\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 17, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 25, \"price\": 182.01, \"timestamp\": \"2017-01-03 14:27:42\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = "{\"id\": 18, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 19, \"price\": 171.17, \"timestamp\": \"2017-01-05 15:43:00\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void createTradeWithExistingId() throws Exception {
        String json = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";
        mockMvc.perform(post(path("/trades")).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void findTradeById() throws Exception {
        String res = "{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}";
        assertTrue(ResultMatcher.matchJson(
                mockMvc.perform(get(path("/trades/1"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));
    }

    @Test
    @Order(4)
    public void findTradeByNonExistingId() throws Exception {
        mockMvc.perform(get(path("/trades/19"))).andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void findAllTradesByUserId() throws Exception {
        String res = "[{\"id\": 2, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 19, \"price\": 153.57, \"timestamp\": \"2016-12-28 13:15:52\"}, {\"id\": 10, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 28, \"price\": 152.93, \"timestamp\": \"2016-12-30 14:48:14\"}, {\"id\": 14, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 30, \"price\": 136.68, \"timestamp\": \"2016-12-31 15:29:15\"}, {\"id\": 15, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 29, \"price\": 168.67, \"timestamp\": \"2016-12-31 15:47:40\"}, {\"id\": 16, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 25, \"price\": 149.35, \"timestamp\": \"2017-01-03 11:59:32\"}, {\"id\": 17, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 25, \"price\": 182.01, \"timestamp\": \"2017-01-03 14:27:42\"}, {\"id\": 18, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 19, \"price\": 171.17, \"timestamp\": \"2017-01-05 15:43:00\"}]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades/users/1"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));
    }

    @Test
    @Order(6)
    public void findAllTradesByNonExistingUserId() throws Exception {
        mockMvc.perform(get(path("/trades/users/5"))).andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void findAllTradesByStockSymbolAndTradeTypeInDateRange() throws Exception {
        String res = "[{\"id\": 16, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 25, \"price\": 149.35, \"timestamp\": \"2017-01-03 11:59:32\"}]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades/stocks/A?type=sell&start=2016-12-28&end=2017-01-03"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));

        res = "[]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades/stocks/MMS?type=sell&start=2016-12-29&end=2016-12-31"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));

        res = "[{\"id\": 5, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"WEX\", \"shares\": 10, \"price\": 190.73, \"timestamp\": \"2016-12-29 09:05:23\"}, {\"id\": 8, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"WEX\", \"shares\": 11, \"price\": 172.35, \"timestamp\": \"2016-12-30 13:07:19\"}, {\"id\": 12, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"WEX\", \"shares\": 18, \"price\": 172.35, \"timestamp\": \"2016-12-31 12:01:56\"}]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades/stocks/WEX?type=buy&start=2016-12-28&end=2017-01-02"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));

        res = "[{\"id\": 6, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 30, \"price\": 137.86, \"timestamp\": \"2016-12-30 11:42:40\"}, {\"id\": 9, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"ZAYO\", \"shares\": 25, \"price\": 154.77, \"timestamp\": \"2016-12-30 13:36:20\"}, {\"id\": 11, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"ZAYO\", \"shares\": 30, \"price\": 154.77, \"timestamp\": \"2016-12-31 09:59:16\"}]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades/stocks/ZAYO?type=buy&start=2016-12-29&end=2017-01-01"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));
    }

    @Test
    @Order(8)
    public void findAllTradesByNonExistingStockSymbolAndTradeTypeInDateRange() throws Exception {
        mockMvc.perform(get(path("/trades/stocks/AB?type=buy&start=2016-12-28&end=2016-12-29"))).andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    public void findAllTrades() throws Exception {
        String res = "[{\"id\": 1, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 11, \"price\": 154.77, \"timestamp\": \"2016-12-28 11:44:37\"}, {\"id\": 2, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 19, \"price\": 153.57, \"timestamp\": \"2016-12-28 13:15:52\"}, {\"id\": 3, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"A\", \"shares\": 12, \"price\": 135.89, \"timestamp\": \"2016-12-28 13:18:18\"}, {\"id\": 4, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"MMS\", \"shares\": 15, \"price\": 183.45, \"timestamp\": \"2016-12-28 15:15:50\"}, {\"id\": 5, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"WEX\", \"shares\": 10, \"price\": 190.73, \"timestamp\": \"2016-12-29 09:05:23\"}, {\"id\": 6, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"ZAYO\", \"shares\": 30, \"price\": 137.86, \"timestamp\": \"2016-12-30 11:42:40\"}, {\"id\": 7, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"MMS\", \"shares\": 19, \"price\": 183.45, \"timestamp\": \"2016-12-30 12:35:21\"}, {\"id\": 8, \"type\": \"buy\", \"user\": {\"id\": 4, \"name\": \"Derrick Garcia\"}, \"symbol\": \"WEX\", \"shares\": 11, \"price\": 172.35, \"timestamp\": \"2016-12-30 13:07:19\"}, {\"id\": 9, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"ZAYO\", \"shares\": 25, \"price\": 154.77, \"timestamp\": \"2016-12-30 13:36:20\"}, {\"id\": 10, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 28, \"price\": 152.93, \"timestamp\": \"2016-12-30 14:48:14\"}, {\"id\": 11, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"ZAYO\", \"shares\": 30, \"price\": 154.77, \"timestamp\": \"2016-12-31 09:59:16\"}, {\"id\": 12, \"type\": \"buy\", \"user\": {\"id\": 2, \"name\": \"Daniel Cortez\"}, \"symbol\": \"WEX\", \"shares\": 18, \"price\": 172.35, \"timestamp\": \"2016-12-31 12:01:56\"}, {\"id\": 13, \"type\": \"buy\", \"user\": {\"id\": 3, \"name\": \"Connie Palmer\"}, \"symbol\": \"A\", \"shares\": 22, \"price\": 135.89, \"timestamp\": \"2016-12-31 13:27:40\"}, {\"id\": 14, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 30, \"price\": 136.68, \"timestamp\": \"2016-12-31 15:29:15\"}, {\"id\": 15, \"type\": \"buy\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 29, \"price\": 168.67, \"timestamp\": \"2016-12-31 15:47:40\"}, {\"id\": 16, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"A\", \"shares\": 25, \"price\": 149.35, \"timestamp\": \"2017-01-03 11:59:32\"}, {\"id\": 17, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 25, \"price\": 182.01, \"timestamp\": \"2017-01-03 14:27:42\"}, {\"id\": 18, \"type\": \"sell\", \"user\": {\"id\": 1, \"name\": \"Jennifer Long\"}, \"symbol\": \"MMS\", \"shares\": 19, \"price\": 171.17, \"timestamp\": \"2017-01-05 15:43:00\"}]";
        assertTrue(ResultMatcher.matchJsonArray(
                mockMvc.perform(get(path("/trades"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                res, true));
    }
}
