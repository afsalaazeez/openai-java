

package com.theokanning.openai.completion.chat;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

@Data
class ChatFunctionParametersAddPropertyTest {
    private String name;
    private String type;
    private Boolean required;
    private String description;
    private ChatFunctionProperty items;
    private Set<?> enumValues;
    public ChatFunctionProperty(String name, String type, Boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }
    public String getName() {
        return name;
    }
    public Boolean getRequired() {
        return required;
    }
}
@Data
class ChatFunctionParametersAddPropertyTest {
    private final String type = "object";
    private final HashMap<String, ChatFunctionProperty> properties = new HashMap<>();
    private List<String> required;
    public void addProperty(ChatFunctionProperty property) {
        properties.put(property.getName(), property);
        if (Boolean.TRUE.equals(property.getRequired())) {
            if (this.required == null) {
                this.required = new ArrayList<>();
            }
            this.required.add(property.getName());
        }
    }
}

class ChatFunctionParametersAddPropertyTest {
    private ChatFunctionParameters parameters;
    @BeforeEach
    void setUp() {
        parameters = new ChatFunctionParameters();
    }
    @Test
    @Tag("valid")
    public void testAddNonRequiredProperty() {
        ChatFunctionProperty property = new ChatFunctionProperty("simple", "string", false);
        parameters.addProperty(property);
        assertTrue(parameters.getProperties().containsKey("simple"), "Property map should contain the added property");
        assertNull(parameters.getRequired(), "Required list should remain null for non-required property");
    }
    @Test
    @Tag("valid")
    public void testAddRequiredPropertyWhenRequiredListIsNull() {
        ChatFunctionProperty property = new ChatFunctionProperty("requiredProp", "integer", true);
        parameters.addProperty(property);
        assertTrue(parameters.getProperties().containsKey("requiredProp"), "Property map should have the required property");
        assertNotNull(parameters.getRequired(), "Required list should be initialized for required property");
        assertEquals(1, parameters.getRequired().size(), "Required list should contain exactly one property name");
        assertEquals("requiredProp", parameters.getRequired().get(0), "Property name should match");
    }
    @Test
    @Tag("boundary")
    public void testAddRequiredPropertyWhenRequiredListExists() {
        ChatFunctionProperty first = new ChatFunctionProperty("firstRequired", "boolean", true);
        ChatFunctionProperty second = new ChatFunctionProperty("secondRequired", "array", true);
        parameters.addProperty(first);
        parameters.addProperty(second);
        assertTrue(parameters.getProperties().containsKey("firstRequired"), "Properties map should contain first property");
        assertTrue(parameters.getProperties().containsKey("secondRequired"), "Properties map should contain second property");
        assertEquals(2, parameters.getRequired().size(), "Required list should have two entries");
        assertEquals("firstRequired", parameters.getRequired().get(0), "First required property name match");
        assertEquals("secondRequired", parameters.getRequired().get(1), "Second required property name match");
    }
    @Test
    @Tag("valid")
    public void testAddDuplicatePropertyName() {
        ChatFunctionProperty prop1 = new ChatFunctionProperty("test", "object", false);
        ChatFunctionProperty prop2 = new ChatFunctionProperty("test", "object", false);
        parameters.addProperty(prop1);
        parameters.addProperty(prop2);
        assertEquals(1, parameters.getProperties().size(), "Properties map should have one entry for duplicate name");
        assertSame(prop2, parameters.getProperties().get("test"), "Map value must be last property added");
        assertNull(parameters.getRequired(), "Required list should not be updated for non-required property overwrite");
    }
    @Test
    @Tag("boundary")
    public void testAddRequiredThenNonRequiredSameName() {
        ChatFunctionProperty requiredProp = new ChatFunctionProperty("overlap", "string", true);
        ChatFunctionProperty nonRequiredProp = new ChatFunctionProperty("overlap", "string", false);
        parameters.addProperty(requiredProp);
        parameters.addProperty(nonRequiredProp);
        assertSame(nonRequiredProp, parameters.getProperties().get("overlap"), "Map value is latest");
        assertEquals(1, parameters.getRequired().size(), "Required list retains property name");
        assertEquals("overlap", parameters.getRequired().get(0), "Required list should contain property name");
    }
    @Test
    @Tag("invalid")
    public void testAddPropertyWithNullRequiredFlag() {
        ChatFunctionProperty property = new ChatFunctionProperty("nullRequired", "number", null);
        parameters.addProperty(property);
        assertTrue(parameters.getProperties().containsKey("nullRequired"), "Properties map must include property");
        assertNull(parameters.getRequired(), "Required list stays null when required flag is null");
    }
    @Test
    @Tag("valid")
    public void testAddMultipleMixedRequiredAndNonRequiredProperties() {
        ChatFunctionProperty prop1 = new ChatFunctionProperty("prop1", "string", true);
        ChatFunctionProperty prop2 = new ChatFunctionProperty("prop2", "integer", false);
        ChatFunctionProperty prop3 = new ChatFunctionProperty("prop3", "object", null);
        ChatFunctionProperty prop4 = new ChatFunctionProperty("prop4", "array", true);
        parameters.addProperty(prop1);
        parameters.addProperty(prop2);
        parameters.addProperty(prop3);
        parameters.addProperty(prop4);
        assertEquals(4, parameters.getProperties().size(), "All properties should be added to map");
        List<String> requiredList = parameters.getRequired();
        assertNotNull(requiredList, "Required list should exist after adding requireds");
        assertTrue(requiredList.contains("prop1"), "Required list should contain prop1");
        assertTrue(requiredList.contains("prop4"), "Required list should contain prop4");
        assertEquals(2, requiredList.size(), "Only required properties should be in required list");
        assertFalse(requiredList.contains("prop2"));
        assertFalse(requiredList.contains("prop3"));
    }
    @Test
    @Tag("integration")
    public void testAddRequiredPropertyWithExistingRequiredList() {
        parameters.setRequired(new ArrayList<>());
        parameters.getRequired().add("existingRequired"); // TODO: Change name as required
        ChatFunctionProperty newRequired = new ChatFunctionProperty("addedRequired", "string", true);
        parameters.addProperty(newRequired);
        List<String> requiredList = parameters.getRequired();
        assertEquals(2, requiredList.size(), "Required list should contain both old and new property names");
        assertEquals("existingRequired", requiredList.get(0), "First name should remain unchanged");
        assertEquals("addedRequired", requiredList.get(1), "Second name should be newly added property");
    }
    @Test
    @Tag("invalid")
    public void testAddPropertyWithNameNull() {
        ChatFunctionProperty nullNameRequired = new ChatFunctionProperty(null, "string", true);
        ChatFunctionProperty nullNameNotRequired = new ChatFunctionProperty(null, "string", false);
        parameters.addProperty(nullNameRequired);
        parameters.addProperty(nullNameNotRequired);
        assertTrue(parameters.getProperties().containsKey(null), "Properties map contains null key");
        assertNotNull(parameters.getRequired(), "Required list must be created for required=true with null name");
        assertEquals(1, parameters.getRequired().size(), "Required list should include property with null name once");
        assertNull(parameters.getRequired().get(0), "Required list's first (and only) entry should be null");
    }
    @Test
    @Tag("boundary")
    public void testAddPropertyMultipleTimesWithChangingRequiredFlag() {
        ChatFunctionProperty nonRequired1 = new ChatFunctionProperty("toggle", "integer", false);
        ChatFunctionProperty required2 = new ChatFunctionProperty("toggle", "integer", true);
        ChatFunctionProperty nonRequired3 = new ChatFunctionProperty("toggle", "integer", false);
        parameters.addProperty(nonRequired1);
        parameters.addProperty(required2);
        parameters.addProperty(nonRequired3);
        assertSame(nonRequired3, parameters.getProperties().get("toggle"), "Properties map has the last property");
        assertNotNull(parameters.getRequired(), "Required list is not null after required property added");
        assertEquals(1, parameters.getRequired().size(), "Required list contains property only once");
        assertEquals("toggle", parameters.getRequired().get(0), "Required list keeps property name after toggle");
    }
}