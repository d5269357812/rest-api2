package rest.api2.controller

import rest.api2.domain.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
//import io.micronaut.http.hateos.JsonError
import io.reactivex.Single

@Controller("/tenColumn")
class TenColumnController {

    private TenColumn tenColumn

    @Get("/")
    Single<HttpResponse<?>> list() {
        List tenColumns
        tenColumns = TenColumn.list()

        Single.just(tenColumns).map({ result ->
            HttpResponse.ok(result)
        })
//				.onErrorReturn({ throwable ->
//			new JsonError(throwable.message)
//		})
    }

    @Get("/{id}")
    Single<HttpResponse<?>> get(String id) {
        tenColumn = TenColumn.get(id)
        Single.just(tenColumn).map({ result ->
            HttpResponse.ok(result)
        })
//		onErrorReturn({ throwable ->
//			new JsonError(throwable.message)
//		})
    }

    @Post("/{c1}/{c2}/{c3}")
    Single<TenColumn> save(String c1,String c2,String c3) {
        tenColumn = new TenColumn(c1: c1,c2: c2,c3: c3)
        TenColumn.withNewSession { Single.just( tenColumn.insert(flush:true) ) }
    }
//
    @Put("/{id}/{c1}/{c2}/{c3}")
    Single<TenColumn> update(String id,String c1,String c2,String c3) {
        tenColumn = TenColumn.get(id)
        tenColumn.c1 = c1;
        tenColumn.c2 = c2;
        tenColumn.c3 = c3
        TenColumn.withNewSession {
            tenColumn.validate()
            Single.just(tenColumn.save(flush:true))
        }
    }

    @Delete("/{id}")
    Single<HttpResponse<?>> delete(String id) {
        tenColumn = TenColumn.get(id)
        TenColumn.withNewSession {
            Single.just(tenColumn.delete(flush: true)).map({ result ->
                HttpResponse.ok(result)
            })
//					.onErrorReturn({ throwable ->
//				new JsonError(throwable.message)
//			})
        }
    }
}