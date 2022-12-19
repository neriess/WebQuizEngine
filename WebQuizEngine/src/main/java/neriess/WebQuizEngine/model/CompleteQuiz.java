package neriess.WebQuizEngine.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;
import java.util.Date;

@Entity
public class CompleteQuiz {


    private long id; //quiz Id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long completeQuizId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CompleteQuiz(long id, User user) {
        this.id = id;
        this.user = user;
        this.completedAt = new Date();
    }

    public CompleteQuiz() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date creationDateTime) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
