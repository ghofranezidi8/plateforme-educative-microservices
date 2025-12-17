package ma.education.courseservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "exam_id")
    private Long examId;

    private Double score;

    @Column(name = "graded_date")
    private LocalDate gradedDate;

    private String feedback;

    @PrePersist
    protected void onCreate() {
        gradedDate = LocalDate.now();
    }
}