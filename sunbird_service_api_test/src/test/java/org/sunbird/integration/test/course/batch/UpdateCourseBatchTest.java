package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

public class UpdateCourseBatchTest extends BaseCitrusTestRunner {

    private static final String sunbird_course_id = System.getenv("sunbird_course_id");
    private static final String sunbird_completed_batch_id = System.getenv("sunbird_completed_batch_id");
    private static final String sunbird_completed_batch_start_date = System.getenv("sunbird_completed_batch_start_date");
    private static final String sunbird_completed_batch_end_date = System.getenv("sunbird_completed_batch_end_date");
    private static final String sunbird_completed_batch_req_start_date = System.getenv("sunbird_completed_batch_req_start_date");
    private static final String sunbird_completed_batch_req_end_date = System.getenv("sunbird_completed_batch_req_end_date");

    private static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
            "testUpdateCourseBatchFailureWithoutAuthToken";
    private static final String TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_ID =
            "testUpdateCourseBatchFailureWithInvalidId";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_STARTED_BATCH_AND_START_DATE =
            "testUpdateCourseBatchFailureWithStartedBatchAndStartDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_STARTED_BATCH_AND_END_DATE =
            "testUpdateCourseBatchFailureWithStartedBatchAndEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_STARTED_BATCH_AND_DIFFERENT_START_DATE_AND_END_DATE =
            "testUpdateCourseBatchFailureWithStartedBatchAndDifferentStartDateAndEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_FUTURE_END_DATE =
            "testUpdateCourseBatchFailureWithNotStartedBatchAndFutureEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_PAST_START_DATE =
            "testUpdateCourseBatchFailureWithNotStartedBatchAndPastStartDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_PAST_END_DATE =
            "testUpdateCourseBatchFailureWithNotStartedBatchAndPastEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_END_DATE_BEFORE_FUTURE_START_DATE =
            "testUpdateCourseBatchFailureWithNotStartedBatchAndEndDateBeforeFutureStartDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_COMPLETED_BATCH_AND_START_DATE =
            "testUpdateCourseBatchFailureWithCompletedBatchAndStartDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_COMPLETED_BATCH_AND_END_DATE =
            "testUpdateCourseBatchFailureWithCompletedBatchAndEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_COMPLETED_BATCH_AND_START_DATE_AND_END_DATE =
            "testUpdateCourseBatchFailureWithCompletedBatchAndStartDateAndEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_SUCCESS_WITH_STARTED_BATCH_AND_SAME_START_DATE_AND_END_DATE =
            "testUpdateCourseBatchSuccessWithStartedBatchAndSameStartDateAndEndDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_SUCCESS_WITH_NOT_STARTED_BATCH_AND_FUTURE_START_DATE =
            "testUpdateCourseBatchSuccessWithNotStartedBatchAndFutureStartDate";
    private static final String
            TEST_UPDATE_COURSE_BATCH_SUCCESS_WITH_NOT_STARTED_BATCH_AND_FUTURE_START_DATE_AND_END_DATE =
            "testUpdateCourseBatchSuccessWithNotStartedBatchAndFutureStartDateAndEndDate";

    public static final String TEMPLATE_DIR = "templates/course/batch/update";

    private String getUpdateCourseBatchUrl() {
        return getLmsApiUriPath("/api/course/v1/batch/update", "/v1/course/batch/update");
    }

