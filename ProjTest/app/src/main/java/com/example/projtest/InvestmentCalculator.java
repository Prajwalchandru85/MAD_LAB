package com.example.projtest;

import java.util.HashMap;
import java.util.Map;

public class InvestmentCalculator {

    /**
     * Calculate future value using compound interest formula
     */
    public static double calculateFutureValue(double principal, double rate, int years, int compoundingPerYear) {
        double r = rate / 100.0;
        int n = compoundingPerYear;
        int t = years;

        return principal * Math.pow(1 + (r/n), n*t);
    }

    /**
     * Get asset allocation based on risk profile and time horizon
     */
    public static Map<String, Double> getAssetAllocation(String riskProfile, int timeHorizon) {
        Map<String, Double> allocation = new HashMap<>();

        // Base allocations by risk profile
        if ("Low Risk".equals(riskProfile)) {
            allocation.put("Fixed Deposits", 50.0);
            allocation.put("Debt Mutual Funds", 30.0);
            allocation.put("Equity Mutual Funds", 20.0);
        } else if ("Medium Risk".equals(riskProfile)) {
            allocation.put("Fixed Deposits", 30.0);
            allocation.put("Debt Mutual Funds", 30.0);
            allocation.put("Equity Mutual Funds", 40.0);
        } else { // High Risk
            allocation.put("Fixed Deposits", 10.0);
            allocation.put("Debt Mutual Funds", 20.0);
            allocation.put("Equity Mutual Funds", 70.0);
        }

        // Adjust based on time horizon
        adjustForTimeHorizon(allocation, timeHorizon);

        // Adjust based on specific goal (in a real app, this would be more sophisticated)
        // adjustForGoal(allocation, goal);

        return allocation;
    }

    /**
     * Adjust allocation based on investment time horizon
     */
    private static void adjustForTimeHorizon(Map<String, Double> allocation, int timeHorizon) {
        double equityAdjustment = 0.0;

        if (timeHorizon < 3) {
            // Short-term: reduce equity
            equityAdjustment = -10.0;
        } else if (timeHorizon >= 7) {
            // Long-term: increase equity
            equityAdjustment = 10.0;
        }

        // Apply adjustment (ensuring allocations stay in reasonable ranges)
        if (equityAdjustment != 0) {
            double equity = allocation.get("Equity Mutual Funds");
            double fd = allocation.get("Fixed Deposits");

            // Adjust equity while keeping it between 10% and 80%
            double newEquity = Math.max(10.0, Math.min(80.0, equity + equityAdjustment));
            double actualAdjustment = newEquity - equity;

            allocation.put("Equity Mutual Funds", newEquity);

            // Take the adjustment from FDs (or add to FDs if reducing equity)
            double newFd = Math.max(5.0, fd - actualAdjustment);
            allocation.put("Fixed Deposits", newFd);

            // Ensure the total is still 100%
            double total = allocation.values().stream().mapToDouble(Double::doubleValue).sum();
            double debt = allocation.get("Debt Mutual Funds");
            allocation.put("Debt Mutual Funds", debt + (100 - total));
        }
    }

    /**
     * Calculate expected annual return based on asset allocation
     */
    public static double getExpectedReturn(Map<String, Double> allocation) {
        // Estimated returns for each asset class
        double fdReturn = 6.0;  // 6% for FDs
        double debtReturn = 8.0; // 8% for debt funds
        double equityReturn = 12.0; // 12% for equity funds

        double weightedReturn = 0.0;

        // Calculate weighted average return
        if (allocation.containsKey("Fixed Deposits")) {
            weightedReturn += (allocation.get("Fixed Deposits") / 100) * fdReturn;
        }

        if (allocation.containsKey("Debt Mutual Funds")) {
            weightedReturn += (allocation.get("Debt Mutual Funds") / 100) * debtReturn;
        }

        if (allocation.containsKey("Equity Mutual Funds")) {
            weightedReturn += (allocation.get("Equity Mutual Funds") / 100) * equityReturn;
        }

        return weightedReturn;
    }
}