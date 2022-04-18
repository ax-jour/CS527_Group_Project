package com.instacart.project.model;

public class MongoQueryDetail {
    private String db;
    private String collection;
    private String filter;
    private String sort;
    private String projection;
    private String collation;
    private String skip;
    private String limit;
    private String maxTimeMS;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMaxTimeMS() {
        return maxTimeMS;
    }

    public void setMaxTimeMS(String maxTimeMS) {
        this.maxTimeMS = maxTimeMS;
    }
}
