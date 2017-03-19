package org.es.elasticSearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/*
 * http://stackoverflow.com/questions/22071198/adding-mapping-to-a-type-from-java-how-do-i-do-it
*/
public class ElasticConfig {
	static {
		StatusLogger.getLogger().setLevel(Level.OFF);
	}

	private static Client getClient() throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", "name").put("client.transport.sniff", true)
				.put("client.transport.ignore_cluster_name", true).build();

		TransportClient transportClient = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		return transportClient;
	}

	public static Map<String, Object> putJsonDocument(String title, String content, Date postDate, String[] tags,
			String author) {

		Map<String, Object> jsonDocument = new HashMap<String, Object>();

		jsonDocument.put("title", title);
		jsonDocument.put("content", content);
		jsonDocument.put("postDate", postDate);
		jsonDocument.put("tags", tags);
		jsonDocument.put("author", author);

		return jsonDocument;
	}

	public static void getDocument(Client client, String index, String type, String id) {

		GetResponse getResponse = client.prepareGet(index, type, id).execute().actionGet();
		Map<String, Object> source = getResponse.getSource();

		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");
	}

	public static void updateDocument(Client client, String index, String type, String id, String field,
			String newValue) throws InterruptedException, ExecutionException {

		UpdateRequest updateRequest = new UpdateRequest(index, type, id)
				.script(new Script("ctx._source." + field + " = \"" + newValue + "\""));
		client.update(updateRequest).get();
	}

	public static void searchDocument(Client client, String index, String type, String field, String value) {

		SearchResponse response = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(QueryBuilders.termQuery(field, value)).setFrom(0).setSize(60).setExplain(true).get();

		SearchHit[] results = response.getHits().getHits();

		System.out.println("Current results: " + results.length);
		for (SearchHit hit : results) {
			System.out.println("------------------------------");
			Map<String, Object> result = hit.getSource();
			System.out.println(result);
		}
	}

	public static void deleteDocument(Client client, String index, String type, String id) {

		DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
		System.out.println("Information on the deleted document:");
		System.out.println("Index: " + response.getIndex());
		System.out.println("Type: " + response.getType());
		System.out.println("Id: " + response.getId());
		System.out.println("Version: " + response.getVersion());
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

		final Client client = getClient();

//		// Create Index and set settings and mappings
//		final String indexName = "test";
//		final String documentType = "tweet";
//		final String documentId = "1";
//		final String fieldName = "foo";
//		final String value = "bar";
//
//		final IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();
//		if (res.isExists()) {
//			final DeleteIndexRequestBuilder delIdx = client.admin().indices().prepareDelete(indexName);
//			delIdx.execute().actionGet();
//		}
//
//		final CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
//
//		// MAPPING GOES HERE
//		CreateIndexResponse indexResponse =  createIndexRequestBuilder.addMapping("tweet", "{\n" +                
//                "    \"tweet\": {\n" +
//                "      \"properties\": {\n" +
//                "        \"message\": {\n" +
//                "          \"type\": \"string\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }")
//        .get();
//		
////		XContentBuilder xcb=jsonBuilder().startObject().startObject("properties").startObject("name").field("type","text").endObject().startObject("tile").field("type","object").endObject().endObject().endObject();	
////		CreateIndexResponse cir=client.admin().indices().prepareCreate("test").setSource(xcb).execute().actionGet();
////		
////		
//////		final XContentBuilder mappingBuilder = jsonBuilder().startObject().startObject(documentType).startObject("_ttl")
//////				.field("enabled", "true").field("default", "1s").endObject().endObject().endObject();
////		final XContentBuilder mappingBuilder = jsonBuilder().startObject().startObject(documentType).startObject("properties").field("default", "1s").endObject().endObject().endObject();
////		
////		System.out.println(mappingBuilder.string());
//////		createIndexRequestBuilder.addMapping(documentType, mappingBuilder);
////
////		// MAPPING DONE
////		CreateIndexResponse indexResponse = createIndexRequestBuilder.execute().actionGet();
//		System.out.println(indexResponse.toString());
//
//		// Add documents
//		// prepareIndex() function allows to store an arbitrary JSON document and make it searchable
//		final IndexRequestBuilder indexRequestBuilder = client.prepareIndex(indexName, documentType, documentId);
//		// build json object
//		final XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();
//		contentBuilder.field(fieldName, value);
//
//		indexRequestBuilder.setSource(contentBuilder);
//		indexRequestBuilder.execute().actionGet();
//
//		System.out.println("Finished creating index");
//		getDocument(client, "test", "tweet", "1");

		getDocument(client, "movies", "movie", "1");
		
		updateDocument(client, "movies", "movie", "1", "year", "1973");
		getDocument(client, "movies", "movie", "1");
		
		searchDocument(client, "movies", "movie", "year", "1962");
		
		// deleteDocument(client, "movies", "movie", "1");

		client.close();
	}
}
