package com.dvtsoftware.stocktrade.contract;

/**
 * Resolves API paths for the verification suite. Uses assessment.api.base-path from
 * src/test/resources/application.properties (e.g. empty for root, or /api for prefixed APIs).
 * Use {@link AbstractContractTest#path(String)} in test classes.
 */
public final class AssessmentPaths {

    private AssessmentPaths() {}

    public static String path(String basePath, String suffix) {
        if (basePath == null || basePath.isEmpty()) {
            return suffix;
        }
        String normalized = basePath.endsWith("/") ? basePath.substring(0, basePath.length() - 1) : basePath;
        return normalized + suffix;
    }
}
