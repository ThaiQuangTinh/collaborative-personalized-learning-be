package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.LearningPath;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UserOriginalPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LearningPathMapper {

    private final UserService userService;

    public LearningPathResponse toResponse(LearningPath lp) {
        if (lp == null) return null;

        LearningPathResponse res = new LearningPathResponse();
        res.setPathId(lp.getPathId());
        res.setTitle(lp.getTitle());
        res.setDescription(lp.getDescription());
        res.setStartTime(lp.getStartTime());
        res.setEndTime(lp.getEndTime());
        res.setProgressPercent(lp.getProgressPercent());
        res.setStatus(lp.getStatus());
        res.setArchived(lp.isArchived());
        res.setFavourite(lp.isFavourite());
        res.setDeleted(lp.isDeleted());
        res.setCreateAt(lp.getCreatedAt());

        if (lp.getOriginalOwner() != null) {
            res.setUserOriginalPathResponse(
                    new UserOriginalPathResponse(
                            lp.getOriginalOwner().getUserId(),
                            lp.getOriginalOwner().getFullname(),
                            userService.getAvatarUrlByUserId(lp.getOriginalOwner().getUserId()).getAvatarUrl()
                    )
            );
        } else {
            res.setUserOriginalPathResponse(null);
        }

        return res;
    }
}

