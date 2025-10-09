package org.futures.model;

public class Sentiment {
    private String source; // e.g., "news", "twitter", "report"
    private String tone;   // "positive", "neutral", "negative"
    private double intensity; // 0–1, confidence or strength of tone
    private int month; // For time-based analysis

    public Sentiment(String source, String tone, double intensity, int month) {
        this.source = source;
        this.tone = tone;
        this.intensity = intensity;
        this.month = month;
    }

    public String getSource() { return source; }
    public String getTone() { return tone; }
    public double getIntensity() { return intensity; }
    public int getMonth() { return month; }
}
