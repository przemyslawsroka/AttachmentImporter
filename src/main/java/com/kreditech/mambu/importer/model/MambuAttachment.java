package com.kreditech.mambu.importer.model;

/**
 * Object that represent metadata for single attachment from Mambu.
 */
public class MambuAttachment {

    private int id;
    private String name;
    private String clientKey;
    private String lastModified;

    public MambuAttachment() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastModified() { return lastModified; }
    public void setLastModified(String lastModified) { this.lastModified = lastModified; }

    public String getClientKey() { return clientKey; }
    public void setClientKey(String clientKey) { this.clientKey = clientKey; }
}
