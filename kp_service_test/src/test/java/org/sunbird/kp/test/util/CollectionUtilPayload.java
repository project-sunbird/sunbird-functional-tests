package org.sunbird.kp.test.util;

public class CollectionUtilPayload {
    public static final String UPDATE_HIERARCHY_1_UNIT_1_RESOURCE = "{\n" +
            "  \"request\": {\n" +
            "    \"data\": {\n" +
            "      \"nodesModified\": {\n" +
            "        \"${collectionIdVal}\":{\n" +
            "          \"isNew\": false,\n" +
            "          \"root\": true,\n" +
            "          \"metadata\": {}\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"isNew\": true,\n" +
            "          \"root\": false,\n" +
            "          \"metadata\": {\n" +
            "            \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "            \"contentType\": \"${contentTypeVal}\",\n" +
            "            \"code\": \"citrus:concat('unit_code_', citrus:randomNumber(10))\",\n" +
            "            \"name\": \"citrus:concat('Test_${contentTypeVal}_name_', citrus:randomNumber(10))\",\n" +
            "            \"description\": \"citrus:concat('Test_${contentTypeVal}_desc_', citrus:randomNumber(10))\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"hierarchy\": {\n" +
            "        \"${collectionIdVal}\": {\n" +
            "          \"children\": [\n" +
            "            \"2cb4d698-dc19-4f0c-9990-96f49daff753\"\n" +
            "          ],\n" +
            "          \"root\": true\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"children\": [\"resource_1\"],\n" +
            "          \"root\": false\n" +
            "        }\n" +
            "\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    public static final String UPDATE_HIERARCHY_1_UNIT_1_ASSET = "{\n" +
            "  \"request\": {\n" +
            "    \"data\": {\n" +
            "      \"nodesModified\": {\n" +
            "        \"${collectionIdVal}\":{\n" +
            "          \"isNew\": false,\n" +
            "          \"root\": true,\n" +
            "          \"metadata\": {}\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"isNew\": true,\n" +
            "          \"root\": false,\n" +
            "          \"metadata\": {\n" +
            "            \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "            \"contentType\": \"${contentTypeVal}\",\n" +
            "            \"code\": \"citrus:concat('unit_code_', citrus:randomNumber(10))\",\n" +
            "            \"name\": \"citrus:concat('Test_${contentTypeVal}_name_', citrus:randomNumber(10))\",\n" +
            "            \"description\": \"citrus:concat('Test_${contentTypeVal}_desc_', citrus:randomNumber(10))\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"hierarchy\": {\n" +
            "        \"${collectionIdVal}\": {\n" +
            "          \"children\": [\n" +
            "            \"2cb4d698-dc19-4f0c-9990-96f49daff753\"\n" +
            "          ],\n" +
            "          \"root\": true\n" +
            "        },\n" +
            "        \"2cb4d698-dc19-4f0c-9990-96f49daff753\": {\n" +
            "          \"children\": [\"asset_1\"],\n" +
            "          \"root\": false\n" +
            "        }\n" +
            "\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String UPDATE_HIERARCHY_1_UNIT_2_SUBUNIT_3_RESOURCES = "{\"request\": {\n" +
            "   \"data\": {\n" +
            "     \"nodesModified\": {\n" +
            "     \t\"${collectionIdVal}\": {\n" +
            "     \t\t\"isNew\":false,\n" +
            "     \t\t\"root\":true\n" +
            "     \t},\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6c\": {\n" +
            "         \"isNew\": true,\n" +
            "         \"root\": false,\n" +
            "         \"metadata\": {\n" +
            "           \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "           \"contentType\": \"TextBookUnit\",\n" +
            "           \"code\": \"kp.citrus\",\n" +
            "           \"name\": \"Test_CourseUnit_1\",\n" +
            "           \"description\": \"Test_CourseUnit_desc_1\"\n" +
            "         }\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6d\": {\n" +
            "         \"isNew\": true,\n" +
            "         \"root\": false,\n" +
            "         \"metadata\": {\n" +
            "           \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "           \"contentType\": \"TextBookUnit\",\n" +
            "           \"code\": \"kp.citrus\",\n" +
            "           \"name\": \"Test_Course_SubUnit_name_1\",\n" +
            "           \"description\": \"Test_CourseSubUnit_desc_1\"\n" +
            "         }\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6e\": {\n" +
            "         \"isNew\": true,\n" +
            "         \"root\": false,\n" +
            "         \"metadata\": {\n" +
            "           \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "           \"contentType\": \"TextBookUnit\",\n" +
            "           \"code\": \"kp.citrus\",\n" +
            "           \"name\": \"Test_Course_SubUnit_name_2\",\n" +
            "           \"description\": \"Test_CourseSubUnit_desc_2\"\n" +
            "         }\n" +
            "       }\n" +
            "     },\n" +
            "     \"hierarchy\": {\n" +
            "       \"${collectionIdVal}\": {\n" +
            "         \"children\": [\n" +
            "           \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6c\"\n" +
            "         ],\n" +
            "         \"root\": true\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6c\": {\n" +
            "         \"children\": [\n" +
            "         \t\"b9a50833-eff6-4ef5-a2a4-2413f2d51f6d\",\n" +
            "         \t\"b9a50833-eff6-4ef5-a2a4-2413f2d51f6e\"\n" +
            "         ],\n" +
            "         \"root\": false\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6d\": {\n" +
            "         \"children\": [\n" +
            "         \t\"resource_1\"\n" +
            "         ],\n" +
            "         \"root\": false\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6e\": {\n" +
            "         \"children\": [\n" +
            "         \t\"resource_2\",\n" +
            "         \t\"resource_3\"\n" +
            "         ],\n" +
            "         \"root\": false\n" +
            "       }\n" +
            "     }\n" +
            "   }\n" +
            " }\n" +
            "}\n";

    public static final String UPDATE_HIERARCHY_WITH_1_UNIT_2SUB3RESOURCES= "{\"request\": {\n" +
            "   \"data\": {\n" +
            "     \"nodesModified\": {\n" +
            "     \t\"${collectionIdVal}\": {\n" +
            "     \t\t\"isNew\":false,\n" +
            "     \t\t\"root\":true\n" +
            "     \t},\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6c\": {\n" +
            "         \"isNew\": true,\n" +
            "         \"root\": false,\n" +
            "         \"metadata\": {\n" +
            "           \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "           \"contentType\": \"TextBookUnit\",\n" +
            "           \"code\": \"kp.citrus\",\n" +
            "           \"name\": \"Test_CourseUnit_1\",\n" +
            "           \"description\": \"Test_CourseUnit_desc_1\"\n" +
            "         }\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6d\": {\n" +
            "         \"isNew\": true,\n" +
            "         \"root\": false,\n" +
            "         \"metadata\": {\n" +
            "           \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "           \"contentType\": \"TextBookUnit\",\n" +
            "           \"code\": \"kp.citrus\",\n" +
            "           \"name\": \"Test_Course_SubUnit_name_1\",\n" +
            "           \"description\": \"Test_CourseSubUnit_desc_1\"\n" +
            "         }\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6e\": {\n" +
            "         \"isNew\": true,\n" +
            "         \"root\": false,\n" +
            "         \"metadata\": {\n" +
            "           \"mimeType\": \"application/vnd.ekstep.content-collection\",\n" +
            "           \"contentType\": \"TextBookUnit\",\n" +
            "           \"code\": \"kp.citrus\",\n" +
            "           \"name\": \"Test_Course_SubUnit_name_2\",\n" +
            "           \"description\": \"Test_CourseSubUnit_desc_2\"\n" +
            "         }\n" +
            "       }\n" +
            "     },\n" +
            "     \"hierarchy\": {\n" +
            "       \"${collectionIdVal}\": {\n" +
            "         \"children\": [\n" +
            "           \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6c\"\n" +
            "         ],\n" +
            "         \"root\": true\n" +
            "       },\n" +
            "       \"b9a50833-eff6-4ef5-a2a4-2413f2d51f6c\": {\n" +
            "         \"children\": [\n" +
            "         \t\"b9a50833-eff6-4ef5-a2a4-2413f2d51f6d\",\n" +
            "         \t\"b9a50833-eff6-4ef5-a2a4-2413f2d51f6e\",\n" +
            "         \t\"resource_1\",\n" +
            "         \t\"resource_2\",\n" +
            "         \t\"resource_3\"\n" +
            "         ],\n" +
            "         \"root\": false\n" +
            "       }\n" +
            "     }\n" +
            "   }\n" +
            " }\n" +
            "}\n";
}