    @DataProvider(name = "updateCourseBatchDataFailureProvider")
    public Object[][] updateCourseBatchDataFailureProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN,
                        false,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "citrus:currentDate('yyyy-MM-dd', '+8d')",
                        "",
                        HttpStatus.UNAUTHORIZED
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_ID,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "citrus:currentDate('yyyy-MM-dd', '+8d')",
                        "",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_STARTED_BATCH_AND_START_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd')",
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+2d')",
                        "",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_STARTED_BATCH_AND_END_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd')",
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "",
                        "citrus:currentDate('yyyy-MM-dd', '+8d')",
                        HttpStatus.BAD_REQUEST

                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_STARTED_BATCH_AND_DIFFERENT_START_DATE_AND_END_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd')",
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+1d')",
                        "citrus:currentDate('yyyy-MM-dd', '+8d')",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_FUTURE_END_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "",
                        "citrus:currentDate('yyyy-MM-dd', '+15d')",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_PAST_START_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "citrus:currentDate('yyyy-MM-dd', '-2d')",
                        "",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_PAST_END_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "",
                        "citrus:currentDate('yyyy-MM-dd', '-2d')",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_NOT_STARTED_BATCH_AND_END_DATE_BEFORE_FUTURE_START_DATE,
                        true,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "citrus:currentDate('yyyy-MM-dd', '+12d')",
                        "citrus:currentDate('yyyy-MM-dd', '+10d')",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_COMPLETED_BATCH_AND_START_DATE,
                        true,
                        false,
                        false,
                        sunbird_completed_batch_start_date,
                        sunbird_completed_batch_end_date,
                        sunbird_completed_batch_req_start_date,
                        "",
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_COMPLETED_BATCH_AND_END_DATE,
                        true,
                        false,
                        false,
                        sunbird_completed_batch_start_date,
                        sunbird_completed_batch_end_date,
                        "",
                        sunbird_completed_batch_req_end_date,
                        HttpStatus.BAD_REQUEST
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_FAILURE_WITH_COMPLETED_BATCH_AND_START_DATE_AND_END_DATE,
                        true,
                        false,
                        false,
                        sunbird_completed_batch_start_date,
                        sunbird_completed_batch_end_date,
                        sunbird_completed_batch_req_start_date,
                        sunbird_completed_batch_req_end_date,
                        HttpStatus.BAD_REQUEST
                }
        };
    }

    @Test(dataProvider = "updateCourseBatchDataFailureProvider")
    @CitrusParameters({
            "testName",
            "isAuthorized",
            "canCreateCourse",
            "canCreateBatch",
            "existingStartDate",
            "existingEndDate",
            "requestedStartDate",
            "requestedEndDate",
            "httpStatusCode"
    })
    @CitrusTest
    public void testUpdateCourseBatchFailure(
            String testName,
            Boolean isAuthorized,
            Boolean canCreateCourse,
            Boolean canCreateBatch,
            String existingStartDate,
            String existingEndDate,
            String requestedStartDate,
            String requestedEndDate,
            HttpStatus httpStatusCode) {
        getTestCase().setName(testName);
        beforeTest(canCreateCourse, canCreateBatch, existingStartDate, existingEndDate, requestedStartDate, requestedEndDate);

        performPatchTest(
                this,
                TEMPLATE_DIR,
                testName,
                getUpdateCourseBatchUrl(),
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                isAuthorized,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @DataProvider(name = "updateCourseBatchDataSuccessProvider")
    public Object[][] updateCourseBatchDataSuccessProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_SUCCESS_WITH_STARTED_BATCH_AND_SAME_START_DATE_AND_END_DATE,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd')",
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd')",
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        HttpStatus.OK
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_SUCCESS_WITH_NOT_STARTED_BATCH_AND_FUTURE_START_DATE,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "citrus:currentDate('yyyy-MM-dd', '+8d')",
                        "",
                        HttpStatus.OK
                },
                new Object[]{
                        TEST_UPDATE_COURSE_BATCH_SUCCESS_WITH_NOT_STARTED_BATCH_AND_FUTURE_START_DATE_AND_END_DATE,
                        true,
                        true,
                        "citrus:currentDate('yyyy-MM-dd', '+7d')",
                        "citrus:currentDate('yyyy-MM-dd', '+14d')",
                        "citrus:currentDate('yyyy-MM-dd', '+8d')",
                        "citrus:currentDate('yyyy-MM-dd', '+16d')",
                        HttpStatus.OK
                }
        };
    }

    @Test(dataProvider = "updateCourseBatchDataSuccessProvider")
    @CitrusParameters({
            "testName",
            "canCreateCourse",
            "canCreateBatch",
            "existingStartDate",
            "existingEndDate",
            "requestedStartDate",
            "requestedEndDate",
            "httpStatusCode"
    })
    @CitrusTest
    public void testUpdateCourseBatchSuccess(
            String testName,
            Boolean canCreateCourse,
            Boolean canCreateBatch,
            String existingStartDate,
            String existingEndDate,
            String requestedStartDate,
            String requestedEndDate,
            HttpStatus httpStatusCode) {
        getTestCase().setName(testName);
        beforeTest(canCreateCourse, canCreateBatch, existingStartDate, existingEndDate, requestedStartDate, requestedEndDate);

        performPatchTest(
                this,
                TEMPLATE_DIR,
                testName,
                getUpdateCourseBatchUrl(),
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                true,
                httpStatusCode,
                RESPONSE_JSON);
    }

    private void beforeTest(Boolean canCreateCourse, Boolean canCreateBatch, String existingStartDate, String existingEndDate, String requestedStartDate, String requestedEndDate) {

        if (canCreateCourse) {
            getAuthToken(this, true);
            variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
            variable("resourceId", ContentStoreUtil.getResourceId());
            String courseId = ContentStoreUtil.getCourseId(this, testContext);
            variable("courseId", courseId);
        }

        if (canCreateBatch) {
            this.variable("startDate", existingStartDate);
            this.variable("endDate", existingEndDate);

            String courseBatchId = CourseBatchUtil.getCourseBatchId(this, testContext, "testCreateCourseBatchSuccessInviteOnlyBatchWithStartDateAndEndDate");
            this.variable("batchId", courseBatchId);

            this.variable("requestedStartDate", requestedStartDate);
            this.variable("requestedEndDate", requestedEndDate);
        }

        if(canCreateCourse == false && canCreateBatch == false){

            getAuthToken(this, true);
            this.variable("courseId", sunbird_course_id);
            this.variable("batchId", sunbird_completed_batch_id);
            this.variable("startDate", existingStartDate);
            this.variable("endDate", existingEndDate);
            this.variable("requestedStartDate", requestedStartDate);
            this.variable("requestedEndDate", requestedEndDate);
        }
    }
}