package rest.api2.domain.oneToTwo

import grails.gorm.annotation.Entity

/**
 * <p>Title: O/R Mapping(For Course)</p>
 * <p>Description: BaseDAO/SqlObj/ResultSetObj/</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: Free Open Everybody Use</p>
 * @author PA-ORM TableMining 
 * @desc Groovy ORM 1.0.3 (COURSE/課程)
 */
@Entity
class Author {
	String 		id
	String		name
	Book		bookFirst
	String		bookFirstId
	Book		bookSecond
	String		bookSecondId

	static mapping = {
		collection 	 'AUTHOR'
		database	 'oneToTwo'
		comment 	 '作家'
			id 					generator: 'uuid', 			attr:"_id",		   index: true , indexAttributes: [unique:true, dropDCourseups:true] ,ignoreNotFound: true
			name					attr:"NAME",			comment:"課程名稱", ignoreNotFound: true
//		TODO lookup 只會找到 Book or BookId 其中一個
			bookFirst				attr:"BOOK_FIRST",		comment:"首本書", ignoreNotFound: true
			bookFirstId				attr:"BOOK_FIRST",		comment:"首本書ID", ignoreNotFound: true
			bookSecond				attr:"BOOK_SECOND",		comment:"第二書", ignoreNotFound: true
			bookSecondId			attr:"BOOK_SECOND",		comment:"第二書ID", ignoreNotFound: true
	}


	static constraints = {
		name					(nullable:false, blank: false)
		bookFirst				(nullable:true, blank: true)
		bookFirstId				(nullable:true, blank: true)
		bookSecond				(nullable:true, blank: true)
		bookSecondId			(nullable:true, blank: true)
	}
}

