package com.examples.lostandfound.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.examples.lostandfound.model.LostItem;
import com.examples.lostandfound.repository.LostItemRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class LostItemMongoRepository implements LostItemRepository {

	private final MongoCollection<Document> lostItemCollection;

	public LostItemMongoRepository(MongoClient client, String databaseName, String collectionName) {
		lostItemCollection = client
			.getDatabase(databaseName)
			.getCollection(collectionName);
	}

	@Override
	public List<LostItem> findAll() {
		return StreamSupport
			.stream(lostItemCollection.find().spliterator(), false)
			.map(this::fromDocumentToLostItem)
			.collect(Collectors.toList());
	}

	@Override
	public LostItem findById(String id) {
		Document document = lostItemCollection.find(Filters.eq("id", id)).first();
		if (document == null) {
			return null;
		}
		return fromDocumentToLostItem(document);
	}

	@Override
	public void save(LostItem lostItem) {
		lostItemCollection.insertOne(
			new Document()
				.append("id", lostItem.getId())
				.append("description", lostItem.getDescription()));
	}

	@Override
	public void delete(String id) {
		lostItemCollection.deleteOne(Filters.eq("id", id));
	}

	private LostItem fromDocumentToLostItem(Document document) {
		return new LostItem("" + document.get("id"), "" + document.get("description"));
	}
}
