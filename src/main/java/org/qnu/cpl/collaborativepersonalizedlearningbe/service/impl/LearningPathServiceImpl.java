package org.qnu.cpl.collaborativepersonalizedlearningbe.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.config.CorsProperties;
import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.ErrorCode;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.LearningStatus;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.SharePermission;
import org.qnu.cpl.collaborativepersonalizedlearningbe.exception.AppException;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.LearningPathMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.NoteMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.TagMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.mapper.TopicMapper;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.repository.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.LearningPathService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LearningPathServiceImpl implements LearningPathService {

    private final LearningPathRepository learningPathRepository;

    private final LearningPathTagRepository learningPathTagRepository;

    private final TagRepository tagRepository;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private final NoteRepository noteRepository;

    private final ShareLearningPathRepository shareLearningPathRepository;

    private final LessonRepository lessonRepository;

    private final ResourceRepository resourceRepository;

    private final ProgressRepository progressRepository;

    private final CorsProperties corsProperties;

    private final LearningPathMapper learningPathMapper;

    @Override
    @Transactional
    public LearningPathResponse createLearningPath(String userId, CreateLearningPathRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (learningPathRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.LEARNING_PATH_TITLE_ALREADY_EXISTS);
        }

        LearningPath learningPath = new LearningPath();
        learningPath.setPathId(UUIDUtil.generate());
        learningPath.setUser(user);
        learningPath.setTitle(request.getTitle());
        learningPath.setDescription(request.getDescription());
        learningPath.setStatus(LearningStatus.NOT_STARTED);
        learningPath.setProgressPercent(0);
        learningPath.setFavourite(false);
        learningPath.setArchived(false);
        learningPath.setDeleted(false);
        learningPath.setStartTime(LocalDateTime.now());
        learningPath.setEndTime(LocalDateTime.now());
        learningPath.setCreatedAt(LocalDateTime.now());
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);

        return new LearningPathResponse(
                learningPath.getPathId(),
                learningPath.getTitle(),
                learningPath.getDescription(),
                learningPath.getStartTime(),
                learningPath.getEndTime(),
                learningPath.getStatus(),
                learningPath.getProgressPercent(),
                learningPath.isFavourite(),
                learningPath.isArchived(),
                learningPath.isDeleted(),
                learningPath.getCreatedAt(),
                null
        );
    }

    @Override
    @Transactional
    public UpdateLearningPathResponse updateLearningPath(String pathId, UpdateLearningPathRequest request) {
        LearningPath learningPath = learningPathRepository.findById(pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

//        if (learningPathRepository.existsByTitle(request.getTitle())) {
//            throw new AppException(ErrorCode.LEARNING_PATH_TITLE_ALREADY_EXISTS);
//        }

        learningPath.setTitle(request.getTitle());
        learningPath.setDescription(request.getDescription());
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);

        return new UpdateLearningPathResponse(
                learningPath.getPathId(),
                learningPath.getTitle(),
                learningPath.getDescription()
        );
    }

    @Override
    @Transactional
    public void deleteLearningPaths(DeleteLearningPathRequest request) {
        List<LearningPath> learningPaths = learningPathRepository.findAllById(request.getPathIds());

        if (learningPaths.isEmpty()) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();

//        for (LearningPath path : learningPaths) {
//            path.setDeleted(true);
//            path.setUpdatedAt(now);
//        }

//        learningPathRepository.saveAll(learningPaths);

        learningPathRepository.deleteAll(learningPaths);
    }


    @Override
    @Transactional
    public void addFavoriteByPathId(String userId, String pathId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        LearningPath learningPath = learningPathRepository.findByUser_UserIdAndPathIdAndIsDeletedFalse(userId, pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        learningPath.setFavourite(true);
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);
    }

    @Override
    @Transactional
    public void deleteFavoriteByPathId(String userId, String pathId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        LearningPath learningPath = learningPathRepository.findByUser_UserIdAndPathIdAndIsDeletedFalse(userId, pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        learningPath.setFavourite(false);
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);
    }

    @Override
    @Transactional
    public void addArchiveByPathId(String userId, String pathId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        LearningPath learningPath = learningPathRepository.findByUser_UserIdAndPathIdAndIsDeletedFalse(userId, pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        learningPath.setArchived(true);
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);
    }

    @Override
    @Transactional
    public void deleteArchiveByPathId(String userId, String pathId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        LearningPath learningPath = learningPathRepository.findByUser_UserIdAndPathIdAndIsDeletedFalse(userId, pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        learningPath.setArchived(false);
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);
    }

    @Override
    public List<TopicResponse> getTopicsByPathId(String pathId) {
        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        List<Topic> topics = topicRepository.findAllByLearningPath_PathId(pathId);

        return topics.stream().map(TopicMapper::toResponse).toList();
    }

    @Override
    public List<TagResponse> getTagsByLearningPathId(String pathId) {
        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        List<LearningPathTag> learningPathTagList =
                learningPathTagRepository.findAllByLearningPath_PathId(pathId);

        List<String> tagIdList = new ArrayList<>();

        for (LearningPathTag lpt : learningPathTagList) {
            tagIdList.add(lpt.getTag().getTagId());
        }

        List<Tag> tags = new ArrayList<>();
        for (String tagId : tagIdList) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));

            tags.add(tag);
        }

        return tags.stream().map(TagMapper::toResponse).toList();
    }

    @Override
    public List<LearningPathResponse> getAllLearningPathsByUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return learningPathRepository
                .findAllByUser_UserIdAndIsArchivedFalseAndIsDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(learningPathMapper::toResponse)
                .toList();
    }

    @Override
    public List<LearningPathResponse> getFavoriteLearningPaths(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return learningPathRepository.findAllByUser_UserIdAndIsFavouriteTrueAndIsDeletedFalseAndIsArchivedFalse(userId)
                .stream()
                .map(learningPathMapper::toResponse)
                .toList();
    }

    @Override
    public List<LearningPathResponse> getArchivedLearningPaths(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return learningPathRepository.findAllByUser_UserIdAndIsArchivedTrueAndIsDeletedFalse(userId)
                .stream()
                .map(learningPathMapper::toResponse)
                .toList();
    }

    @Override
    public LearningPathResponse getLearningPathByPathId(String pathId) {
        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        LearningPath learningPath = learningPathRepository.findByPathIdAndIsDeletedFalse(pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        return new LearningPathResponse(
                learningPath.getPathId(),
                learningPath.getTitle(),
                learningPath.getDescription(),
                learningPath.getStartTime(),
                learningPath.getEndTime(),
                learningPath.getStatus(),
                learningPath.getProgressPercent(),
                learningPath.isArchived(),
                learningPath.isFavourite(),
                learningPath.isDeleted(),
                learningPath.getCreatedAt(),
                null
        );
    }

    @Override
    public List<NoteResponse> getNotesByLearningPathId(String pathId) {
        if (!learningPathRepository.existsById(pathId)) {
            throw new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND);
        }

        List<Note> notes = noteRepository.findAllByTargetId(pathId);

        return notes.stream().map(NoteMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public CreateShareLearningPathResponse shareLearningPath(
            String userId,
            String pathId,
            CreateShareLearningPathRequest request
    ) {

        // 1. Check user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 2. Check LP
        LearningPath learningPath = learningPathRepository.findById(pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        // 3. Kiểm tra LearningPath đã từng share chưa
        Optional<ShareLearningPath> existingShareOpt =
                shareLearningPathRepository.findByLearningPath_PathId(pathId);

        String uiHost = corsProperties.getAllowedOrigins().get(0);

        // ------------ CASE 1: ĐÃ SHARE RỒI → TRẢ LẠI LINK CŨ ------------
        if (existingShareOpt.isPresent()) {
            ShareLearningPath existing = existingShareOpt.get();
            existing.setSharePermission(request.getSharePermission());
            existing.setUpdatedAt(LocalDateTime.now());

            shareLearningPathRepository.save(existing);

            String existingUrl =
                    uiHost + "/learning-path/" + existing.getShareToken() + "/share" + "?perm=" + existing.getSharePermission();

            return CreateShareLearningPathResponse.builder()
                    .token(existing.getShareToken())
                    .shareUrl(existingUrl)
                    .sharePermission(existing.getSharePermission())
                    .build();
        }

        // ------------ CASE 2: CHƯA SHARE → TẠO MỚI ------------
        String newToken = UUIDUtil.generate();
        String newUrl =
                "http://localhost:3000/learning-path/" + newToken + "/share" + "?perm=" + request.getSharePermission();

        ShareLearningPath newShare = new ShareLearningPath();
        newShare.setShareId(UUIDUtil.generate());
        newShare.setSharedByUser(user);
        newShare.setLearningPath(learningPath);
        newShare.setShareToken(newToken);
        newShare.setIsPublic(true);

        // set permission
        SharePermission permission = request.getSharePermission();
        newShare.setSharePermission(permission);
        newShare.setCreatedAt(LocalDateTime.now());
        newShare.setUpdatedAt(LocalDateTime.now());

        shareLearningPathRepository.save(newShare);

        return CreateShareLearningPathResponse.builder()
                .token(newToken)
                .shareUrl(newUrl)
                .sharePermission(permission)
                .build();
    }

    @Override
    public ShareLearningPathResponse getLearningPathByShare(String token) {
        ShareLearningPath shareLearningPath = shareLearningPathRepository
                .findByShareToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.ACCESS_DENIED));

        return new ShareLearningPathResponse(
                shareLearningPath.getSharedByUser().getUserId(),
                shareLearningPath.getLearningPath().getPathId(),
                shareLearningPath.getSharePermission()
        );
    }

    @Override
    @Transactional
    public LearningPathResponse cloneLearningPathForUser(String userId, String token) {
        ShareLearningPath share = shareLearningPathRepository
                .findByShareToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.ACCESS_DENIED));

        if (share.getSharePermission() != SharePermission.CLONE) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (learningPathRepository.existsByUser_UserIdAndOriginalPathId(userId, share.getLearningPath().getPathId())) {
            throw new AppException(ErrorCode.LEARNING_PATH_ALREADY_EXISTS);
        }

        // Clone learning path
        LearningPath original = share.getLearningPath();
        LearningPath learningPathCloned = original.clone(user);

        learningPathRepository.save(learningPathCloned);

        // Clone topics
        for (Topic topic : original.getTopics()) {
            Topic clonedTopic = topic.clone(learningPathCloned);
            clonedTopic.setLearningPath(learningPathCloned);

            topicRepository.save(clonedTopic);

            // Clone lessons
            for (Lesson lesson : topic.getLessons()) {
                Lesson clonedLesson = lesson.clone(clonedTopic);
                clonedLesson.setTopic(clonedTopic);

                lessonRepository.save(clonedLesson);

                // Clone resources
                for (Resource res : lesson.getResources()) {
                    Resource clonedRes = res.clone(clonedLesson, user);
                    clonedRes.setLesson(clonedLesson);

                    resourceRepository.save(clonedRes);
                }
            }
        }

        return new LearningPathResponse(
                learningPathCloned.getPathId(),
                learningPathCloned.getTitle(),
                learningPathCloned.getDescription(),
                learningPathCloned.getStartTime(),
                learningPathCloned.getEndTime(),
                learningPathCloned.getStatus(),
                learningPathCloned.getProgressPercent(),
                learningPathCloned.isFavourite(),
                learningPathCloned.isArchived(),
                learningPathCloned.isDeleted(),
                learningPathCloned.getCreatedAt(),
                null
        );
    }

    @Override
    public List<LearningPathExportResponse> exportLearningPaths(ExportLearningPathRequest request) {
        List<LearningPathExportResponse> result = new ArrayList<>();

        for (String pathId : request.getPathIds()) {
            LearningPath learningPath = learningPathRepository.findById(pathId)
                    .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

            LearningPathExportResponse pathExport = new LearningPathExportResponse();
            pathExport.setTitle(learningPath.getTitle());
            pathExport.setDescription(learningPath.getDescription());

            List<TopicExportResponse> topicExports = new ArrayList<>();

            // Sắp xếp topic theo displayIndex
            List<Topic> sortedTopics = new ArrayList<>(learningPath.getTopics());
            sortedTopics.sort(Comparator.comparingInt(Topic::getDisplayIndex));

            for (Topic topic : sortedTopics) {
                TopicExportResponse topicExport = new TopicExportResponse();
                topicExport.setTitle(topic.getTitle());
                topicExport.setDisplayIndex(topic.getDisplayIndex());

                List<LessonExportResponse> lessonExports = new ArrayList<>();

                // Sắp xếp lesson theo displayIndex
                List<Lesson> sortedLessons = new ArrayList<>(topic.getLessons());
                sortedLessons.sort(Comparator.comparingInt(Lesson::getDisplayIndex));

                for (Lesson lesson : sortedLessons) {
                    LessonExportResponse lessonExport = new LessonExportResponse();
                    lessonExport.setTitle(lesson.getTitle());
                    lessonExport.setDisplayIndex(lesson.getDisplayIndex());

                    List<ResourceExportResponse> resourceExports = new ArrayList<>();

                    for (Resource resource : lesson.getResources()) {
                        ResourceExportResponse resourceExport = new ResourceExportResponse();
                        resourceExport.setResourceId(resource.getResourceId());
                        resourceExport.setName(resource.getName());
                        resourceExport.setOriginalResourceId(resource.getOriginalResourceId());
                        resourceExport.setExternalLink(resource.getExternalLink());
                        resourceExport.setType(resource.getType());
                        resourceExport.setMimeType(resource.getMimeType());
                        resourceExport.setObjectName(resource.getObjectName());
                        resourceExport.setSizeBytes(resource.getSizeBytes());

                        resourceExports.add(resourceExport);
                    }

                    lessonExport.setResources(resourceExports);
                    lessonExports.add(lessonExport);
                }

                topicExport.setLessons(lessonExports);
                topicExports.add(topicExport);
            }

            pathExport.setTopics(topicExports);
            result.add(pathExport);
        }

        return result;
    }

    @Transactional
    @Override
    public List<LearningPathResponse> importLearningPaths(String userId, List<LearningPathImportRequest> requests) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<LearningPath> result = new ArrayList<>();

        for (LearningPathImportRequest request : requests) {

            LearningPath learningPath = new LearningPath();
            learningPath.setPathId(UUIDUtil.generate());
            learningPath.setUser(user);
            learningPath.setTitle(request.getTitle());
            learningPath.setDescription(request.getDescription());
            learningPath.setCreatedAt(LocalDateTime.now());
            learningPath.setUpdatedAt(LocalDateTime.now());
            learningPath.setStatus(LearningStatus.NOT_STARTED);
            learningPath.setProgressPercent(0);
            learningPath.setArchived(false);
            learningPath.setFavourite(false);
            learningPath.setDeleted(false);

            List<Topic> topics = new ArrayList<>();

            for (TopicImportRequest topicReq : request.getTopics()) {

                Topic topic = new Topic();
                topic.setTopicId(UUIDUtil.generate());
                topic.setLearningPath(learningPath);
                topic.setTitle(topicReq.getTitle());
                topic.setDisplayIndex(topicReq.getDisplayIndex());
                topic.setStatus(LearningStatus.NOT_STARTED);
                topic.setCreatedAt(LocalDateTime.now());
                topic.setUpdatedAt(LocalDateTime.now());

                List<Lesson> lessons = new ArrayList<>();

                for (LessonImportRequest lessonReq : topicReq.getLessons()) {

                    Lesson lesson = new Lesson();
                    lesson.setLessonId(UUIDUtil.generate());
                    lesson.setTopic(topic);
                    lesson.setTitle(lessonReq.getTitle());
                    lesson.setDisplayIndex(lessonReq.getDisplayIndex());
                    lesson.setStatus(LearningStatus.NOT_STARTED);
                    lesson.setCreatedAt(LocalDateTime.now());
                    lesson.setUpdatedAt(LocalDateTime.now());

                    List<Resource> resources = new ArrayList<>();

                    for (ResourceImportRequest resReq : lessonReq.getResources()) {

                        Resource resource = new Resource();
                        resource.setResourceId(UUIDUtil.generate());
                        resource.setOriginalResourceId(resReq.getResourceId());
                        resource.setLesson(lesson);
                        resource.setUser(user);
                        resource.setName(resReq.getName());
                        resource.setType(resReq.getType());
                        resource.setObjectName(resReq.getObjectName());
                        resource.setExternalLink(resReq.getExternalLink());
                        resource.setSizeBytes(resReq.getSizeBytes());
                        resource.setMimeType(resReq.getMimeType());
                        resource.setOriginalResourceId(resReq.getOriginalResourceId());
                        resource.setCreatedAt(LocalDateTime.now());
                        resource.setUpdatedAt(LocalDateTime.now());

                        resources.add(resource);
                    }

                    lesson.setResources(resources);
                    lessons.add(lesson);
                }

                topic.setLessons(lessons);
                topics.add(topic);
            }

            learningPath.setTopics(topics);

            // Lưu từng path
            learningPathRepository.save(learningPath);

            // Add vào list trả về
            result.add(learningPath);
        }

        return result.stream().map(learningPathMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public List<LearningPathResponse> importJsonLearningPath(String userId, ImportLearningPathJsonRequest request) {
        List<LearningPathResponse> learningPaths = new ArrayList<>();

        try {
            // Đọc file JSON -> List<LearningPathImportRequest>
            ObjectMapper mapper = new ObjectMapper();
            List<LearningPathImportRequest> requests = mapper.readValue(
                    request.getFile().getInputStream(),
                    new TypeReference<List<LearningPathImportRequest>>() {
                    }
            );

            learningPaths = importLearningPaths(userId, requests);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_PARSE_ERROR);
        }

        return learningPaths;
    }

    private LearningPathStatisticResponse buildLearningPathStatistic(
            User user,
            LearningPath learningPath
    ) {

        LearningPathStatisticResponse res = new LearningPathStatisticResponse();
        res.setPathId(learningPath.getPathId());
        res.setPathTitle(learningPath.getTitle());

        // 1. Tổng số chủ đề
        List<Topic> topics = learningPath.getTopics();
        res.setTotalTopics(topics.size());

        // 2. Tổng số bài học
        List<Lesson> lessons = topics.stream()
                .flatMap(t -> t.getLessons().stream())
                .toList();

        int totalLessons = lessons.size();
        res.setTotalLessons(totalLessons);

        // 3. Progress theo user
        List<Progress> progresses = lessons.isEmpty()
                ? List.of()
                : progressRepository.findAllByUserAndLessonIn(user, lessons);

        int completedLessons = (int) progresses.stream()
                .filter(p -> p.getStatus() == LearningStatus.COMPLETED)
                .count();

        res.setCompletedLessons(completedLessons);
        res.setRemainingLessons(totalLessons - completedLessons);

        // 4. Tiến độ %
        int progressPercent = totalLessons == 0
                ? 0
                : (completedLessons * 100) / totalLessons;

        res.setOverallProgress(progressPercent);

        // 5. Thời gian học
        LocalDateTime start = learningPath.getStartTime();
        LocalDateTime end = learningPath.getEndTime();

        if (start != null && end != null) {
            LocalDate startDate = start.toLocalDate();
            LocalDate endDate = end.toLocalDate();

            Period period = Period.between(startDate, endDate);

            res.setDurationMonths((long) period.getMonths());
            res.setDurationDays((long) period.getDays());

            res.setStartDate(start.toString());
            res.setEndDate(end.toString());
        }

        // 6. Ngày cập nhật gần nhất
        LocalDateTime lastUpdate = progresses.stream()
                .map(Progress::getUpdatedAt)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(learningPath.getUpdatedAt());

        res.setLastUpdated(lastUpdate != null ? lastUpdate.toString() : null);

        return res;
    }

