package com.instacart.project.service;

import com.github.vincentrussell.query.mongodb.sql.converter.QueryConverter;
import com.github.vincentrussell.query.mongodb.sql.converter.QueryResultIterator;
import com.instacart.project.model.MongoQueryDetail;
import com.instacart.project.model.ResponseDetail;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MongoDBconn {
    private String username = "superadmin";
    private String password = "AQ%21sw2DE%23";
    private String uri = "mongodb://"+username+":"+password+"@ec2-18-117-109-188.us-east-2.compute.amazonaws.com:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false";
    private String db = null;

    public String getDb() {
        return db;
    }
    public void setDb(String db) {
        this.db = db;
    }

    public ResponseDetail exec(String sql) throws Exception {
        try{
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase(getDb());
            QueryConverter queryConverter = new QueryConverter.Builder().sqlString(sql).build();

            Instant start = Instant.now();
            Object result = queryConverter.run(database);
            System.out.println("Executed!");
            Instant finish = Instant.now();

            ResponseDetail rd = new ResponseDetail();
            long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
            rd.setElapsed_time(timeElapsed);

            if (long.class.isInstance(result)){
                return null;
            } else {
                QueryResultIterator<Document> iterator = (QueryResultIterator)result;
                ArrayList<Document> docs = new ArrayList<>();
                while(iterator.hasNext()){
                    Document curr = iterator.next();
                    // ToString the ObjectID
                    if (curr.getObjectId("_id") != null) {
                        String str_id = curr.getObjectId("_id").toString();
                        curr.put("_id", str_id);
                    }
                    docs.add(curr);
                }

                rd.setRawData(docs);
                return rd;
            }
        } catch (Exception e) {
            System.err.println("An exception occurred when initializing MongoDB client: " + e);
            throw e;
        }
    }

    public ResponseDetail exec_v1(MongoQueryDetail query) throws Exception {
        try{
            MongoClient mongoClient = MongoClients.create(uri);

            MongoDatabase database = mongoClient.getDatabase(getDb());

            try {
                String strCmd = "{ 'find': "+ query.getCollection();
                if (query.getFilter().trim() != "") {
                    strCmd = strCmd + ", 'filter': " + query.getFilter();
                }
                if (query.getSort().trim() != "") {
                    strCmd = strCmd + ", 'sort': " + query.getSort();
                }
                if (query.getProjection().trim() != "") {
                    strCmd = strCmd + ", 'projection': " + query.getProjection();
                }
                if (query.getCollation().trim() != "") {
                    strCmd = strCmd + ", 'collation': " + query.getCollation();
                }
                if (query.getSkip().trim() != "" && query.getSkip().trim() != "0") {
                    strCmd = strCmd + ", 'skip': " + query.getSkip();
                }
                if (query.getLimit().trim() != "" && query.getLimit().trim() != "0") {
                    strCmd = strCmd + ", 'limit': " + query.getLimit();
                }
                if (query.getMaxTimeMS().trim() != "" && query.getMaxTimeMS().trim() != "0") {
                    strCmd = strCmd + ", 'maxTimeMS': " + query.getMaxTimeMS();
                }
                strCmd = strCmd +" }";


                Document bsonCmd = Document.parse(strCmd);
                Instant start = Instant.now();
                Document result = database.runCommand(bsonCmd);
                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();  //in millis

                ResponseDetail rd = new ResponseDetail();
                rd.setElapsed_time(timeElapsed);


                Document cursor = (Document) result.get("cursor");
                List<Document> docs = (List<Document>) cursor.get("firstBatch");

                rd.setRawData(docs);

                System.out.println("Connected successfully to MongoDB.");

                return rd;
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            } finally {
                mongoClient.close();
            }

            return null;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e);
            return null;
        }
    }

    public ResponseDetail exec_v2(String query) throws Exception {
        try{
            MongoClient mongoClient = MongoClients.create(uri);

            MongoDatabase database = mongoClient.getDatabase(getDb());

            try {
                Document bsonCmd = Document.parse(query);
                Instant start = Instant.now();
                Document result = database.runCommand(bsonCmd);
                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();  //in millis

                ResponseDetail rd = new ResponseDetail();
                rd.setElapsed_time(timeElapsed);

                Document cursor = (Document) result.get("cursor");
                List<Document> docs = (List<Document>) cursor.get("firstBatch");

                rd.setRawData(docs);

                System.out.println("Connected successfully to MongoDB.");

                return rd;
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            } finally {
                mongoClient.close();
            }

            return null;
        } catch (Exception e) {
            System.err.println("An exception occurred: " + e);
            return null;
        }
    }

}
