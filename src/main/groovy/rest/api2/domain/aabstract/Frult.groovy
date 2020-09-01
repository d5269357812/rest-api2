package rest.api2.domain.aabstract
import grails.gorm.annotation.Entity

@Entity
class Frult {
	String 		id
	String		name

	static mapping = {
		collection 'FRULT'
		database 'Cluster0'
		comment '課程'

			id 					generator: 'uuid', 			attr:"_id",		   index: true , indexAttributes: [unique:true, dropDCourseups:true]
			name				attr:"NAME",				comment:"課程名稱", ignoreNotFound: true
	}


	static constraints = {
		name				(nullable:false, blank: false)
	}
}

