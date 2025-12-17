package ma.education.courseservice.service;

import lombok.RequiredArgsConstructor;
import ma.education.courseservice.entity.Grade;
import ma.education.courseservice.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));
    }

    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesByExam(Long examId) {
        return gradeRepository.findByExamId(examId);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}