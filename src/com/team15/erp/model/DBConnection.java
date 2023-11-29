package com.team15.erp.model;

import com.holub.database.Database;
import com.holub.database.Table;
import com.holub.text.ParseFailure;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class DBConnection {
    Database database;

    private DBConnection() {
    }

    public static DBConnection getInstance() {
        return Inner.dbConnection;
    }

    private static class Inner {
        private static final DBConnection dbConnection = new DBConnection();
    }

    public void initialize(URI directory) throws IOException {
        this.database = new Database(directory);
    }

    public void initialize(File path) throws IOException {
        this.database = new Database(path);
    }

    public void initialize(String path) throws IOException {
        this.database = new Database(path);
    }

    public Table query(String query) throws IOException, ParseFailure {
        return this.database.execute(query);
    }

    public void transaction(ArrayList<String> queries) throws IOException, ParseFailure {
        this.database.begin();

        for(String query: queries) {
            this.database.execute(query);
        }

        this.database.commit();
        this.database.dump();
    }
}
