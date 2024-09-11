package com.pengyu.magnet.domain.assessment;

import com.pengyu.magnet.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Test Invitation domain
 */
@Data
@Entity
@Table(name = "assessment_test_invitation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "invitation_user_id_fk")
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "paper_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "invitation_paper_id_fk")
    )
    private TestPaper testPaper;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING,
        FINISHED
    }
}
