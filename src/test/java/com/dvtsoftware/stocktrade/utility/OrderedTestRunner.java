package com.dvtsoftware.stocktrade.utility;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderedTestRunner extends BlockJUnit4ClassRunner {

    public OrderedTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        Map<FrameworkMethod, Integer> orders = new HashMap<>();
        List<FrameworkMethod> methods = super.computeTestMethods();
        int maxOrder = 0;
        for (FrameworkMethod method : methods) {
            Order order = method.getAnnotation(Order.class);
            maxOrder = Math.max(maxOrder, order == null ? 0 : order.value());
            orders.put(method, order == null ? null : order.value());
        }
        final int order = maxOrder + 1;
        methods.forEach(method -> orders.computeIfAbsent(method, value -> order));
        return methods.stream()
                .sorted((f1, f2) -> orders.get(f1) - orders.get(f2))
                .collect(Collectors.toList());
    }
}
