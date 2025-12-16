package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Resource;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.ResourceResponse;

public class ResourceMapper {

    public static ResourceResponse toResponse(Resource resource) {
        if (resource == null) return null;

        ResourceResponse res = new ResourceResponse();
        res.setResourceId(resource.getResourceId());
        res.setName(resource.getName());
        res.setType(resource.getType());
        res.setExternalLink(resource.getExternalLink());
        res.setSizeBytes(resource.getSizeBytes());

        return res;
    }

}
