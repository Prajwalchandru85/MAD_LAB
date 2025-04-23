package com.example.trial;

import java.util.List;

public class InvestmentPlan {
    private final double monthlyInvestment;
    private final List<AssetAllocation> allocations;
    private final double projectedReturns;

    public InvestmentPlan(double monthlyInvestment, List<AssetAllocation> allocations, double projectedReturns) {
        this.monthlyInvestment = monthlyInvestment;
        this.allocations = allocations;
        this.projectedReturns = projectedReturns;
    }

    public double getMonthlyInvestment() { return monthlyInvestment; }
    public List<AssetAllocation> getAllocations() { return allocations; }
    public double getProjectedReturns() { return projectedReturns; }
}