package org.qnu.cpl.collaborativepersonalizedlearningbe.service;

import java.util.List;

import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateShareLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.DeleteLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ExportLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.ImportLearningPathJsonRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.LearningPathImportRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateLearningPathRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.UpdateProgressPercentRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.CreateShareLearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathExportResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.LearningPathStatisticResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.NoteResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ShareLearningPathResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TagResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TopicResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.UpdateLearningPathResponse;

public interface LearningPathService {

    LearningPathResponse createLearningPath(String userId, CreateLearningPathRequest request);

    UpdateLearningPathResponse updateLearningPath(String pathId, UpdateLearningPathRequest request);

    void deleteLearningPaths(DeleteLearningPathRequest request);

    void addFavoriteByPathId(String userId, String pathId);

    void deleteFavoriteByPathId(String userId, String pathId);

    void addArchiveByPathId(String userId, String pathId);

    void deleteArchiveByPathId(String userId, String pathId);

    List<TopicResponse> getTopicsByPathId(String pathId);

    List<TagResponse> getTagsByLearningPathId(String pathId);

    List<LearningPathResponse> getAllLearningPathsByUser(String userId);

    List<LearningPathResponse> getFavoriteLearningPaths(String userId);

    List<LearningPathResponse> getArchivedLearningPaths(String userId);

    LearningPathResponse getLearningPathByPathId(String pathId);

    List<NoteResponse> getNotesByLearningPathId(String pathId);

    CreateShareLearningPathResponse shareLearningPath(String userId, String pathId,
            CreateShareLearningPathRequest request);

    ShareLearningPathResponse getLearningPathByShare(String token);

    LearningPathResponse cloneLearningPathForUser(String userId, String token);

    List<LearningPathExportResponse> exportLearningPaths(ExportLearningPathRequest request);

    List<LearningPathResponse> importLearningPaths(String userId, List<LearningPathImportRequest> requests);

    List<LearningPathResponse> importJsonLearningPath(String userId, ImportLearningPathJsonRequest request);

    LearningPathStatisticResponse getLearningPathStatistics(String userId, String pathId);

    List<LearningPathStatisticResponse> getAllLearningPathStatistics(String userId);

    void updateProgressPercent(String pathId, UpdateProgressPercentRequest request);

}
