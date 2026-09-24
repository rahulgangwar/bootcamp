package com.example;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;

import java.util.List;

public class ElasticSearchApplication {

    public static void main(String[] args) throws Exception {
        ElasticsearchClient client =
                ElasticsearchClient.of(builder -> builder.host("http://localhost:9200"));
        System.out.println(client.info().version().number());

        // Create index
        boolean exists = client.indices().exists(e -> e.index("products")).value();
        if (!exists) {
            TypeMapping typeMapping =
                    new TypeMapping.Builder()
                            .properties("id", p -> p.integer(i -> i))
                            .properties("name", p -> p.text(t -> t))
                            .properties("brand", p -> p.keyword(k -> k))
                            .properties("price", p -> p.double_(d -> d))
                            .build();

            CreateIndexRequest createIndexRequest =
                    new CreateIndexRequest.Builder()
                            .index("products")
                            .mappings(typeMapping)
                            .build();
            client.indices().create(createIndexRequest);
        }

        // Create product
        List<Product> products =
                List.of(
                        new Product(1, "iPhone 17 Pro", "Apple", 79999.0),
                        new Product(2, "Galaxy S26", "Samsung", 69999.0),
                        new Product(3, "iPhone 18 Pro", "Apple", 90000.0),
                        new Product(4, "Pixel 10", "Google", 64999.0));

        for (Product product : products) {
            IndexRequest<Product> request =
                    new IndexRequest.Builder<Product>()
                            .index("products")
                            .id(String.valueOf(product.id()))
                            .document(product)
                            .build();
            client.index(request);
        }

        // Get document
        //        GetRequest getRequest = new
        // GetRequest.Builder().index("products").id("1").build();
        //        var response = client.get(getRequest, Product.class);

        // Search document
        SearchRequest searchRequest =
                new SearchRequest.Builder()
                        .index("products")
                        .query(q -> q.match(m -> m.field("name").query("iphone")))
                        .build();
        var response = client.search(searchRequest, Product.class);
        for (var hit : response.hits().hits()) {
            System.out.println(hit.id());
            System.out.println(hit.source());
        }
        client.close();
    }
}
