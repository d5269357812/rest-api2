package rest.api2.service

import rest.api2.domain.Course
import grails.gorm.services.Service
import groovy.transform.CompileStatic

@Service(Course)
@CompileStatic
interface CourseService {

    Course get(Serializable id)

    List<Course> list(Map args)

    Long count()

    Course save(Course Course)
}