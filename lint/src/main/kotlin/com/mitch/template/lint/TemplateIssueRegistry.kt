package com.mitch.template.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.mitch.template.lint.compose.designsystem.DesignSystemDetector
import com.mitch.template.lint.kt.KotlinNamingDetector

class TemplateIssueRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(
        DesignSystemDetector.IncorrectDesignSystemCallIssue,
        KotlinNamingDetector.EnumConstantNameIssue,
        KotlinNamingDetector.ConstantFieldNameIssue
    )

    override val minApi: Int = 12
    override val api: Int = CURRENT_API

    private val repoUrl = "https://github.com/seve-andre/jetpack-compose-template"
    override val vendor: Vendor = Vendor(
        vendorName = "Template",
        feedbackUrl = "$repoUrl/issues",
        contact = repoUrl
    )
}
