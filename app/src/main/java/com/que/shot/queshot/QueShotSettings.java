package com.que.shot.queshot;

public class QueShotSettings {
    private boolean isClearViewsEnabled;
    private boolean isTransparencyEnabled;

    public boolean isTransparencyEnabled() {
        return this.isTransparencyEnabled;
    }

    public boolean isClearViewsEnabled() {
        return this.isClearViewsEnabled;
    }

    private QueShotSettings(Builder builder) {
        this.isClearViewsEnabled = builder.isClearViewsEnabled;
        this.isTransparencyEnabled = builder.isTransparencyEnabled;
    }

    public static class Builder {

        public boolean isClearViewsEnabled = true;

        public boolean isTransparencyEnabled = true;

        public QueShotSettings build() {
            return new QueShotSettings(this);
        }
    }
}
