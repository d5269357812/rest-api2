package rest.api2.controller

import grails.gorm.transactions.Transactional
import rest.api2.domain.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
// TODO find new usage replacement of JsonError
//import io.micronaut.http.hateos.JsonError
import io.reactivex.Single

@Controller("/course")
class CourseController {

	@Get("/")
	Single<HttpResponse<?>> list() {
		List courses
		Course.withNewSession { courses = Course.list() }

		Single.just(courses).map({ result ->
			HttpResponse.ok(result)
		})
//				.onErrorReturn({ throwable ->
//			new JsonError(throwable.message)
//		})
	}

	@Get("/{id}")
	Single<HttpResponse<?>> get(String id) {
		Course course
		Course.withNewSession { course = Course.get(id) }
		Single.just(course).map({ result ->
			HttpResponse.ok(result)
		})
//		onErrorReturn({ throwable ->
//			new JsonError(throwable.message)
//		})
	}

//	@Post("/")
//	Single<Course> save(Course course) {
//		Course.withNewSession { Single.just(Course.save(person)) }
//	}
//
//	@Put("/")
//	Single<Course> update(Course course) {
//		Course.withNewSession { Single.just(Course.save(person)) }
//	}
//
//	@Delete("/")
//	Single<HttpResponse<?>> delete(Course course) {
//		Course.withNewSession {
//			Single.just(course.delete()).map({ result ->
//				HttpResponse.ok(result)
//			}).onErrorReturn({ throwable ->
//				new JsonError(throwable.message)
//			})
//		}
//	}
}