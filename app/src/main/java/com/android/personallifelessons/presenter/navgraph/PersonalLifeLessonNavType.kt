package com.android.personallifelessons.presenter.navgraph

import android.os.Bundle
import androidx.navigation.NavType
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.google.gson.Gson

class PersonalLifeLessonNavType: NavType<PersonalLifeLesson>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PersonalLifeLesson? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PersonalLifeLesson {
        return Gson().fromJson(value, PersonalLifeLesson::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: PersonalLifeLesson) {
        bundle.putParcelable(key, value)
    }
}