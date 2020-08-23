package rest.api2.domain
import grails.gorm.annotation.Entity
import org.bson.types.ObjectId

@Entity
class TenColumn {
	ObjectId	id
	String		c1
	String		c2
	String		c3
	String		c4
	String		c5
	String		c6
	String		c7
	String		c8
	String		c9
	String		c10

	static mapping = {
		collection 'TEN_COLUMN'
		database 'Cluster0'
		comment """表單填寫api（10ㄍ欄位） jira https://tsunglife.atlassian.net/browse/SFIM-63?atlOrigin=eyJpIjoiZDFkYzQ1OWIwY2M2NDM2Mjg2MTVmMDZkZjUwNjM5YTAiLCJwIjoiaiJ9"""
		//---1.COURSE_ID PK0
			c1				attr:"C1"
			c2				attr:"C2"
			c3				attr:"C3"
			c4				attr:"C4"
			c5				attr:"C5"
			c6				attr:"C6"
			c7				attr:"C7"
			c8				attr:"C8"
			c9				attr:"C9"
			c10				attr:"C10"
	}


	static constraints = {
		c1				(nullable:true, blank: true)
		c2				(nullable:true, blank: true)
		c3				(nullable:true, blank: true)
		c4				(nullable:true, blank: true)
		c5				(nullable:true, blank: true)
		c6				(nullable:true, blank: true)
		c7				(nullable:true, blank: true)
		c8				(nullable:true, blank: true)
		c9				(nullable:true, blank: true)
		c10				(nullable:true, blank: true)
	}
}

