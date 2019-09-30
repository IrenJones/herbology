package com.example.herbology;

import lombok.SneakyThrows;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.defaultanswers.ReturnsSmartNulls;
import org.mockito.invocation.InvocationOnMock;

public class StrictMockito extends Mockito {

    @FunctionalInterface
    public interface MockConfigurer<T> {
        void configure(T mock) throws Throwable;
    }

    @SneakyThrows
    public static <T> T strictMock(Class<T> classToMock, MockConfigurer<T> configurer) {
        StrictAnswer defaultAnswer = new StrictAnswer();
        T mock = mock(classToMock, defaultAnswer);

        // We want toString() be mocked by default:
        when(mock.toString()).thenReturn(classToMock.getSimpleName() + "@" + System.identityHashCode(mock));

        configurer.configure(mock);
        defaultAnswer.isReady = true;
        return mock;
    }

    private static class StrictAnswer extends ReturnsSmartNulls {

        private boolean isReady;

        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            if (!isReady) return super.answer(invocation);
            throw new IllegalStateException("Unknown invocation: " + invocation);
        }
    }
}
