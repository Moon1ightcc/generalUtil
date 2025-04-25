package org.example;


import com.google.gson.annotations.SerializedName;

public class Root {
    @SerializedName("return")
    private Detail returnDetail;

    @SerializedName("drawdown")
    private Detail drawdown;

    @SerializedName("fee")
    private Detail fee;

    @SerializedName("asset_character")
    private Detail assetCharacter;

    @SerializedName("manager")
    private Detail manager;

    // Getters and setters

    public Detail getReturnDetail() { return returnDetail; }
    public void setReturnDetail(Detail returnDetail) { this.returnDetail = returnDetail; }

    public Detail getDrawdown() { return drawdown; }
    public void setDrawdown(Detail drawdown) { this.drawdown = drawdown; }

    public Detail getFee() { return fee; }
    public void setFee(Detail fee) { this.fee = fee; }

    public Detail getAssetCharacter() { return assetCharacter; }
    public void setAssetCharacter(Detail assetCharacter) { this.assetCharacter = assetCharacter; }

    public Detail getManager() { return manager; }
    public void setManager(Detail manager) { this.manager = manager; }

    public static class Detail {
        @SerializedName("compare_tag")
        private String compareTag;

        private String reason;

        // Getters and setters

        public String getCompareTag() { return compareTag; }
        public void setCompareTag(String compareTag) { this.compareTag = compareTag; }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}



     