package org.qnu.cpl.collaborativepersonalizedlearningbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.qnu.cpl.collaborativepersonalizedlearningbe.model.ApiResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateFileResourceRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.request.CreateLinkResourceRequest;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceUrlResponse;
import org.qnu.cpl.collaborativepersonalizedlearningbe.security.CustomUserDetails;
import org.qnu.cpl.collaborativepersonalizedlearningbe.service.ResourceService;
import org.qnu.cpl.collaborativepersonalizedlearningbe.util.ResponseUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    // Create resource.
    @PostMapping("/upload/file")
    public ResponseEntity<ApiResponse<ResourceResponse>> uploadFile(
            @ModelAttribute CreateFileResourceRequest request,
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUserId();

        ResourceResponse response = resourceService.createFileResource(userId, request);

        return ResponseUtil.created(response, "Resource file created successfully!", httpRequest);
    }

    @PostMapping("/upload/link")
    public ResponseEntity<ApiResponse<ResourceResponse>> upLoadLink(
            @RequestBody CreateLinkResourceRequest request,
            HttpServletRequest httpRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUserId();

        ResourceResponse response = resourceService.createLinkResource(userId, request);

        return ResponseUtil.created(response, "Resource link created successfully!", httpRequest);
    }

    @DeleteMapping("/{resourceId}")
    public ResponseEntity<ApiResponse<Object>> deleteResource(@PathVariable String resourceId,
                                                              HttpServletRequest httpRequest) {

        resourceService.deleteResource(resourceId);

        return ResponseUtil.success(null, "Resource deleted successfully!",
                HttpStatus.OK, httpRequest);
    }

    @GetMapping("/{resourceId}/download")
    public ResponseEntity<InputStreamResource> downloadResource(@PathVariable String resourceId) {
        String[] fileName = new String[1];
        String[] mimeType = new String[1];

        InputStreamResource fileStream = resourceService.getResourceFile(resourceId, fileName, mimeType);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName[0] + "\"")
                .contentType(MediaType.parseMediaType(mimeType[0]))
                .body(fileStream);
    }

    @GetMapping("/{resourceId}/preview")
    public ResponseEntity<byte[]> preview(@PathVariable String resourceId) {
        String[] fileName = new String[1];
        String[] mimeType = new String[1];

        InputStreamResource resourceStream = resourceService.getResourceFile(resourceId, fileName, mimeType);

        try (InputStream is = resourceStream.getInputStream()) {
            byte[] bytes = is.readAllBytes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName[0] + "\"")
                    .contentType(MediaType.parseMediaType(mimeType[0]))
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                    .contentLength(bytes.length)
                    .body(bytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{resourceId}/url")
    public ResponseEntity<ApiResponse<ResourceUrlResponse>> getResourceUrlById(
            @PathVariable String resourceId,
            HttpServletRequest httpRequest) {

        ResourceUrlResponse resourceUrl = resourceService.getResourceUrl(resourceId);

        return ResponseUtil.success(resourceUrl, "Get resource url successfully!",
                HttpStatus.OK, httpRequest);
    }

}
