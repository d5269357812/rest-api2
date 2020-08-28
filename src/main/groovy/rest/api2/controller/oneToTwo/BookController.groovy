package rest.api2.controller.oneToTwo


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.hateoas.JsonError
import io.reactivex.Single
import rest.api2.domain.oneToTwo.Book

@Controller("/book")
class BookController {

	private Book book

	@Get("/")
	Single<HttpResponse<?>> list() {
		List books
		books = Book.list()

		Single.just(books).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Get("/{id}")
	Single<HttpResponse<?>> get(String id) {
		book = Book.get(id)
		Single.just(book).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Post("/{title}")
	Single<Book> save(String title) {
		book = new Book(title: title)
		Book.withNewSession { Single.just( book.insert(flush:true) ) }
	}
//
	@Put("/{id}/{title}")
	Single<Book> update(String id,String title) {
		book = Book.get(id)
		book.title = title
		Book.withNewSession {
			book.validate()
			Single.just(book.save(flush:true))
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		book = Book.get(id)
		Book.withNewSession {
			book.delete(flush:true)
			HttpResponse.ok()
		}
	}
}