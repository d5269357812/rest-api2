package rest.api2.controller.aabstract

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.aabstract.Frult
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class FrultControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "List的猴子測試"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/frult")

        HttpResponse<List<Frult>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(Frult))
        """use rsp.body to get result"""
        expect:
        rsp.body() != []
        "小玉西瓜" in rsp.body().collect { frult -> frult.name}
        "我就西瓜" in rsp.body().collect { frult -> frult.name}
        "蟲咬過的" in rsp.body().collect { frult -> frult.name}
    }

    def "Get的monkeyTest"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/frult/5f4e0c340169526cbf7fcc67")

        HttpResponse<Frult> rsp = client.toBlocking().exchange(request,
                Argument.of(Frult))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "小玉西瓜"

    }
}
