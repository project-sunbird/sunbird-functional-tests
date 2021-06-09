package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.course.batch.CreateCourseBatchTest;

public class CourseBatchUtil {

  private String openCourseBatchId;
  public static final String TEST_NAME_CREATE_COURSE_BATCH_SUCCESS_OPEN_BATCH =
      "testCreateCourseBatchSuccessOpenBatch";

  private static String getCreateCourseBatchUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath("/api/course/v1/batch/create", "/v1/course/batch/create");
  }

  public String getOpenCourseBatchId(BaseCitrusTestRunner runner, TestContext testContext) {
    if (StringUtils.isBlank(openCourseBatchId)) {
      openCourseBatchId =
          getCourseBatchId(runner, testContext, TEST_NAME_CREATE_COURSE_BATCH_SUCCESS_OPEN_BATCH);
    }
    return openCourseBatchId;
  }

  private static String getCourseBatchId(
      BaseCitrusTestRunner runner, TestContext testContext, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                CreateCourseBatchTest.TEMPLATE_DIR,
                testName,
                getCreateCourseBatchUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));
    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                HttpStatus.OK,
                "$.result.batchId",
                Constant.EXTRACT_VAR_BATCH_ID));
    return testContext.getVariable(Constant.EXTRACT_VAR_BATCH_ID);
  }
}
