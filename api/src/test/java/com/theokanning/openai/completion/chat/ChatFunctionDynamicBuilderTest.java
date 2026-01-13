

package com.theokanning.openai.completion.chat;
import lombok.Data;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.junit.jupiter.api.*;

public class ChatFunctionDynamicBuilderTest {
    @Test
    @Tag("valid")
    public void testBuilderReturnsNonNullBuilderInstance() {
        ChatFunctionDynamic.Builder builder = ChatFunctionDynamic.builder();
        Assertions.assertNotNull((Object) builder);
        Assertions.assertTrue(builder instanceof ChatFunctionDynamic.Builder);
    }
    @Test
    @Tag("valid")
    public void testBuilderReturnsUniqueInstances() {
        ChatFunctionDynamic.Builder builder1 = ChatFunctionDynamic.builder();
        ChatFunctionDynamic.Builder builder2 = ChatFunctionDynamic.builder();
        Assertions.assertNotSame((Object) builder1, (Object) builder2);
    }
    @Test
    @Tag("boundary")
    public void testBuilderInitializesWithDefaultState() {
        ChatFunctionDynamic.Builder builder = ChatFunctionDynamic.builder();
        Assertions.assertNull((Object) builder.name); // Default
        Assertions.assertNull((Object) builder.description); // Default
        Assertions.assertNotNull((Object) builder.parameters);
        // Parameters should have zero properties at init
        Assertions.assertEquals(0, ((ChatFunctionParameters) builder.parameters).properties.size());
    }
    @Test
    @Tag("valid")
    public void testBuilderMethodIsPublic() {
        // Since Java test can access static public methods,
        // compilation of this code means method is public
        ChatFunctionDynamic.Builder builder = ChatFunctionDynamic.builder();
        Assertions.assertTrue(builder instanceof ChatFunctionDynamic.Builder);
    }
    @Test
    @Tag("valid")
    public void testBuilderReturnsCorrectType() {
        for (int i = 0; i < 5; i++) {
            ChatFunctionDynamic.Builder builder = ChatFunctionDynamic.builder();
            Assertions.assertTrue(builder instanceof ChatFunctionDynamic.Builder);
        }
    }
    @Test
    @Tag("integration")
    public void testBuilderMethodIntegrationInBuildProcess() {
        String expectedName = "TestFunction"; // TODO: change value as needed
        String expectedDescription = "Function description"; // TODO: change value as needed
        ChatFunctionParameters params = new ChatFunctionParameters();
        ChatFunctionProperty property = new ChatFunctionProperty();
        property.name = "userId"; // TODO
        property.type = "string";
        property.required = true;
        property.description = "User ID";
        params.addProperty(property);
        ChatFunctionDynamic chatFunction = ChatFunctionDynamic.builder()
                .name(expectedName)
                .description(expectedDescription)
                .parameters(params)
                .build();
        Assertions.assertEquals(expectedName, (Object) chatFunction.name);
        Assertions.assertEquals(expectedDescription, (Object) chatFunction.description);
        Assertions.assertEquals(params, (Object) chatFunction.parameters);
    }
    @Test
    @Tag("boundary")
    public void testBuilderHandlesConcurrentAccess() throws InterruptedException {
        int threadCount = 5;
        AtomicReferenceArray<ChatFunctionDynamic.Builder> builders = new AtomicReferenceArray<>(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Runnable task = () -> {
            int idx = (int) Thread.currentThread().getId() % threadCount;
            builders.set(idx, ChatFunctionDynamic.builder());
            latch.countDown();
        };
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
        latch.await();
        for (int i = 0; i < threadCount; i++) {
            Assertions.assertNotNull((Object) builders.get(i));
        }
        for (int i = 0; i < threadCount; i++) {
            for (int j = i + 1; j < threadCount; j++) {
                Assertions.assertNotSame((Object) builders.get(i), (Object) builders.get(j));
            }
        }
    }
    @Test
    @Tag("valid")
    public void testBuilderIsUsableWithoutInstance() {
        ChatFunctionDynamic.Builder builder = ChatFunctionDynamic.builder();
        Assertions.assertNotNull((Object) builder);
        Assertions.assertTrue(builder instanceof ChatFunctionDynamic.Builder);
    }
    @Test
    @Tag("valid")
    public void testBuilderIsIndependentOfChatFunctionDynamicFields() {
        ChatFunctionDynamic functionInstance = new ChatFunctionDynamic("SomeName"); // TODO
        functionInstance.description = "instanceDescription";
        functionInstance.parameters = new ChatFunctionParameters();
        ChatFunctionDynamic.Builder builderFromStatic = ChatFunctionDynamic.builder();
        Assertions.assertNotNull((Object) builderFromStatic);
        Assertions.assertTrue(builderFromStatic instanceof ChatFunctionDynamic.Builder);
        // Confirm builder's default fields untouched by instance
        Assertions.assertNull((Object) builderFromStatic.name);
        Assertions.assertNull((Object) builderFromStatic.description);
    }
    @Test
    @Tag("valid")
    public void testBuilderDoesNotThrowException() {
        for (int i = 0; i < 10; i++) {
            Assertions.assertDoesNotThrow(() -> ChatFunctionDynamic.builder());
        }
    }
}