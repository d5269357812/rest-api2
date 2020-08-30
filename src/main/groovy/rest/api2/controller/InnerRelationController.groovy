package rest.api2.controller


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.hateoas.JsonError
import io.reactivex.Single
import rest.api2.domain.InnerRelation

@Controller("/innerRelation")
class InnerRelationController {

	private InnerRelation innerRelation

	@Get("/")
	Single<HttpResponse<?>> list() {
		List innerRelations
		innerRelations = InnerRelation.list()

		Single.just(innerRelations).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Get("/{id}")
	Single<HttpResponse<?>> get(String id) {
		innerRelation = InnerRelation.get(id)
		Single.just(innerRelation).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Get("/getMate/{id}")
	Single<HttpResponse<?>> getMate(String id) {
		innerRelation = InnerRelation.get(id)
		if (innerRelation?.mate_id == null) throw new Exception("The number ${innerRelation.name} is 單身")
		innerRelation = InnerRelation.get(innerRelation.mate_id)
		Single.just(innerRelation).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}


	@Post("/{name}")
	Single<InnerRelation> save(String name) {
		innerRelation = new InnerRelation(name: name)
		InnerRelation.withNewSession { Single.just( innerRelation.insert(flush:true) ) }
	}
//
	@Put("/{id}/{name}")
	Single<InnerRelation> update(String id,String name) {
		innerRelation = InnerRelation.get(id)
		innerRelation.name = name
		InnerRelation.withNewSession {
			innerRelation.validate()
			Single.just(innerRelation.save(flush:true))
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		innerRelation = InnerRelation.get(id)
		InnerRelation.withNewSession {
			innerRelation.delete(flush:true)
			HttpResponse.ok()
		}
	}

	@Put("/marriage/{id1}/{id2}")
	Single<InnerRelation> marriage(String id1,String id2) {
		InnerRelation bride = InnerRelation.get(id1);InnerRelation groom = InnerRelation.get(id2)
		//check is marriged before
		if(bride.mate_id || groom.mate_id ) throw new Exception("The bride or groom is marriged")
		bride.mate_id = groom.id;groom.mate_id = bride.id
		InnerRelation.withNewSession {
			bride.validate();groom.validate()
			groom.save()
			Single.just(bride.save(flush:true))
		}
	}

	@Put("/divorce/{id1}/{id2}")
	Single<InnerRelation> divorce(String id1,String id2) {
		InnerRelation bride = InnerRelation.get(id1);InnerRelation groom = InnerRelation.get(id2)
		//check is marriged before
		if(bride.mate_id != groom.id || groom.mate_id != bride.id) throw new Exception("他們沒有結婚")
		bride.mate_id = "";groom.mate_id = ""
		InnerRelation.withNewSession {
			bride.validate();groom.validate()
			groom.save()
			Single.just(bride.save(flush:true))
		}
	}


}