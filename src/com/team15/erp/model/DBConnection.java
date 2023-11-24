package com.team15.erp.model;

import com.holub.database.Database;
import com.holub.database.Table;
import com.holub.text.ParseFailure;

import java.io.File;
import java.io.IOException;
import java.net.URI;

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
        this.database.begin();
    }

    public void initialize(File path) throws IOException {
        this.database = new Database(path);
        this.database.begin();
    }

    public void initialize(String path) throws IOException {
        this.database = new Database(path);
        this.database.begin();
    }

    public Table execute(String expr) throws IOException, ParseFailure {
        return this.database.execute(expr);
    }
}
