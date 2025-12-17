package ma.education.courseservice.service;

import lombok.RequiredArgsConstructor;
import ma.education.courseservice.entity.Course;
import ma.education.courseservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    // Configuration personnalisée requise par le projet
    @Value("${mes-config-ms.cours-last:7}")
    private int coursLastDays;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours introuvable avec l'id : " + id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setCredits(courseDetails.getCredits());
        course.setTeacherName(courseDetails.getTeacherName());
        course.setDuration(courseDetails.getDuration());
        course.setStartDate(courseDetails.getStartDate());
        course.setEndDate(courseDetails.getEndDate());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    /**
     * Récupère les cours créés dans les N derniers jours
     * N est défini par la configuration mes-config-ms.cours-last
     */
    public List<Course> getRecentCourses() {
        LocalDate dateLimit = LocalDate.now().minusDays(coursLastDays);
        return courseRepository.findByCreatedAtAfter(dateLimit);
    }
}