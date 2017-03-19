## Elastic Search

* http://joelabrahamsson.com/elasticsearch-101/ </br>
Follow this tutorial to get basic hands-on in using Elastic Search. Elastic search are REST apis. So, you can use POSTMAN to run them. Second, is also "Sense" Chrome Browser plugin cutomised for Elastic Search. Third you can run using CURL. Haven't explored this third option though. All examples are given using CURL. These can be translated into REST URLS and checked using any REST clients available.

* Elasticsearch Setup </br>
Unzip the downloaded setup, navigate to bin folder and start the server by running elasticsearch.bat file.

* Add these in elasticssearch.yml if the elastic search doesnot start. </br>
http.cors.enabled: true </br>
http.cors.allow-origin: "*" </br>

* Elasticsearch provides an interface to browse through the clusters, indexes and data which can be accessed using the URL http://localhost:9200/_plugin/head. 

* Elasticsearch configuration are part of elasticsearch.yml file. </br>
cluster.name: cards </br>
port.number:9300 </br>
search.cluster.name= cards
socket.transport.address=localhost </br>
search.port.number=9300 </br>
search.index.name=card_index </br>

http://thediscoblog.com/blog/2013/09/03/effortless-elasticsearch-clustering/ </br>
Elastic Search supports clustering. So, you can have three different elastic search instances work in a co-ordinated manner without any admin intevention.

http://blog.e-zest.com/tutorial-elasticsearch-integration-in-a-java-application/ </br>
Every record inserted in Elasticsearch is called a Document which is associated with a unique identifier called Document Id. You can map the database identity of corresponding record to the document Id. This will help bridge the gap between Elasticsearch data and data stored in database at a few functional points. If you don’t map the Document Id, Elasticsearch associates the document with a self-generated unique identifier.
