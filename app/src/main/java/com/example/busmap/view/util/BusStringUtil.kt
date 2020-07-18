package com.example.busmap.view.util

class BusStringUtil {
    companion object {
        fun formatOrganizationString(organization: String) : String {
            return organization.split("<br/>")
                .filter {
                    it.isNotBlank()
                }
                .joinToString(" ")
        }

        fun formatPathDescription(description : String) : String {
            return description.replace("\n", " ")
        }
    }
}