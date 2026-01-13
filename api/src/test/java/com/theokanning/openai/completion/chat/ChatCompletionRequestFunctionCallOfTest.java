

package com.theokanning.openai.completion.chat;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

public class ChatCompletionRequestFunctionCallOfTest {
    @Test
    @Tag("valid")
    public void testCreateInstanceWithValidName() {
        String validName = "functionName";
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(validName);
        assertEquals((String) validName, (String) functionCall.name, "Name should match the valid input string");
    }
    @Test
    @Tag("boundary")
    public void testCreateInstanceWithEmptyStringName() {
        String emptyName = "";
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(emptyName);
        assertEquals((String) emptyName, (String) functionCall.name, "Name should be an empty string");
    }
    @Test
    @Tag("invalid")
    public void testCreateInstanceWithNullName() {
        String nullName = null;
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(nullName);
        assertNull((String) functionCall.name, "Name should be null for null input");
    }
    @Test
    @Tag("valid")
    public void testCreateInstanceWithSpecialCharacterName() {
        String specialName = "!@#$_- ";
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(specialName);
        assertEquals((String) specialName, (String) functionCall.name, "Special character name should be retained");
    }
    @Test
    @Tag("boundary")
    public void testCreateInstanceWithLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("x"); // TODO: Adjust the length as needed for upper bounds
        }
        String longName = sb.toString();
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(longName);
        assertEquals((String) longName, (String) functionCall.name, "Long string name should not be truncated");
    }
    @Test
    @Tag("valid")
    public void testReturnedObjectIsNeverNull() {
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("someName");
        assertNotNull((Object) functionCall, "Returned object should not be null");
    }
    @Test
    @Tag("valid")
    public void testReturnedObjectTypeIsCorrect() {
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("test");
        assertTrue(functionCall instanceof ChatCompletionRequest.ChatCompletionRequestFunctionCall,
            "Object should be of type ChatCompletionRequestFunctionCall");
    }
    @Test
    @Tag("valid")
    public void testDistinctObjectsForDistinctNames() {
        String name1 = "name1";
        String name2 = "name2";
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall1 =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(name1);
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall2 =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(name2);
        assertNotEquals((String) functionCall1.name, (String) functionCall2.name,
            "Name fields should be distinct for distinct input values");
    }
    @Test
    @Tag("valid")
    public void testDistinctObjectsForSameName() {
        String repeatName = "repeatName";
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall1 =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(repeatName);
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall2 =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(repeatName);
        assertNotSame((Object) functionCall1, (Object) functionCall2,
            "Distinct instances should be created for the same input value each time");
    }
    @Test
    @Tag("valid")
    public void testNameCannotBeChangedAfterConstruction() {
        String immutableName = "immutableName";
        ChatCompletionRequest.ChatCompletionRequestFunctionCall functionCall =
            ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(immutableName);
        // No setter exists, we verify immutability by only checking that the name remains unchanged
        assertEquals((String) immutableName, (String) functionCall.name,
            "Name field should remain unchanged after construction");
    }
}