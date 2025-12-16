package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Tag;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.User;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateTagRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateTagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.TagRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.UserRepository;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.TagService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ColorUtil;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateTagResponse createTag(String userId, CreateTagRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (tagRepository.existsByUser_UserIdAndTagName(userId, request.getTagName())) {
            throw new AppException(ErrorCode.TAG_NAME_ALREADY_EXISTS);
        }

        if (!ColorUtil.isValidHexColor(request.getTextColor())) {
            throw new AppException(ErrorCode.INVALID_TAG_COLOR);
        }

        if (tagRepository.existsByTextColor(request.getTextColor())) {
            throw new AppException(ErrorCode.TAG_COLOR_ALREADY_EXISTS);
        }

        Tag tag = new Tag();
        tag.setTagId(UUIDUtil.generate());
        tag.setUser(user);
        tag.setTagName(request.getTagName());
        tag.setTextColor(request.getTextColor());
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());

        tagRepository.save(tag);

        return new CreateTagResponse(tag.getTagId(), tag.getTagName(), tag.getTextColor());
    }

    @Override
    @Transactional
    public UpdateTagResponse updateTag(String tagId, UpdateTagRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));

        if (!ColorUtil.isValidHexColor(request.getTextColor())) {
            throw new AppException(ErrorCode.INVALID_TAG_COLOR);
        }

        tag.setTagName(request.getTagName());
        tag.setTextColor(request.getTextColor());
        tag.setUpdatedAt(LocalDateTime.now());

        tagRepository.save(tag);

        return new UpdateTagResponse(tag.getTagId(), tag.getTagName(), tag.getTextColor());
    }

    @Override
    @Transactional
    public void deleteTag(String tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));

        tagRepository.delete(tag);
    }

    @Override
    public List<TagResponse> getAllTagsByUser(String userId) {
        List<Tag> tags = tagRepository.findAllByUser_UserId(userId);

        List<TagResponse> tagResponses = new ArrayList<>();

        for (Tag tag : tags) {
            TagResponse tagResponse = new TagResponse(
                    tag.getTagId(), tag.getTagName(), tag.getTextColor()
            );

            tagResponses.add(tagResponse);
        }

        return tagResponses;
    }

}
