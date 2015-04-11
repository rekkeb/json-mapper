package com.rekkeb.tests.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;

/**
 *
 * Created by Rekkeb on 11/04/15.
 */
@RunWith(JUnit4.class)
public class TestsJsonMappings {

    private String jsonData = "{" +
            "    \"name\" : \"test\"," +
            "    \"lastName\" : \"testLN\"," +
            "    \"phone\" : 123456789," +
            "    \"email\" : [" +
            "        {\"work\":\"email@work.com\"}," +
            "        {\"personal\":\"email@personal.com\"}" +
            "    ]," +
            "    \"certs\" : {" +
            "        \"aws\" : true," +
            "        \"hadoop\" : false" +
            "    }" +
            "}";

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testJsonNode() throws Exception {

        JsonNode root = mapper.readTree(jsonData);

        Assert.assertNotNull(root);
        Assert.assertEquals(5, root.size()); //We got five nodes


        Assert.assertNull(root.get("not-exist"));


        //Name
        Assert.assertNotNull(root.get("name"));
        Assert.assertTrue(root.get("name").isTextual());
        Assert.assertEquals("test", root.get("name").textValue());

        //Phone
        Assert.assertNotNull(root.get("phone"));
        Assert.assertTrue(root.get("phone").isNumber());
        Assert.assertEquals(123456789, root.get("phone").intValue());

        //Email
        Assert.assertNotNull(root.get("email"));
        Assert.assertTrue(root.get("email").isArray());

        Assert.assertEquals(2, root.get("email").size()); //We got 2 elements within the array

        for (JsonNode email : root.get("email")){
            Assert.assertTrue(email.isObject());
            for (Iterator<String> i = email.fieldNames(); i.hasNext();){
                String fieldName = i.next();
                Assert.assertTrue(email.get(fieldName).isTextual());
            }
        }

        //Certs
        Assert.assertTrue(root.get("certs").isObject());

        JsonNode certs = root.get("certs");

        Assert.assertTrue(certs.findValue("aws").asBoolean());

    }
}
