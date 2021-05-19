package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.common.BaseCitrusTestRunner;
import org.sunbird.common.EndpointConfig.TestGlobalProperty;

public class CourseEnrollmentUtil {
  public static final String TEMPLATE_DIR_ENROLL_COURSE = "templates/course/batch/enroll";
  public static final String TEST_NAME_ENROLL_COURSE_SUCCESS = "testEnrollCourseSuccess";

  public static final String TEMPLATE_DIR_UNENROLL_COURSE = "templates/course/batch/unenroll";
  public static final String TEST_NAME_UNENROLL_COURSE_SUCCESS = "testUnenrollCourseSuccess";

  private static String getUnenrollCourseUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/course/v1/unenrol", "/v1/course/unenroll");
  }

  private static String getEnrollCourseUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/course/v1/enrol", "/v1/course/enroll");
  }

  public static void enrollCourse(
      BaseCitrusTestRunner runner, TestContext testContext, TestGlobalProperty config) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                TEMPLATE_DIR_ENROLL_COURSE,
                TEST_NAME_ENROLL_COURSE_SUCCESS,
                getEnrollCourseUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
  }

  public static void unenrollCourse(
      BaseCitrusTestRunner runner, TestContext testContext, TestGlobalProperty config) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                TEMPLATE_DIR_UNENROLL_COURSE,
                TEST_NAME_UNENROLL_COURSE_SUCCESS,
                getUnenrollCourseUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
  }
}
