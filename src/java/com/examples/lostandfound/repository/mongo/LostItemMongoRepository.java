package com.examples.lostandfound.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.examples.lostandfound.model.LostItem;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class LostItemMongoRepository {

	private final MongoCollection<Document> lostItemCollection;

	public LostItemMongoRepository(MongoClient client, String databaseName, String collectionName) {
		lostItemCollection = client
			.getDatabase(databaseName)
			.getCollection(collectionName);
	}

	public List<LostItem> findAll() {
		return StreamSupport
			.stream(lostItemCollection.find().spliterator(), false)
			.map(this::fromDocumentToLostItem)
			.collect(Collectors.toList());
	}

	public LostItem findById(String id) {
		Document document = lostItemCollection.find(Filters.eq("id", id)).first();
		if (document == null) {
			return null;
		}
		return fromDocumentToLostItem(document);
	}

	private LostItem fromDocumentToLostItem(Document document) {
		return new LostItem("" + document.get("id"), "" + document.get("description"));
	}
}
