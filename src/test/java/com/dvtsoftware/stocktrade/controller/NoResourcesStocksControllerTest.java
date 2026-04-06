package com.dvtsoftware.stocktrade.controller;

import com.dvtsoftware.stocktrade.contract.AbstractContractTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoResourcesStocksControllerTest extends AbstractContractTest {

    @Test
    public void findHighestAndLowestPriceByNonExistingStockSymbolInDateRange() throws Exception {
        mockMvc.perform(get(path("/stocks/A/price?start=2017-01-05&end=2017-01-06"))).andExpect(status().isNotFound());
    }
}
