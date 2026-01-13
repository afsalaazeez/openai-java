

package com.theokanning.openai.completion.chat;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class ChatFunctionBuilderTest {
    @Test
    @Tag("valid")
    public void testBuilderReturnsNonNullBuilder() {
        ChatFunction.Builder builder = ChatFunction.builder();
        assertNotNull((Object) builder);
        assertEquals(ChatFunction.Builder.class, builder.getClass());
    }
    @Test
    @Tag("valid")
    public void testBuilderProvidesUniqueInstances() {
        ChatFunction.Builder builder1 = ChatFunction.builder();
        ChatFunction.Builder builder2 = ChatFunction.builder();
        assertNotSame((Object) builder1, (Object) builder2);
    }
    @Test
    @Tag("valid")
    public void testBuilderReturnsBuilderType() {
        Object builder = ChatFunction.builder();
        assertTrue(builder instanceof ChatFunction.Builder);
    }
    @Test
    @Tag("valid")
    public void testBuilderAllowsConstructionOfMinimalObject() {
        String testName = "testFunction";
        ChatFunction chatFunction = ChatFunction.builder().name(testName).build();
        assertNotNull((Object) chatFunction);
        assertEquals((Object) testName, (Object) chatFunction.name); // TODO: replace direct access if accessor exists
    }
    @Test
    @Tag("valid")
    public void testBuilderInstancesProduceIndependentObjects() {
        String name1 = "firstFunction";
        String name2 = "secondFunction";
        ChatFunction chatFunction1 = ChatFunction.builder().name(name1).build();
        ChatFunction chatFunction2 = ChatFunction.builder().name(name2).build();
        assertNotSame((Object) chatFunction1, (Object) chatFunction2);
        assertEquals((Object) name1, (Object) chatFunction1.name); // TODO: replace direct access if accessor exists
        assertEquals((Object) name2, (Object) chatFunction2.name); // TODO: replace direct access if accessor exists
    }
    @Test
    @Tag("boundary")
    public void testBuilderImmutabilityBetweenBuilds() {
        ChatFunction.Builder builder = ChatFunction.builder().name("originalName");
        ChatFunction chatFunction1 = builder.build();
        builder.name("newName");
        ChatFunction chatFunction2 = builder.build();
        assertEquals((Object) "originalName", (Object) chatFunction1.name); // TODO: replace direct access if accessor exists
        assertEquals((Object) "newName", (Object) chatFunction2.name); // TODO: replace direct access if accessor exists
        assertNotEquals((Object) chatFunction1.name, (Object) chatFunction2.name); // TODO: replace direct access if accessor exists
    }
    @Test
    @Tag("valid")
    public void testBuilderSupportsAllProperties() {
        String name = "fullFunction";
        String description = "This function does something.";
        Class<?> parametersClass = String.class;
        Function<Object, Object> executor = input -> "processed_" + input;
        ChatFunction chatFunction = ChatFunction.builder()
                .name(name)
                .description(description)
                .executor(parametersClass, executor)
                .build();
        assertEquals((Object) name, (Object) chatFunction.name); // TODO: replace direct access if accessor exists
        assertEquals((Object) description, (Object) chatFunction.description); // TODO: replace direct access if accessor exists
        assertEquals((Object) parametersClass, (Object) chatFunction.parametersClass); // TODO: replace direct access if accessor exists
        assertEquals((Object) executor, (Object) chatFunction.executor); // TODO: replace direct access if accessor exists
    }
    @Test
    @Tag("boundary")
    public void testBuilderHandlesNullOptionalProperties() {
        String testName = "onlyName";
        ChatFunction chatFunction = ChatFunction.builder().name(testName).build();
        assertNull((Object) chatFunction.description); // TODO: replace direct access if accessor exists
        assertNull((Object) chatFunction.parametersClass); // TODO: replace direct access if accessor exists
        assertNull((Object) chatFunction.executor); // TODO: replace direct access if accessor exists
        assertEquals((Object) testName, (Object) chatFunction.name); // TODO: replace direct access if accessor exists
    }
    @Test
    @Tag("valid")
    public void testBuilderSetsExecutorProperty() {
        Function<Object, Object> executor = input -> "Hello, " + input;
        ChatFunction chatFunction = ChatFunction.builder()
                .name("functionWithExecutor")
                .executor(String.class, executor)
                .build();
        assertEquals((Object) executor, (Object) chatFunction.executor); // TODO: replace direct access if accessor exists
    }
    @Test
    @Tag("valid")
    public void testBuilderSupportsMethodChaining() {
        String name = "chainFunction";
        String description = "Chained builder description.";
        Function<Object, Object> executor = input -> input;
        ChatFunction chatFunction = ChatFunction.builder()
                .name(name)
                .description(description)
                .executor(Integer.class, executor)
                .build();
        assertEquals((Object) name, (Object) chatFunction.name); // TODO: replace direct access if accessor exists
        assertEquals((Object) description, (Object) chatFunction.description); // TODO: replace direct access if accessor exists
        assertEquals((Object) Integer.class, (Object) chatFunction.parametersClass); // TODO: replace direct access if accessor exists
        assertEquals((Object) executor, (Object) chatFunction.executor); // TODO: replace direct access if accessor exists
    }
}