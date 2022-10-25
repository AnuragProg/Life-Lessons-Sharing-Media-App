package com.android.personallifelessons.presenter.post

enum class Category(val nam : String, val id: String) {
    CAREER_ADVICE("Career Advice", "career_advice"),
    HIGH_LEVEL_ADVICE("High level Advice", "high_level_advice"),
    OFFICE_POLITICS_ADVICE("Office Politics Advice","office_politics_advice"),
    SELF_SERVICE_ADVICE("Self Service Advice", "self_service_advice"),
    SEMI_SOLICITED_ADVICE("Semi Solicited Advice", "semi_solicited_advice"),
    SOLICITED_ADVICE("Solicited Advice", "solicited_advice"),
    TOO_HIGH_LEVEL_ADVICE("Too High Level Advice", "too_high_level_advice"),
    UNSOLICITED_ADVICE("Unsolicited Advice", "unsolicited_advice");

    companion object{
        fun extractCategoryFromNam(nam: String): Category? {
            return when (nam) {
                CAREER_ADVICE.nam -> CAREER_ADVICE
                HIGH_LEVEL_ADVICE.nam -> HIGH_LEVEL_ADVICE
                SOLICITED_ADVICE.nam -> SOLICITED_ADVICE
                SELF_SERVICE_ADVICE.nam -> SELF_SERVICE_ADVICE
                OFFICE_POLITICS_ADVICE.nam -> OFFICE_POLITICS_ADVICE
                SEMI_SOLICITED_ADVICE.nam -> SEMI_SOLICITED_ADVICE
                TOO_HIGH_LEVEL_ADVICE.nam -> TOO_HIGH_LEVEL_ADVICE
                UNSOLICITED_ADVICE.nam -> UNSOLICITED_ADVICE
                else -> null
            }
        }
    }
}