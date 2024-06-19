package pl.bratek20.commons.http.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import com.github.bratek20.architecture.tests.ApiTest;
import pl.bratek20.tests.InterfaceTest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class HttpApiTest extends InterfaceTest<HttpClientFactory> {

    private WireMockServer server;
    private HttpClient client;

    @Override
    protected void setup() {
        super.setup();
        server = new WireMockServer(8080);
        server.start();

        client = instance.create("http://localhost:8080");
    }

    @Override
    protected void clean() {
        super.clean();
        if (server != null) {
            server.stop();
        }
    }

    record ExampleBody(String message) {}

    @Test
    void shouldSupportGet() {
        server.stubFor(
            WireMock.get(WireMock.urlEqualTo("/get"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("{\"message\": \"Hello World\"}")));

        var response = client.get("/get");

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody(ExampleBody.class))
            .isEqualTo(new ExampleBody("Hello World"));
    }

    @Test
    void shouldSupportPost() {
        server.stubFor(
            WireMock.post(WireMock.urlEqualTo("/post"))
                .withRequestBody(WireMock.equalToJson("{\"message\": \"Request\"}"))
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withStatus(200)
                    .withBody("{\"message\": \"Response\"}")));

        var response = client.post("/post", new ExampleBody("Request"));

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody(ExampleBody.class))
            .isEqualTo(new ExampleBody("Response"));
    }
}