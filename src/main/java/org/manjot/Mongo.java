package org.manjot;

import java.io.IOException;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Mongo {
	public static MongoClient client;

	public static void initialize() throws IOException {
		ConnectionString connectionString = new ConnectionString(Auth.mongo);
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();
		client = MongoClients.create(settings);
	}
}
