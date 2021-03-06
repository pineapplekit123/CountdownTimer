package com.codequest.kitchan.countdowntimer.models;

import com.google.gson.annotations.SerializedName;

public class TimerTask {
    @SerializedName("n")
    private String name;

    @SerializedName("ct")
    private Long millisCreateTime;

    @SerializedName("ft")
    private Long millisInFuture;

    @SerializedName("uft")
    private Long millisUntilFinished;

    private TimerTask(Builder builder) {
        name = builder.name;
        millisCreateTime = builder.millisCreateTime;
        millisInFuture = builder.millisInFuture;
        setMillisUntilFinished(builder.millisUntilFinished);
    }

    public String getName() {
        return name;
    }

    public Long getMillisInFuture() {
        return millisInFuture;
    }

    public Long getMillisUntilFinished() {
        return millisUntilFinished;
    }

    public Long getMillisCreateTime() {
        return millisCreateTime;
    }

    public void setMillisUntilFinished(Long millisUntilFinished) {
        this.millisUntilFinished = millisUntilFinished;
    }

    public static final class Builder {
        private String name;
        private Long millisCreateTime;
        private Long millisInFuture;
        private Long millisUntilFinished;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder millisCreateTime(Long val) {
            millisCreateTime = val;
            return this;
        }

        public Builder millisInFuture(Long val) {
            millisInFuture = val;
            return this;
        }

        public Builder millisUntilFinished(Long val) {
            millisUntilFinished = val;
            return this;
        }

        public TimerTask build() {
            return new TimerTask(this);
        }
    }

    public long getTaskDurationmillis() {
        return millisInFuture - millisUntilFinished + 1000L;
    }
}
