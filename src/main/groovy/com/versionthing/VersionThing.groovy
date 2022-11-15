package com.versionthing

import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionThing implements Plugin<Project> {

    private Project project;

    @Override
    void apply(Project project) {
        this.project = project;
        project.tasks.create("releaseWithBranch") { task ->
            task.doLast {
                releaseWithBranch(project)
            }
        }
    }

    private void releaseWithBranch() {
        String pv = project.version.toString()
        println(">> is version: " + pv)
        String branch = getCurrentGitBranch()
        if (branch.contains("release/")) {
            String[] v = branch.split('/')

            String version = v[1]

            println(">> new version: " + version)
            ant.replace(file: 'gradle.properties', token: pv, value: version)

            ("git tag $version").execute()
            ("git push --tags").execute()
        }
    }

    private String getCurrentGitBranch() {
        def gitBranch = "Unknown branch"
        try {
            def workingDir = new File("${project.projectDir}")
            def result = 'git rev-parse --abbrev-ref HEAD'.execute(null, workingDir)
            result.waitFor()
            if (result.exitValue() == 0) {
                gitBranch = result.text.trim()
            }
        } catch (e) {
        }
        return gitBranch
    }

}