package cz.mirek.template;

import cz.mirek.domain.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RibbonTemplateTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, 9000);
    private MockServerClient mockServerClient;

    private static final int ID_EXPECTATION = 12345;
    HttpRequest HEALTH_ENDPOINT = HttpRequest.request("/health");
    HttpRequest DATA_ENDPOINT = HttpRequest.request("/data");

    @Before
    public void setup() {
        mockServerClient.when(HEALTH_ENDPOINT).respond(HttpResponse.response().withStatusCode(200));
        mockServerClient.when(DATA_ENDPOINT).respond(HttpResponse.response().withStatusCode(200).withBody("{\"userId\" : \"" + ID_EXPECTATION + "\"}"));
    }

    @After
    public void cleanUp() {
        mockServerClient.reset();
    }

    @Test
    public void shouldReturnUserDataNoLoadBalancing() throws Exception {
        RibbonTemplate template = new RibbonTemplate("localhost:9000");
        UserData result = template.getForObject("/data", UserData.class);

        assertThat(result.userId, is(ID_EXPECTATION));

        mockServerClient.verify(HEALTH_ENDPOINT, VerificationTimes.once());
        mockServerClient.verify(DATA_ENDPOINT, VerificationTimes.once());
    }

    @Test
    public void shouldReturnUserDataWithLoadBalancingOnFakeServer() throws Exception {
        RibbonTemplate template = new RibbonTemplate("nonexisting.server.com", "localhost:9000");
        UserData result = template.getForObject("/data", UserData.class);

        assertThat(result.userId, is(ID_EXPECTATION));

        mockServerClient.verify(HEALTH_ENDPOINT, VerificationTimes.once());
        mockServerClient.verify(DATA_ENDPOINT, VerificationTimes.once());
    }

    @Test
    public void shouldReturnUserDataWithLoadBalancingOnFakeServerMultipleTimes() throws Exception {
        RibbonTemplate template = new RibbonTemplate("nonexisting.server.com", "localhost:9000");

        int loop = 3;
        for (int i = 0; i < loop; i++) {
            UserData result = template.getForObject("/data", UserData.class);

            assertThat(result.userId, is(ID_EXPECTATION));
        }

        mockServerClient.verify(HEALTH_ENDPOINT, VerificationTimes.once());
        mockServerClient.verify(DATA_ENDPOINT, VerificationTimes.exactly(loop));
    }

}
