package ma.education.courseservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "exam_date")
    private LocalDateTime examDate;

    private Integer duration; // en minutes

    @Positive
    @Column(name = "max_score")
    private Double maxScore;

    private Double coefficient;
}