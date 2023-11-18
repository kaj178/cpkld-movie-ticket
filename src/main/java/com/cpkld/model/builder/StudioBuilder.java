package com.cpkld.model.builder;

import com.cpkld.model.entity.Studio;

public interface StudioBuilder {
    StudioBuilder setName(String name);
    StudioBuilder setAddress(String address);
    StudioBuilder setPhoneNumber(String phoneNumber);
    StudioBuilder setEmail(String Email);

    StudioBuilder setWebsite(String website);
    StudioBuilder setImgUrl(String imgUrl);

    Studio build();
}
