package com.example.trial;

import java.util.ArrayList;
import java.util.List;

public class InvestmentPlanner {
    public InvestmentPlan generatePlan(InvestmentProfile profile) {
        List<AssetAllocation> allocations = new ArrayList<>();
        double projectedReturns;

        switch (profile.getRiskTolerance()) {
            case LOW:
                allocations.add(new AssetAllocation("Fixed Deposits", 40));
                allocations.add(new AssetAllocation("Debt Mutual Funds", 35));
                allocations.add(new AssetAllocation("Equity Mutual Funds", 25));
                projectedReturns = 8.0;
                break;

            case MEDIUM:
                allocations.add(new AssetAllocation("Fixed Deposits", 25));
                allocations.add(new AssetAllocation("Debt Mutual Funds", 35));
                allocations.add(new AssetAllocation("Equity Mutual Funds", 40));
                projectedReturns = 10.0;
                break;

            case HIGH:
                allocations.add(new AssetAllocation("Fixed Deposits", 10));
                allocations.add(new AssetAllocation("Debt Mutual Funds", 30));
                allocations.add(new AssetAllocation("Equity Mutual Funds", 60));
                projectedReturns = 12.0;
                break;

            default:
                throw new IllegalStateException("Invalid risk tolerance");
        }

        double r = projectedReturns / 100 / 12;
        int n = profile.getTimeHorizon() * 12;
        double monthlyInvestment = (profile.getGoalAmount() * r) / (Math.pow(1 + r, n) - 1);

        return new InvestmentPlan(monthlyInvestment, allocations, projectedReturns);
    }
}