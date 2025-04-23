package com.example.trial;

public class InvestmentProfile {
    private final int age;
    private final double income;
    private final double goalAmount;
    private final int timeHorizon;
    private final RiskTolerance riskTolerance;

    public InvestmentProfile(int age, double income, double goalAmount, int timeHorizon, RiskTolerance riskTolerance) {
        this.age = age;
        this.income = income;
        this.goalAmount = goalAmount;
        this.timeHorizon = timeHorizon;
        this.riskTolerance = riskTolerance;
    }

    public int getAge() { return age; }
    public double getIncome() { return income; }
    public double getGoalAmount() { return goalAmount; }
    public int getTimeHorizon() { return timeHorizon; }
    public RiskTolerance getRiskTolerance() { return riskTolerance; }
}