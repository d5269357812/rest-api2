package rest.api2.controller.oneToTwo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters as Mfil
import com.mongodb.client.model.Updates as Mupd

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
		if (author.bookFirst || author.bookSecond)  throw new Exception("The number ${author.name} is 出過書了")
		author.bookFirst = Book.get(book1Id);author.bookSecond = Book.get(book2Id)
		Author.withNewSession {
			author.validate()
			Single.just(author.save(flush:true))
		}
	}

	@Put("closeBook/{id}/{book1Id}/{book2Id}")
	void closeBook(id, book1Id, book2Id) {
		author = Author.get id
		if (author.bookFirst.id != book1Id || author.bookSecond.id != book2Id) throw new Exception("他沒有出這兩本書")
		//TODO hardcode has to solve block by read app.yml issue 791 https://github.com/micronaut-projects/micronaut-core/issues/791
		MongoClient mongoClient = MongoClients.create "mongodb+srv://Tsung:d39105648@cluster0.dmjou.mongodb.net/Cluster0?retryWrites=true&w=majority"
		mongoClient.getDatabase("oneToTwo").getCollection("AUTHOR").updateOne(Mfil.eq("_id", id), Mupd.combine(Mupd.unset("BOOK_FIRST"), Mupd.unset("BOOK_SECOND")))
		//before do
		mongoClient.close()
	}


}