//    @Transactional
//    @Override
//    public LearningPathStatisticResponse getLearningPathStatistics(String userId, String pathId) {
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        LearningPath learningPath = learningPathRepository.findById(pathId)
//                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));
//
//        LearningPathStatisticResponse res = new LearningPathStatisticResponse();
//
//        // 1. Tổng số chủ đề
//        List<Topic> topics = learningPath.getTopics();
//        res.setTotalTopics(topics.size());
//
//        // 2. Tổng số bài học
//        List<Lesson> lessons = topics.stream()
//                .flatMap(t -> t.getLessons().stream())
//                .toList();
//
//        int totalLessons = lessons.size();
//        res.setTotalLessons(totalLessons);
//
//        // 3. Lấy progress theo user & list lesson
//        List<Progress> progresses = progressRepository.findAllByUserAndLessonIn(user, lessons);
//
//        int completedLessons = (int) progresses.stream()
//                .filter(p -> p.getStatus() == LearningStatus.COMPLETED)
//                .count();
//
//        res.setCompletedLessons(completedLessons);
//        res.setRemainingLessons(totalLessons - completedLessons);
//
//        // 4. Tiến độ tổng thể %
//        int progressPercent = totalLessons == 0 ? 0 : (completedLessons * 100) / totalLessons;
//        res.setOverallProgress(progressPercent);
//
//        // 5. Tính thời gian học: start → end
//        LocalDateTime start = learningPath.getStartTime();
//        LocalDateTime end = learningPath.getEndTime();
//
//        if (start != null && end != null) {
//            // Chỉ lấy phần LocalDate để tính tháng/ngày
//            LocalDate startDate = start.toLocalDate();
//            LocalDate endDate = end.toLocalDate();
//
//            Period period = Period.between(startDate, endDate);
//
//            res.setDurationMonths((long) period.getMonths());
//            res.setDurationDays((long) period.getDays());
//
//            res.setStartDate(start.toString());
//            res.setEndDate(end.toString());
//        }
//
//        // 6. Lấy ngày cập nhật gần nhất
//        LocalDateTime lastUpdate = progresses.stream()
//                .map(Progress::getUpdatedAt)
//                .filter(Objects::nonNull)
//                .max(LocalDateTime::compareTo)
//                .orElse(learningPath.getUpdatedAt());
//
//        res.setLastUpdated(lastUpdate != null ? lastUpdate.toString() : null);
//
//        return res;
//    }

    @Transactional(readOnly = true)
    @Override
    public LearningPathStatisticResponse getLearningPathStatistics(String userId, String pathId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        LearningPath learningPath = learningPathRepository.findById(pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        return buildLearningPathStatistic(user, learningPath);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LearningPathStatisticResponse> getAllLearningPathStatistics(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<LearningPath> learningPaths =
                learningPathRepository.findAllByUserAndIsArchivedFalseAndIsDeletedFalse(user);

        return learningPaths.stream()
                .map(path -> buildLearningPathStatistic(user, path))
                .toList();
    }


    @Override
    @Transactional
    public void updateProgressPercent(String pathId, UpdateProgressPercentRequest request) {
        LearningPath learningPath = learningPathRepository.findById(pathId)
                .orElseThrow(() -> new AppException(ErrorCode.LEARNING_PATH_NOT_FOUND));

        learningPath.setProgressPercent(request.getProgressPercent());
        learningPath.setUpdatedAt(LocalDateTime.now());

        learningPathRepository.save(learningPath);
    }

}
