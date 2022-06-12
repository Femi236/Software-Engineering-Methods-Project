package nl.tudelft.sem.template.gateway;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RequestBuilder {

    private static final String startUrl = "http://localhost:";

    private static final String errorMessage = "CommunicationService with server failed";

    private static final String authorization = "authorization";

    /**
     * Get request to other microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @return the response as a String
     */
    public static String get(String port, String url) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.GET).uri(url);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }


    }


    /**
     * delete request to other microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @return the response as a String
     */
    public static String delete(String port, String url, String args) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.DELETE).uri(url + args);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }


    }

    /**
     * Post request to other microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @param args the arguments of the post request
     * @return the response as a String
     */
    public static String post(String port, String url, String args) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.POST).uri(url + args);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }


    }

    /**
     * Get request to Authentication microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @param token the token to verify that the user is logged in
     * @return the response as a String
     */
    public static String getAuth(String port, String url, String token) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.GET).uri(url)
                .header(authorization, token);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }


    }

    /**
     * Post request to Authentication microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @param args the arguments of the post request
     * @param token the token to verify that the user is logged in
     * @return the response as a String
     */
    public static String postAuth(String port, String url, String args, String token) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.POST).uri(url + args)
                .header(authorization, token);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }





    }

    /**
     * Delete request to Authentication microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @param args the arguments of the post request
     * @param token the token to verify that the user is logged in
     * @return the response as a String
     */
    public static String deleteAuth(String port, String url, String args, String token) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.DELETE).uri(url + args)
                .header(authorization, token);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }
    }

    /**
     * update request to Authentication microservices.
     *
     * @param port the port the microservice is running on
     * @param url the url of the request we want
     * @param args the arguments of the post request
     * @param token the token to verify that the user is logged in
     * @return the response as a String
     */
    public static String putAuth(String port, String url, String args, String token) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestBodySpec uri = client.method(HttpMethod.PUT).uri(url + args)
                .header(authorization, token);

        try {
            WebClient.ResponseSpec response = uri.retrieve();
            return response.bodyToMono(String.class).block();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMessage;
        }
    }

    /**
     * Sends a post request to auth inclusing login details in the body in order to receive
     * a bearer token.
     *
     * @param port the port of auth
     * @param url the url of the auth method
     * @param body the body containing the login details
     * @return the token if correct login details.
     */
    public static String postLogIn(String port, String url, String body) {
        String baseUrl = startUrl + port;
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();

        WebClient.RequestHeadersSpec<?> uri = client.method(HttpMethod.POST).uri(url)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));

        try {
            String res = uri.exchange()
                    .map(clientResponse -> clientResponse.headers().header("Authorization").get(0))
                    .block();
            return res;

        } catch (IndexOutOfBoundsException e) {
            return "LoginError";
        } catch (Exception e) {
            return null;
        }
    }


}
