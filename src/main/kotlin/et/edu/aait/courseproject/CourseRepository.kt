package et.edu.aait.courseproject

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourseRepository: JpaRepository<Course,Long> {
    fun findByCode(code:String): Optional<Course>
}