package rest.api2.controller

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import rest.api2.domain.InnerRelation
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class InnerRelationControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client

    def "List的猴子測試"() {
        HttpRequest request = HttpRequest.GET("http://localhost:8080/innerRelation")

        HttpResponse<List<InnerRelation>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(InnerRelation))
        """use rsp.body to get result"""
        expect:
        rsp.body() != []
    }

    def "Get的monkeyTest"() {
        """get Adam"""
        HttpRequest request = HttpRequest.GET("http://localhost:8080/innerRelation/5f4871c36cad4e238db083ad")

        HttpResponse<InnerRelation> rsp = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "Adam"

    }

    def "getMate的monkeyTest"() {
        """get 亞當's wife"""
        HttpRequest request = HttpRequest.GET("http://localhost:8080/innerRelation/getMate/5f489e689bf9fa330fb3d828")

        HttpResponse<InnerRelation> rsp = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "夏娃"

    }


    def "Save 的monkeyTest"() {
        """前提：delete method沒問題"""
        HttpRequest request = HttpRequest.POST("http://localhost:8080/innerRelation/forpostApiTest",String)

        HttpResponse<InnerRelation> rsp1 = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        expect:
        rsp1.complete == true

        cleanup:
        HttpRequest.DELETE("http://localhost:8080/innerRelation/"+rsp1.body().id,String)

    }

    def "Update的monkeyTest"() {
        String newName = "forPutapiTest"+Math.random().toString()
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/innerRelation/5f4871cb6cad4e238db083ae/"+newName,String)

        HttpResponse<InnerRelation> rsp = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        expect:
        rsp.complete == true
    }

    def "Delete的monkeyTest"() {
        HttpRequest request = HttpRequest.POST("http://localhost:8080/innerRelation/forDELApiTest",String)

        HttpResponse<InnerRelation> rsp1 = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        request = HttpRequest.DELETE("http://localhost:8080/innerRelation/"+rsp1.body().id,String)
        HttpResponse<InnerRelation> rsp2 = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        expect:
        rsp2.complete == true

    }

    def "marry 的 monkeytest"(){
        HttpRequest request = HttpRequest.PUT("http://localhost:8080/innerRelation/marriage/5f487b266cad4e238db083b1/5f487b2f6cad4e238db083b2",String)

        HttpResponse<InnerRelation> rsp = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        expect:
        rsp.complete         == true
        rsp.body().mate_id   != null
        cleanup:
        HttpRequest.PUT("http://localhost:8080/innerRelation/divorce/5f487b266cad4e238db083b1/5f487b2f6cad4e238db083b2",String)
    }

    def "divorce 的 monkeytest"(){
        HttpRequest.PUT("http://localhost:8080/innerRelation/marriage/5f487b266cad4e238db083b1/5f487b2f6cad4e238db083b2",String)

        HttpRequest request = HttpRequest.PUT("http://localhost:8080/innerRelation/divorce/5f487b266cad4e238db083b1/5f487b2f6cad4e238db083b2",String)

        HttpResponse<InnerRelation> rsp = client.toBlocking().exchange(request,
                Argument.of(InnerRelation))
        expect:
        rsp.complete         == true
    }

}
