package com.example.trial;

public class AssetAllocation {
    private final String assetType;
    private final double percentage;

    public AssetAllocation(String assetType, double percentage) {
        this.assetType = assetType;
        this.percentage = percentage;
    }

    public String getAssetType() { return assetType; }
    public double getPercentage() { return percentage; }
}