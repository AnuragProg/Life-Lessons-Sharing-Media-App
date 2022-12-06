package com.android.personallifelessons.data.components

object Constants {

    private const val BASEURL = "http://10.0.2.2:5000/"
    private const val VERSION = "v1/"
    private const val CATEGORY = "category/"
    private const val COMMENT = "comment/"
    private const val USER = "user/"
    private const val PLL = "pll/"

    const val USERBASEURL= BASEURL + VERSION + USER
    const val COMMENTBASEURL = BASEURL + VERSION + COMMENT
    const val PLLBASEURL = BASEURL + VERSION + PLL
    const val CATEGORYBASEURL = BASEURL + VERSION + CATEGORY
}