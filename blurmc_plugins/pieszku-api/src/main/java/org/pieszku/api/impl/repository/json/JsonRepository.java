package org.pieszku.api.impl.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.pieszku.api.API;
import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.impl.repository.CrudRepository;
import org.pieszku.api.redis.packet.type.UpdateType;

import java.util.Collection;
import java.util.HashSet;

public class JsonRepository<ID, T extends Identifiable<ID>> implements CrudRepository<ID, T> {


    private Class<T> type;
    private String documentName;
    private String collection;
    protected Gson gson;

    public JsonRepository(Class<T> type, String documentName, String collection) {
        this(type, documentName,  collection, new GsonBuilder().setPrettyPrinting().create());
    }
    public JsonRepository(Class<T> type, String documentName, String collection, Gson gson){
        this.type = type;
        this.documentName = documentName;
        this.collection = collection;
        this.gson = gson;
    }

    @Override
    public void update(T object, ID field, UpdateType updateType) {
        if(updateType.equals(UpdateType.CREATE)){
            API.getInstance()
                    .getMongoService()
                    .getMongoDatabase()
                    .getCollection(this.collection)
                    .insertOne(
                            Document.parse(
                                    new Gson().toJson(object)));
            return;
        }
        if(updateType.equals(UpdateType.UPDATE)){
            API.getInstance().getMongoService().getMongoDatabase().getCollection(this.collection).findOneAndUpdate(new Document(this.documentName, field), new Document("$set", Document.parse(new Gson().toJson(object))));
            return;
        }
        if(updateType.equals(UpdateType.REMOVE)){
            API.getInstance().getMongoService().getMongoDatabase().getCollection(this.collection).findOneAndDelete(new Document(this.documentName, field));
        }

    }

    @Override
    public Collection<T> saveAll(Collection<T> objects) {
        return null;
    }

    @Override
    public T find(ID id) {
        Object object = new Gson().fromJson(API.getInstance().getMongoService().getMongoDatabase().getCollection(this.collection)
                .find(Filters.eq(this.documentName, id)).first().toJson(JsonWriterSettings.builder().build()), this.type);
        return (T) object;
    }

    @Override
    public Collection<T> findAll() {
        Collection<T> collection = new HashSet<>();
        API.getInstance().getMongoService().getMongoDatabase().getCollection(this.collection).find().forEach((Block<? super Document>) (Document document) -> {
            Object object = new Gson().fromJson(document.toJson(JsonWriterSettings.builder().build()), this.getType());
            collection.add((T) object);
        });
        System.out.println("[API]" + this.getType().getSimpleName() + " loaded from mongodb: " + collection.size());
        return collection;
    }

    @Override
    public boolean delete(T object) {
        return false;
    }

    public Class<T> getType() {
        return type;
    }

    public Gson getGson() {
        return gson;
    }

    public String getCollection() {
        return collection;
    }


    public String getDocumentName() {
        return documentName;
    }
}
