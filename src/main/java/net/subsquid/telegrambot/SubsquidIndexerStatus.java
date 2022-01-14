package net.subsquid.telegrambot;

import org.springframework.core.style.ToStringCreator;

public class SubsquidIndexerStatus {

    private int chainHeight;
    private int head;
    private String hydraVersion;
    private boolean inSync;
    private int lastComplete;
    private int maxComplete;
    private String url;

    public int getChainHeight() {
        return chainHeight;
    }

    public void setChainHeight(int chainHeight) {
        this.chainHeight = chainHeight;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getHydraVersion() {
        return hydraVersion;
    }

    public void setHydraVersion(String hydraVersion) {
        this.hydraVersion = hydraVersion;
    }

    public boolean isInSync() {
        return inSync;
    }

    public void setInSync(boolean inSync) {
        this.inSync = inSync;
    }

    public int getLastComplete() {
        return lastComplete;
    }

    public void setLastComplete(int lastComplete) {
        this.lastComplete = lastComplete;
    }

    public int getMaxComplete() {
        return maxComplete;
    }

    public void setMaxComplete(int maxComplete) {
        this.maxComplete = maxComplete;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("url", this.url)
            .append("chainHeight", this.chainHeight)
            .append("head", this.head)
            .append("hydraVersion", this.hydraVersion)
            .append("inSync", this.inSync)
            .append("lastComplete", this.lastComplete)
            .append("maxComplete", this.maxComplete)
            .toString();
    }
}