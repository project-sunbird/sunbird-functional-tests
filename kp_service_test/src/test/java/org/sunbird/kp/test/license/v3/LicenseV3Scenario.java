package org.sunbird.kp.test.license.v3;

public class LicenseV3Scenario {

    //Update License Api Scenarios
    public static final String TEST_UPDATE_LICENSE_WITH_VALID_REQUEST = "testUpdateLicenseWithValidRequest";
    public static final String TEST_UPDATE_LICENSE_WITH_STATUS = "testUpdateLicenseWithStatus";
    public static final String TEST_UPDATE_LICENSE_WITH_CODE = "testUpdateLicenseWithCode";
    public static final String TEST_UPDATE_LICENSE_WITH_IDENTIFIER = "testUpdateLicenseWithIdentifier";
    public static final String TEST_UPDATE_LICENSE_WITH_NEW_METADATA = "testUpdateLicenseWithNewMetadata";
    public static final String TEST_UPDATE_LICENSE_WITH_METADATA_NAME_CASE_MISMATCH = "testUpdateLicenseWithMetadataNameCaseMismatch";
    public static final String TEST_UPDATE_LICENSE_WITH_DATA_TYPE_MISMATCH = "testUpdateLicenseWithDataTypeMismatch";
    public static final String TEST_UPDATE_LICENSE_WITH_INVALID_IDENTIFIER = "testUpdateLicenseWithInvalidIdentifier";

    //Retire License Api Scenarios
    public static final String TEST_RETIRE_LICENSE_WITH_VALID_REQUEST = "testRetireLicenseWithValidRequest";
    public static final String TEST_RETIRE_LICENSE_WITH_INVALID_IDENTIFIER = "testRetireLicenseWithInvalidIdentifier";
    //Create License Api Scenarios
    public static final String TEST_CREATE_LICENSE_WITH_VALID_REQUEST = "testCreateLicenseWithValidRequest";
    public static final String TEST_CREATE_LICENSE_WITHOUT_NAME = "testCreateLicenseWithoutName";
    public static final String TEST_CREATE_LICENSE_WITHOUT_URL = "testCreateLicenseWithoutUrl";
    public static final String TEST_CREATE_LICENSE_WITH_NEW_METADATA = "testCreateLicenseWithNewMetadata";
    public static final String TEST_CREATE_LICENSE_WITH_IDENTIFIER = "testCreateLicenseWithIdentifier";
    public static final String TEST_CREATE_LICENSE_WITH_STATUS = "testCreateLicenseWithStatus";
    public static final String TEST_CREATE_LICENSE_WITH_METADATA_NAME_CASE_MISMATCH = "testCreateLicenseWithMetadataNameCaseMismatch";
    public static final String TEST_CREATE_LICENSE_WITH_DATA_TYPE_MISMATCH = "testCreateLicenseWithDataTypeMismatch";
    public static final String TEST_CREATE_LICENSE_WITH_DUPLICATE_IDENTIFIER = "testCreateLicenseWithDuplicateIdentifier";
    public static final String TEST_CREATE_LICENSE_WITH_SLUGGIFIED_NAME = "testCreateLicenseWithSluggifiedName";

    //Read License Api Scenarios
    public static final String TEST_READ_LICENSE_WITH_VALID_IDENTIFIER = "testReadLicenseWithValidIdentifier";
    public static final String TEST_READ_LICENSE_WITH_INVALID_IDENTIFIER = "testReadLicenseWithInvalidIdentifier";
    public static final String TEST_READ_LICENSE_WITH_FIELDS = "testReadLicenseWithFields";
    public static final String TEST_READ_LICENSE_WITH_INVALID_FIELDS = "testReadLicenseWithInvalidFields";
    public static final String TEST_READ_LICENSE_AFTER_RETIRE = "testReadLicenseAfterRetire";
}
