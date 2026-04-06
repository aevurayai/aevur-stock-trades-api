package com.dvtsoftware.stocktrade.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TradesControllerTest.class,
        StocksControllerTest.class,
        ResourcesControllerTest.class,
        NoResourcesStocksControllerTest.class,
        NoResourcesTradesControllerTest.class
})
public class TestSuite {
}
