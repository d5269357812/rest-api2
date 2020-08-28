package rest.api2.controller.oneToTwo


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.hateoas.JsonError
import io.reactivex.Single
import rest.api2.domain.oneToTwo.Author
import rest.api2.domain.oneToTwo.Book

@Controller("/author")
class AuthorController {

	private Author author

	@Get("/")
	Single<HttpResponse<?>> list() {
		List authors
		authors = Author.list()

		Single.just(authors).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Get("/{id}")
	Single<HttpResponse<?>> get(String id) {
		author = Author.get(id)
		Single.just(author).map({ result ->
			HttpResponse.ok(result)
		}).onErrorReturn({ throwable ->
			new JsonError(throwable.message)
		})
	}

	@Post("/{name}")
	Single<Author> save(String name) {
		author = new Author(name: name)
		Author.withNewSession { Single.just( author.insert(flush:true) ) }
	}
//
	@Put("/{id}/{name}")
	Single<Author> update(String id,String name) {
		author = Author.get(id)
		author.name = name
		Author.withNewSession {
			author.validate()
			Single.just(author.save(flush:true))
		}
	}

	@Delete("/{id}")
	void delete(String id) {
		author = Author.get(id)
		Author.withNewSession {
			author.delete(flush:true)
			HttpResponse.ok()
		}
	}

	@Put("publish/{id}/{book1Id}/{book2Id}")
	Single<Author> publish(String id,String book1Id,String book2Id) {
		author = Author.get(id)
		if (author.bookFirstId || author.bookSecondId)  throw new Exception("The number ${author.name} is 出過書了")
		author.bookFirst = Book.get(book1Id)
		author.bookSecond = Book.get(book2Id)
		Author.withNewSession {
			author.validate()
			Single.just(author.save(flush:true))
		}
	}

	@Put("closeBook/{id}/{book1Id}/{book2Id}")
	void closeBook(String id,String book1Id,String book2Id) {
//		TODO this work around should solve
		author = Author.get(id)
		String b1Id,b2Id
		if (author.bookFirst)b1Id = author.bookFirst.id
		if (author.bookSecond)b1Id = author.bookFirst.id
		if (author.bookFirstId)b2Id = author.bookFirstId
		if (author.bookSecondId)b2Id = author.bookSecondId

		if ( b1Id != book1Id || b2Id != book2Id)  throw new Exception("他沒有出這兩本書")
		author.bookFirstId 		= ""
		author.bookSecondId 	= ""
		Author.withNewSession {
			author.validate()
//			TODO can not convert to json?
			author.save(flush:true)
		}
	}


}