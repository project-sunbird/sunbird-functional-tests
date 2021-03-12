package org.sunbird.kp.test.common;

public class SearchCitrusTestRunner extends BaseCitrusTestRunner{
    @Override
    public String getEndPoint(String reqUrl) {
        return Constant.KP_SEARCH_SERVICE_ENDPOINT;
    }

}
