package com.site.bemystory.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User from_user;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User to_user;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;
}
