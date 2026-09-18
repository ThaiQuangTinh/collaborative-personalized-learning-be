package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostLikeId implements Serializable {

    @Column(name = "post_id", columnDefinition = "CHAR(36)")
    private String postId;

    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String userId;

}
