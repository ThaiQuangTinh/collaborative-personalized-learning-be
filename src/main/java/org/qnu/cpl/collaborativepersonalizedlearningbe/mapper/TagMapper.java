package org.qnu.cpl.collaborativepersonalizedlearningbe.mapper;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.Tag;
import org.qnu.cpl.collaborativepersonalizedlearningbe.payload.response.TagResponse;

public class TagMapper {

    public static TagResponse toResponse(Tag tag) {
        if (tag == null) return null;

        TagResponse res = new TagResponse();
        res.setTagId(tag.getTagId());
        res.setTagName(tag.getTagName());
        res.setTextColor(tag.getTextColor());

        return res;
    }

}
