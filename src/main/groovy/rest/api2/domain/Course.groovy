package rest.api2.domain
import grails.gorm.annotation.Entity
import org.bson.types.ObjectId

/**
 * <p>Title: O/R Mapping(For Course)</p>
 * <p>Description: BaseDAO/SqlObj/ResultSetObj/</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: Free Open Everybody Use</p>
 * @author PA-ORM TableMining 
 * @desc Groovy ORM 1.0.3 (COURSE/課程)
 */
@Entity
class Course {
//	TODO asign id by self ref https://medium.com/swlh/dont-use-database-generated-ids-d703d35e9cc4
	String 		id
	String		name
//	TODo try Dynamic Attributes

	static mapping = {
		collection 'COURSE'
		database 'Cluster0'
		comment '課程'
//		version true
		//---1.COURSE_ID PK0
//		TODO Add indice index to the key property or it will be very slow ，and mongodb hint
			id 					generator: 'uuid', 			attr:"_id",		   index: true , indexAttributes: [unique:true, dropDCourseups:true]
			name				attr:"NAME",				comment:"課程名稱", ignoreNotFound: true
	}


	static constraints = {
		name				(nullable:false, blank: false)
	}
}

