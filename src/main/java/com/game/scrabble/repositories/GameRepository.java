package com.game.scrabble.repositories;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.scrabble.model.Game;
import com.game.scrabble.utilities.ConfigProperties;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class GameRepository {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ConfigProperties properties;

	public Game findById(String id) throws JsonMappingException, JsonProcessingException {
		try (MongoClient mongoClient = MongoClients.create(properties.getUrl())) {
			MongoDatabase database = mongoClient.getDatabase(properties.getDb());
			Document filter = new Document();
			filter.put("_id", id);
			Document game = database.getCollection(properties.getCollection()).find(filter).first();
			ObjectMapper objectMapper = new ObjectMapper();
			Game result = objectMapper.readValue(objectMapper.writeValueAsString(game), new TypeReference<Game>() {
			});
			return result;
		}
	}

	public List<Game> findAll() throws JsonMappingException, JsonProcessingException {
		// TODO Auto-generated method stub
		try (MongoClient mongoClient = MongoClients.create(properties.getUrl())) {
			MongoDatabase database = mongoClient.getDatabase(properties.getDb());
			List<Document> games = database.getCollection(properties.getCollection()).find()
					.into(new ArrayList<Document>());
			ObjectMapper objectMapper = new ObjectMapper();
			List<Game> result = objectMapper.readValue(objectMapper.writeValueAsString(games),
					new TypeReference<List<Game>>() {
					});
			return result;
		}
	}

	public boolean save(Game game) throws JsonProcessingException {
		boolean success = false;
		ObjectMapper objectMapper = new ObjectMapper();
		Document pojoDocument = Document.parse(objectMapper.writeValueAsString(game));
		Document set = new Document();
		set.append("$set", pojoDocument);
		try (MongoClient mongoClient = MongoClients.create(properties.getUrl())) {
			MongoDatabase database = mongoClient.getDatabase(properties.getDb());
			BasicDBObject filter = new BasicDBObject();
			filter.put("_id", game.get_id());
			UpdateOptions options = new UpdateOptions().upsert(true);
			UpdateResult ret = database.getCollection(properties.getCollection()).updateOne(filter, set, options);
			if (ret.getUpsertedId() != null)
				success = true;
			return success;

		}
	}

	public GameRepository() {
	}

	public long deleteById(String gameId) {
		// TODO Auto-generated method stub
		try (MongoClient mongoClient = MongoClients.create(properties.getUrl())) {
			MongoDatabase database = mongoClient.getDatabase(properties.getDb());
			Document filter = new Document();
			filter.put("_id", gameId);
			DeleteResult game = database.getCollection(properties.getCollection()).deleteOne(filter);

			return game.getDeletedCount();
		}
	}
}
