package com.dvtsoftware.stocktrade.controller;

import com.dvtsoftware.stocktrade.contract.AbstractContractTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourcesControllerTest extends AbstractContractTest {

    @Test
    public void eraseAllRecords() throws Exception {
        mockMvc.perform(delete(path("/erase")))
                .andExpect(status().isOk());
    }
}
