package processor;

import annotations.classes.AnnotatedClass;
import annotations.classes.BaseType;
import annotations.classes.NotAnnotatedClass;
import annotations.processor.Processor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Optional<AnnotatedClass> annotatedClass = processor.createAnnotatedClassIfExists(AnnotatedClass.class);

        assertTrue(annotatedClass.isPresent());
    }

    @Test
    void processor_returns_no_instance_if_no_annotated_class_exists() {
        Optional<NotAnnotatedClass> annotatedClass = processor.createAnnotatedClassIfExists(NotAnnotatedClass.class);

        assertTrue(annotatedClass.isEmpty());
    }

    @Test
    void processor_returns_an_instance_of_all_annotated_class_with_common_base() {
        List<BaseType> annotatedClass = processor.createAnnotatedClasses(BaseType.class);

        assertEquals(1, annotatedClass.size());
    }

}