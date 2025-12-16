package org.qnu.cpl.collaborativepersonalizedlearningbe.repository;

import org.qnu.cpl.collaborativepersonalizedlearningbe.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingRepository extends JpaRepository<UserSettings, String> {

    UserSettings findAllByUser_UserId(String userId);

}
