package dev.logchange.maven_plugin.mojo.release;

import com.soebes.itf.jupiter.extension.MavenGoal;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenOption;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

@MavenJupiterExtension
class ReleaseVersionMojoIT {


    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:release")
    @MavenTest
    @DisplayName("Project with pom.xml and task.yml in changelog dir after release task.yml is moved to version dir and CHANGELOG.md is generated with logchange-config.yml included")
    void releaseWithLogchangeConfig(MavenExecutionResult result) throws IOException {
        assertThat(result).isSuccessful()
                .project()
                .has("changelog")
                .has("changelog/unreleased")
                .has("changelog/v1.0.0");


        File gitKeep = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/unreleased/.gitkeep");
        File changelog = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "CHANGELOG.md");
        File taskMovedToRelease = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.0.0/task.yml");
        File releaseDateFile = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.0.0/release-date.txt");
        File versionSummary = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.0.0/version-summary.md");

        assertThat(gitKeep).exists();
        assertThat(changelog).exists();
        assertThat(taskMovedToRelease).exists();
        assertThat(releaseDateFile).exists();
        assertThat(versionSummary).exists();

        assertThat(FileUtils.readFileToString(changelog, StandardCharsets.UTF_8)).contains("Dodano");
        assertThat(FileUtils.readFileToString(versionSummary, StandardCharsets.UTF_8)).contains("Dodano");
    }


    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:release")
    @MavenTest
    @DisplayName("Project with pom.xml and task.yml in changelog dir after release task.yml is moved to version dir and CHANGELOG.md is generated")
    void releaseWithMissingLogchangeConfig(MavenExecutionResult result) {
        assertThat(result).isSuccessful()
                .project()
                .has("changelog")
                .has("changelog/unreleased")
                .has("changelog/v1.0.0");


        File gitKeep = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/unreleased/.gitkeep");
        File changelog = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "CHANGELOG.md");
        File taskMovedToRelease = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.0.0/task.yml");
        File releaseDateFile = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.0.0/release-date.txt");
        File versionSummary = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.0.0/version-summary.md");

        assertThat(gitKeep).exists();
        assertThat(changelog).exists();
        assertThat(taskMovedToRelease).exists();
        assertThat(releaseDateFile).exists();
        assertThat(versionSummary).exists();
    }

    @MavenGoal("${project.groupId}:${project.artifactId}:${project.version}:release")
    @MavenTest
    @DisplayName("Project with pom.xml with version set to 1.3.7")
    void releaseWithVersionInPomXmlWithoutSnapshotOrRc(MavenExecutionResult result) {
        assertThat(result).isSuccessful()
                .project()
                .has("changelog")
                .has("changelog/unreleased")
                .has("changelog/v1.3.7");


        File gitKeep = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/unreleased/.gitkeep");
        File changelog = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "CHANGELOG.md");
        File taskMovedToRelease = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.3.7/task.yml");
        File releaseDateFile = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.3.7/release-date.txt");
        File versionSummary = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.3.7/version-summary.md");

        assertThat(gitKeep).exists();
        assertThat(changelog).exists();
        assertThat(taskMovedToRelease).exists();
        assertThat(releaseDateFile).exists();
        assertThat(versionSummary).exists();
    }

    @MavenGoal({"${project.groupId}:${project.artifactId}:${project.version}:release"})
    @MavenOption("-DchangesXml")
    @MavenOption("-DoutputFileXml=\"TestChanges.xml\"")
    @MavenTest
    @DisplayName("Project with pom.xml with version set to 1.8.9, generating Changes.xml with custom name")
    void releaseWithVersionInPomXmlGeneratingChangesXml(MavenExecutionResult result) {
        assertThat(result).isSuccessful()
                .project()
                .has("changelog")
                .has("changelog/unreleased")
                .has("changelog/v1.8.9");


        File gitKeep = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/unreleased/.gitkeep");
        File changelog = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "CHANGELOG.md");
        File taskMovedToRelease = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.8.9/task.yml");
        File releaseDateFile = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.8.9/release-date.txt");
        File versionSummary = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "changelog/v1.8.9/version-summary.md");
        File changesXmlFIle = new File(result.getMavenProjectResult().getTargetProjectDirectory(), "TestChanges.xml");

        assertThat(gitKeep).exists();
        assertThat(changelog).exists();
        assertThat(taskMovedToRelease).exists();
        assertThat(releaseDateFile).exists();
        assertThat(versionSummary).exists();
        assertThat(changesXmlFIle).exists();
    }


}