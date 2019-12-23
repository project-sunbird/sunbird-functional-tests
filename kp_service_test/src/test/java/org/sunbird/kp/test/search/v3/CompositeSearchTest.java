package org.sunbird.kp.test.search.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.common.SearchCitrusTestRunner;
import org.sunbird.kp.test.util.CompositeSearchUtil;
import org.sunbird.kp.test.util.ContentPayload;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.SearchPayload;
import org.sunbird.kp.test.util.WorkflowConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Functional Test Cases for search-service (Composite Search Api)
 *
 * @author Kumar Gauraw
 */
public class CompositeSearchTest extends SearchCitrusTestRunner {

	private static final String TEMPLATE_DIR = "templates/search/v3";

	@Test
	@CitrusTest
	public void testSearchForTextBookHavingRelatedBoards() throws JsonProcessingException {
		String testName = "testSearchForTextBookHavingConsumeAs";
		this.getTestCase().setName(testName);
		getAuthToken(this, Constant.CREATOR);
		//TODO: Get Board Using Framework API.
		String board = "ICSE";
		String anotherBoard = "testboard"+generateRandomDigits(5);
		String textbookId = (String) ContentUtil.createCollectionContent(this, null, "textBook", null).get("content_id");
		System.out.println("Textbook Id : " + textbookId);
		String updateReqPayload = "{\"request\":{\"content\":{\"board\":\"boardVal\"}}}".replace("boardVal", board);
		String updatedTbId = (String) ContentUtil.systemUpdate(this, testContext, textbookId, updateReqPayload, testName, null).get("content_id");
		Assert.assertTrue(StringUtils.isNotBlank(textbookId) && StringUtils.isNotBlank(updatedTbId));
		String consumableTextbookId = (String) ContentUtil.createCollectionContent(this, null, "textBook", null).get("content_id");
		String payload = "{\"request\":{\"content\":{\"relatedBoards\":boardVal}}}".replace("boardVal", objectMapper.writeValueAsString(new ArrayList<String>(){{
			add(board);
			add(anotherBoard);
		}}));
		String updatedId = (String) ContentUtil.systemUpdate(this, testContext, consumableTextbookId, payload, testName, null).get("content_id");
		Assert.assertTrue(StringUtils.isNotBlank(textbookId) && StringUtils.isNotBlank(updatedId));
		ContentUtil.publishContent(this, null, "public", consumableTextbookId, null);
		delay(this, 60000);
		//search content and validate
		String searchPayload = SearchPayload.SEARCH_CONTENT_WITH_BOARD.replace("boardVal", board);
		System.out.println("searchPayload : " + searchPayload);
		Map<String, Object> searchResult = CompositeSearchUtil.searchContent(this, searchPayload, testName, null);
		System.out.println("count : "+searchResult.get("count"));
		List<Map<String, Object>> content = (List<Map<String, Object>>) searchResult.get("content");
		boolean found = false;
		for (Map<String, Object> record : content) {
			if (record.containsKey("relatedBoards")) {
				List<String> relatedBoards = (List<String>)record.get("relatedBoards");
				for(String relBoard:relatedBoards){
					if (StringUtils.equals(relBoard, anotherBoard) && StringUtils.equals(consumableTextbookId, (String) record.get("identifier"))) {
						found = true;
					}
				}
			}
		}
		Assert.assertTrue(found);
	}

	@Test
	@CitrusTest
	public void testSearchForRemovalContentTaggedPropertyBackwardSupport() throws JsonProcessingException {
		String testName = SearchV3Scenarios.TEST_SEARCH_FOR_MEDIUM_SUBJECT;
		this.getTestCase().setName(testName);
		getAuthToken(this, Constant.CREATOR);
		Map<String, Object> result = ContentUtil.prepareResourceContent(WorkflowConstants.CONTENT_IN_LIVE_STATE, this, ContentPayload.CREATE_RESOURCE_CONTENT_WITH_SUBJECT_MEDIUM
				, "application/pdf", null);
		delay(this, 10000);
		//TODO: Get Board Using Framework API.
		String identifier = (String) result.get("content_id");
		this.variable("contentIdVal", identifier);
		performPostTest(this, TEMPLATE_DIR, testName, APIUrl.COMPOSITE_SEARCH, null,
				REQUEST_JSON, MediaType.APPLICATION_JSON, HttpStatus.OK, null, RESPONSE_JSON);
	}
}
