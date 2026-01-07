

package com.theokanning.openai.utils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import com.knuddels.jtokkit.api.ModelType;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.Encodings;
import java.util.*;
import com.theokanning.openai.utils.TikTokensUtil;
import com.theokanning.openai.utils.ModelEnum;
import org.junit.jupiter.api.*;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class TikTokensUtilGetModelTypeByNameTest {
    @Test
    @Tag("valid")
    @DisplayName("Scenario 1: Valid input - Name exactly matches ModelEnum.GPT_3_5_TURBO_0301")
    public void testMatchGpt35Turbo0301NameReturnsGpt35TurboType() {
        String name = ModelEnum.GPT_3_5_TURBO_0301.getName();
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals((ModelType) ModelType.GPT_3_5_TURBO, result);
    }
    @Test
    @Tag("valid")
    @DisplayName("Scenario 2: Valid input - Name matches ModelEnum.GPT_4")
    public void testMatchGpt4NameReturnsGpt4Type() {
        String name = ModelEnum.GPT_4.getName();
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals((ModelType) ModelType.GPT_4, result);
    }
    @Test
    @Tag("valid")
    @DisplayName("Scenario 3: Valid input - Name matches ModelEnum.GPT_4_32K")
    public void testMatchGpt4_32kNameReturnsGpt4Type() {
        String name = ModelEnum.GPT_4_32K.getName();
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals((ModelType) ModelType.GPT_4, result);
    }
    @Test
    @Tag("valid")
    @DisplayName("Scenario 4: Valid input - Name matches ModelEnum.GPT_4_32K_0314")
    public void testMatchGpt4_32k0314NameReturnsGpt4Type() {
        String name = ModelEnum.GPT_4_32K_0314.getName();
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals((ModelType) ModelType.GPT_4, result);
    }
    @Test
    @Tag("valid")
    @DisplayName("Scenario 5: Valid input - Name matches ModelEnum.GPT_4_0314")
    public void testMatchGpt4_0314NameReturnsGpt4Type() {
        String name = ModelEnum.GPT_4_0314.getName();
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals((ModelType) ModelType.GPT_4, result);
    }
    @Test
    @Tag("valid")
    @DisplayName("Scenario 6: Valid input - Name matches other ModelType.getName()")
    public void testMatchOtherModelTypeNameReturnsCorrectType() {
        String name = ModelType.GPT_3_5_TURBO.getName(); // Not gpt-3.5-turbo-0301; fallback case
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals((ModelType) ModelType.GPT_3_5_TURBO, result);
    }
    @Test
    @Tag("invalid")
    @DisplayName("Scenario 7: Invalid input - Unknown name returns null")
    public void testUnknownModelNameReturnsNull() {
        String name = "not-a-model-name"; // TODO: Change to another invalid name if needed
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertNull(result);
    }
    @Test
    @Tag("boundary")
    @DisplayName("Scenario 8: Edge case - Name is null")
    public void testNullNameReturnsNull() {
        String name = null;
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertNull(result);
    }
    @Test
    @Tag("boundary")
    @DisplayName("Scenario 9: Edge case - Name is an empty string")
    public void testEmptyStringNameReturnsNull() {
        String name = "";
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertNull(result);
    }
    @Test
    @Tag("boundary")
    @DisplayName("Scenario 10: Edge case - Name contains only whitespaces")
    public void testWhitespaceOnlyNameReturnsNull() {
        String name = "   ";
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertNull(result);
    }
    @Test
    @Tag("boundary")
    @DisplayName("Scenario 11: Edge case - Name matches ModelEnum.GPT_4_1106_preview (not explicitly handled)")
    public void testMatchGpt4_1106PreviewReturnsNullOrCorrectType() {
        String name = ModelEnum.GPT_4_1106_preview.getName();
        ModelType expected = null;
        for (ModelType mt : ModelType.values()) {
            if (mt.getName().equals(name)) {
                expected = mt;
                break;
            }
        }
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertEquals(expected, result);
    }
    @Test
    @Tag("invalid")
    @DisplayName("Scenario 12: Case sensitivity - Name case does not match")
    public void testCaseInsensitiveNameReturnsNull() {
        String name = "GPT-4"; // should be "gpt-4"
        ModelType result = TikTokensUtil.getModelTypeByName(name);
        assertNull(result);
    }
    @Test
    @Tag("boundary")
    @DisplayName("Scenario 13: Edge case - Name with leading/trailing whitespace")
    public void testNameWithWhitespaceReturnsNull() {
        String validName = ModelEnum.GPT_4.getName();
        String nameWithSpaces = "  " + validName + "  ";
        ModelType result = TikTokensUtil.getModelTypeByName(nameWithSpaces);
        assertNull(result);
    }
    @Test
    @Tag("integration")
    @DisplayName("Scenario 14: Fallback - ModelType.values covers all names")
    public void testAllModelTypeNamesReturnCorrespondingType() {
        for (ModelType modelType : ModelType.values()) {
            String name = modelType.getName();
            ModelType result = TikTokensUtil.getModelTypeByName(name);
            assertEquals(modelType, result, "Failed for: " + name);
        }
    }
}