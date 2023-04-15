package processor;

import annotations.classes.AnnotatedClass;
import annotations.processor.Processor;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessorTest {
    Processor processor = new Processor();

    @Test
    void processor_recognizes_an_annotated_class() {
        boolean annotated = processor.isAnnotated(AnnotatedClass.class);

        assertTrue(annotated);
    }

    @Test
    void processor_returns_only_annotated_classes() {
        Set<Class<?>> annotatedClasses = processor.getAnnotatedClasses();

        assertEquals(1, annotatedClasses.size());
        assertEquals(AnnotatedClass.class, annotatedClasses.iterator().next());
    }

    @Test
    void processor_returns_an_instance_of_first_annotated_class() {
        AnnotatedClass annotatedClass = processor.createAnnotatedClass(AnnotatedClass.class);

        assertNotNull(annotatedClass);
    }
}