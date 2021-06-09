package org.sunbird.integration.test.course.enrollment;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.common.action.CourseEnrollmentUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

public class LearnerStateUpdateTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/course/content/state/update";
    private CourseBatchUtil courseBatchUtil = new CourseBatchUtil();
    private static final String TEST_LEARNER_STATE_UPDATE =
            "testUpdateLearnerState";

    private String getUpdateCotentStateURL() {
        return getLmsApiUriPath("/api/course/v1/content/state/update", "/v1/content/state/update");
    }

    private String getGetCotnentStateUrl() {
        return getLmsApiUriPath("/api/course/v1/content/state/read", "/v1/content/state/read");
    }

    @DataProvider(name = "updateCourseBatchDataFailureProvider")
    public Object[][] updateCourseBatchDataFailureProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_LEARNER_STATE_UPDATE,
                        true,
                        true,
                        HttpStatus.OK
                }
        };
    }

    @Test(dataProvider = "updateLearnerState")
    @CitrusParameters({
            "testName",
            "isAuthRequired",
            "isOpenBatch",
            "httpStatusCode"
    })
    @CitrusTest
    public void testUpdateCourseBatchFailure(
            String testName,
            boolean isAuthRequired,
            boolean isOpenBatch,
            HttpStatus httpStatusCode) {
        getTestCase().setName(testName);
        beforeTest(isOpenBatch);
        variable("status", 2);
        performPatchTest(
                this,
                TEMPLATE_DIR,
                testName,
                getUpdateCotentStateURL(),
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);

        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                getGetCotnentStateUrl(),
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);

        variable("status", 1);

        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                getUpdateCotentStateURL(),
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                isAuthRequired,
                httpStatusCode,
                "invalid_response.json");
    }

    private void beforeTest(boolean isOpenBatch) {
        String courseId = ContentStoreUtil.createLiveCourse(this, testContext);
        String resourceId = ContentStoreUtil.getResourceId();
        String courseBatchId = "";
        courseBatchId = courseBatchUtil.getOpenCourseBatchId(this, testContext);
        variable("batchId", courseBatchId);
        variable("courseId", courseId);
        variable("contentId", resourceId);
        testContext.setVariable(Constant.USER_ID, "bacacc74-f828-403c-987a-333550c204db");
        CourseEnrollmentUtil.enrollCourse(this, testContext, config);

    }
}
