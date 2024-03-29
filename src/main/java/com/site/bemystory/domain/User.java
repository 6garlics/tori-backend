package com.site.bemystory.domain;

import com.site.bemystory.dto.FollowDTO;
import com.site.bemystory.dto.Profile;
import com.site.bemystory.dto.UserInfoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "user_name")
    private String userName;
    private String password;
    private String email;
    private String role;
    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    //프로필 사진
    private String profile;

    //팔로우
    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<Follow> followings;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<Follow> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Diary> diaries;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Book> books;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<Letter> from_letter;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<Letter> to_letter;

    @Builder
    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.profile="https://bemystory-s3-data.s3.ap-northeast-2.amazonaws.com/default_profile.png";
    }


    public UserInfoRequest toDTO(){
        return UserInfoRequest.builder()
                .userName(this.userName)
                .profileImg(this.profile)
                .build();
    }

    public Profile toProfile(){
        return Profile.builder()
                .userName(this.userName)
                .profile(this.profile)
                .build();
    }

    public FollowDTO toFollow(String friendStatus){
        return FollowDTO.builder()
                .userName(this.userName)
                .profileImg(this.profile)
                .friendStatus(friendStatus)
                .build();
    }
}
