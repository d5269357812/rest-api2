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
class Book {
	String 		id
	String		title
	Date		publishDate

	static mapping = {
		collection 'BOOK'
		database 'oneToTwo'
		comment '書本'

			id 					generator: 'uuid', 			attr:"_id",		   index: true , indexAttributes: [unique:true, dropDCourseups:true]
			title				attr:"TITLE",				comment:"課程名稱", ignoreNotFound: true
			publishDate			attr:"PUBLISH_DATE",		comment:"出版日期", ignoreNotFound: true
	}


	static constraints = {
		title				(nullable:false, blank: false)
		publishDate			(nullable:true)
	}
}

