package rest.api2.controller

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters as Mfil
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.bson.Document
//TODO test 和 app 不該共用資源
import rest.api2.domain.Course
import spock.lang.Shared
import spock.lang.Specification


import javax.inject.Inject

//TODO generate test data auto matic amd clean
@MicronautTest
class CourseControllerTest extends Specification {
    @Inject
    @Client("/")
    RxStreamingHttpClient client
    @Shared
    MongoClient mongoClient
    @Shared
    MongoCollection<Document> courseCol
    @Shared
    HttpRequest request

    static final String uuid = UUID.randomUUID()

    def setupSpec() {
        //TODO hardcode has to solve block by read app.yml issue 791 https://github.com/micronaut-projects/micronaut-core/issues/791
        mongoClient = MongoClients.create "mongodb+srv://Tsung:d39105648@cluster0.dmjou.mongodb.net/Cluster0?retryWrites=true&w=majority"
        courseCol = mongoClient.getDatabase("Cluster0").getCollection("COURSE")
    }

    def cleanupSpec() {
        mongoClient.close()
    }

    def "List的猴子測試"() {
        request = HttpRequest.GET "http://localhost:8080/course"

        HttpResponse<List<Course>> rsp = client.toBlocking().exchange(request, Argument.listOf(Course))
        """use rsp.body to get result"""
        expect:
        rsp.complete == true
    }

    def "Get的monkeyTest"() {
        Document doc = new Document("_id", uuid)
                .append("name", "analysticGetApitest")
        courseCol.insertOne doc
        request = HttpRequest.GET "http://localhost:8080/course/$uuid"
        HttpResponse<Course> rsp = client.toBlocking().exchange(request, Argument.of(Course))
        courseCol.deleteOne Mfil.eq("_id", uuid)
        """use rsp.body to get result"""
        expect:
        rsp.body().name == "analysticGetApitest"
    }

//    TODO update 無 api 單元測試
//    def "Update的monkeyTest 打錯字了啦"() {
//        Document doc = new Document("_id", uuid)
//                .append("NAME", "msthClass")
//        courseCol.insertOne doc
//        request = HttpRequest.PUT("http://localhost:8080/course/$uuid/mathClass",String)
//        HttpResponse<Course> rsp = client.toBlocking().exchange(request, Argument.of(Course))
//        courseCol.deleteOne Mfil.eq("_id", uuid)
//        expect:
//        rsp.body().name == "mathClass"
//    }

    def "Delete 和 Save的monkeyTest"() {
        request = HttpRequest.POST("http://localhost:8080/course/forDELApiTest", String)

        HttpResponse<Course> rsp1 = client.toBlocking().exchange(request, Argument.of(Course))
        request = HttpRequest.DELETE("http://localhost:8080/course/" + rsp1.body().id, String)
        HttpResponse<Course> rsp2 = client.toBlocking().exchange(request, Argument.of(Course))
        expect:
        rsp1.body().name == "forDELApiTest"
    }

    def "insert update 整合測試"() {
        request = HttpRequest.POST("http://localhost:8080/course/msthClass", String)
        HttpResponse<Course> rsp_insert = client.toBlocking().exchange(request, Argument.of(Course))

        request = HttpRequest.PUT("http://localhost:8080/course/${rsp_insert.body().id}/mathClass", String)
        HttpResponse<Course> rsp_update = client.toBlocking().exchange(request, Argument.of(Course))

        courseCol.deleteOne Mfil.eq("_id", rsp_insert.body().id)
        expect:
        rsp_insert.body().name == "msthClass"
        rsp_update.body().name == "mathClass"
    }
}
